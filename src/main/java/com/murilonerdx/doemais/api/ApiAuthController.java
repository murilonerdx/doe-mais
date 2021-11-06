package com.murilonerdx.doemais.api;

import com.murilonerdx.doemais.dto.UserDTO;
import com.murilonerdx.doemais.exceptions.AuthorizationException;
import com.murilonerdx.doemais.security.TokenService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "Endpoint para autenticação")
public class ApiAuthController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    TokenService tokenService;

    @PostMapping()
    public ResponseEntity<Object> auth(@RequestBody @Valid UserDTO auth) {
        Map<Object, Object> model = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(auth.getUsername(), new BCryptPasswordEncoder().encode(auth.getPassword()));
        Authentication authenticate = authManager.authenticate(authentication);
        String token = tokenService.getToken(authenticate);

        model.put("email", auth.getUsername());
        model.put("token", token);
        return ResponseEntity.ok(model);
    }
}
