package com.murilonerdx.doemais.services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Userman;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenService {

	private String secret = "ipfwxnitkzomvapmwfteowxjzbhfssnmpjkagtgvkewycrbiet";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private OngService ongService;

	public String getToken(Authentication authhenticate) {
		
		Userman usuario = (Userman) authhenticate.getPrincipal();
		
		Date today = new Date();
		long duration = 60000 * 1440;
		Date expirationDate = new Date(today.getTime() + duration);
		return Jwts.builder()
						.setIssuer("DoeMais")
						.setSubject(usuario.getId().toString())
						.setIssuedAt(today)
						.setExpiration(expirationDate)
						.signWith(SignatureAlgorithm.HS256, secret)
					.compact()
		;
	}

	public boolean isValid(String token) {
		try {
			Jwts.parser()
				.setSigningKey(this.secret)
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims data = Jwts.parser()
			.setSigningKey(this.secret)
			.parseClaimsJws(token)
			.getBody();
		
		return Long.valueOf(data.getSubject());
	}
	
	public Object findByToken(String token) {
		if(token == null) return null;
		Long id = getIdUsuario(token);
		Userman usuario = userService.findById(id);
		if(usuario == null) return null;

		switch (usuario.getPermissions().get(0).getAuthority()) {
		case "ROLE_BUSINESS":
			
			try {
				Business business = businessService.findById(usuario.getId());
				return business;
			} catch (Exception e) {
				return null;
			}
			
		case "ROLE_ONG":
			
			try {
				Ong ong = ongService.findById(usuario.getId());
				return ong;
			} catch (Exception e) {
				return null;
			}
			
		default:
			return null;
		}
	}
	
	public String extractToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header == null || header.isEmpty() || !header.startsWith("Bearer "))
			return null;
		
		return header.substring(7, header.length());
		
	}

}
