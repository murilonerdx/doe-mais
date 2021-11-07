package com.murilonerdx.doemais.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Userman> usuario = repository.findByUsername(username);
		if(usuario.isEmpty()) throw new UsernameNotFoundException("Username não encontrado");
		return usuario.get();
	}
	
	public static PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}