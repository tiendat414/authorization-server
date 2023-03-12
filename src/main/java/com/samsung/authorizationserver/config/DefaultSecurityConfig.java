package com.samsung.authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;

import com.samsung.authorizationserver.service.CustomAuthenticationProvider;

@EnableWebSecurity
public class DefaultSecurityConfig {
	
	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and()
		.formLogin(Customizer.withDefaults());
		return http.build();
	}
	
	@Autowired
	public void bindAuthenticationManagerBuider(AuthenticationManagerBuilder authenticationManagerBuilder) {
		authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
	}
	
}
