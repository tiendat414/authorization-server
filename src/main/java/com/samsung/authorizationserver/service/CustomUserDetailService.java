package com.samsung.authorizationserver.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samsung.authorizationserver.entity.User;
import com.samsung.authorizationserver.repository.UserRepository;


@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new  UsernameNotFoundException("not found user");
		}
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),user.getPassword(),user.isEnabled(),true,true,true,
				getAuthorities(List.of(user.getRole()))			
				);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
		// TODO Auto-generated method stub
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(String role:roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

}
