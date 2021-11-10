package com.murilonerdx.doemais.services;

import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    final
    UserRepository repository;


    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Userman> usuario = repository.findByUsername(username);
        if(usuario.isEmpty()) throw new UsernameNotFoundException("Email não encontrado");
        return usuario.get();
    }

    public Userman findById(Long id){
        return repository.findById(id).get();
    }

    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
