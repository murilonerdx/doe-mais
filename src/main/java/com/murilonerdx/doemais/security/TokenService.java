package com.murilonerdx.doemais.security;


import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.services.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class TokenService {

    private String secret = "ipfwxnitkzomvapmwfteowxjzbhfssnmpjkagtgvkewycrbiet";


    @Autowired
    private UserService userService;


    public String getToken(Authentication authhenticate) {

        Userman usuario = (Userman) authhenticate.getPrincipal();

        Date today = new Date();
        long duration = 60000 * 1440;
        Date expirationDate = new Date(today.getTime() + duration);
        return Jwts.builder()
                .setIssuer("OneID")
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
        return usuario;
    }

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || header.isEmpty() || !header.startsWith("Bearer "))
            return null;

        return header.substring(7, header.length());

    }
}
