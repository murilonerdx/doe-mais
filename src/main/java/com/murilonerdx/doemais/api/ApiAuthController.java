package com.murilonerdx.doemais.api;

import com.murilonerdx.doemais.dto.CredentialDTO;
import com.murilonerdx.doemais.dto.OngDTO;
import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.exceptions.AuthorizationException;
import com.murilonerdx.doemais.repository.OngRepository;
import com.murilonerdx.doemais.repository.UserRepository;
import com.murilonerdx.doemais.security.JwtTokenProvider;
import com.murilonerdx.doemais.util.DozerConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.ResponseEntity.ok;

@Api(tags = "Endpoint de autenticação")
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserRepository repository;

    @Autowired
    OngRepository ongRepository;

    @Operation(summary = "Autenticar usuario e retornar token")
    @SuppressWarnings("rawtypes")
    @PostMapping()
    public ResponseEntity signin(@RequestBody CredentialDTO credentialDTO) {
        try {
            String username = credentialDTO.getUsername();
            String pasword = credentialDTO.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pasword));

            Userman user = repository.findByUsername(username).get();

            var token = "";

            if (user != null) {
                token = tokenProvider.createToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            return ok(map);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @GetMapping("/user")
    public ResponseEntity<OngDTO> getOng(HttpServletRequest request) {
        String username = tokenProvider.getUsername(tokenProvider.resolveToken(request));
        Ong ong = ongRepository.findByUser(repository.findByUsername(username).get());
        if(ong == null){
            throw new AuthorizationException("Você precisa ser uma ong para acessar esse endpoint");
        }
        OngDTO ongDTO = DozerConverter.parseObject(ong, OngDTO.class);

        if(ongDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ongDTO);
    }
}
