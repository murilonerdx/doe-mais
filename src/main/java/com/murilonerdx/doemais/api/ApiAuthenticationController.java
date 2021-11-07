package com.murilonerdx.doemais.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murilonerdx.doemais.entities.Credential;
import com.murilonerdx.doemais.services.TokenService;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthenticationController {
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthenticationManager authManeger;

	@PostMapping
	public ResponseEntity<String> login(@RequestBody Credential login) {
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
		try {
			Authentication authenticate = authManeger.authenticate(authentication);
			String token = tokenService.getToken(authenticate);
			return ResponseEntity.ok(token);
		} catch (AuthenticationException	 e) {
			return ResponseEntity.badRequest().build();		}
	}
	
	@GetMapping
	public ResponseEntity<Object> getUser(HttpServletRequest request) {
		Object usuario = tokenService.findByToken(tokenService.extractToken(request));
		if(usuario == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(usuario);
	}
}