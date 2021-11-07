package com.murilonerdx.doemais.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.services.TokenService;
import com.murilonerdx.doemais.services.UserService;

public class AuthorizationFilter extends OncePerRequestFilter {

	private TokenService tokenService;

	private UserService usuarioService;

	public AuthorizationFilter(TokenService tokenService, UserService usuarioService) {
		this.tokenService = tokenService;
		this.usuarioService = usuarioService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = tokenService.extractToken(request);

		boolean valid = tokenService.isValid(token);

		if (valid)
			authorizeUser(token);

		filterChain.doFilter(request, response);
	}

	private void authorizeUser(String token) {
		Long id = tokenService.getIdUsuario(token);

		Userman usuario = usuarioService.findById(id);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null,
				usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
