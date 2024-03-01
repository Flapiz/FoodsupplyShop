package com.foodsupply.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.foodsupply.model.Roles;
import com.foodsupply.model.Users;
import com.foodsupply.repository.UserRepository;

@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private UserRepository userrepository;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		Users user = userrepository.readByUsername(username);
		if(null != user && user.getUserId() > 0 && passwordEncoder.matches(pwd, user.getPwd())) {
			return new UsernamePasswordAuthenticationToken(
					username, null, getGrantedAuthorities(user.getRoles()));
		}else{
			throw new BadCredentialsException("Invalid credentials!");
		}
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(Roles roles){
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roles.getRoleName()));
		return grantedAuthorities;
	}
	

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
