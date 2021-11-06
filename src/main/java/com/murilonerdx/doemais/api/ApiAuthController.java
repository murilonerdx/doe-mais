package com.murilonerdx.doemais.api;

import com.murilonerdx.doemais.dto.UserDTO;
import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.repository.UserRepository;
import com.murilonerdx.doemais.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.springframework.http.ResponseEntity.ok;

@Api(tags = "Endpoint de Autenticação")
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserRepository repository;

    @Operation(summary = "Autenticar usuario e retornar token")
    @SuppressWarnings("rawtypes")
    @PostMapping()
    public ResponseEntity signin(@RequestBody UserDTO userDTO) {
        try {
            String username = userDTO.getUsername();
            String pasword = userDTO.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pasword));

            Userman user = repository.findByUsername(username).get();

            var token = "";

            if (user != null) {
                token = tokenProvider.createToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }
}
