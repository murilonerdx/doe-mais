package com.murilonerdx.doemais.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.murilonerdx.doemais.services.AuthenticationService;
import com.murilonerdx.doemais.services.TokenService;
import com.murilonerdx.doemais.services.UserService;

@Configuration
@Order(1)
public class SecurityConfigurationAPI extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserService usuarioService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService)
			.passwordEncoder(AuthenticationService.getPasswordEncoder())
		;
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/api/**")
			.authorizeRequests()
				.antMatchers("/img/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/business", "/api/auth", "/api/ong")
					.permitAll()
				.antMatchers("/api/img/**")
					.permitAll()
				.anyRequest()
					.authenticated()
				.and()
					.csrf()
						.disable()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.addFilterBefore(new AuthorizationFilter(tokenService, usuarioService), UsernamePasswordAuthenticationFilter.class)
				 
		;
	}
}