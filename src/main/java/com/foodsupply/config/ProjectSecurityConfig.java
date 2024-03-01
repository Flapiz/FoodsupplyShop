package com.foodsupply.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		
		http.csrf((csrf) -> csrf.ignoringRequestMatchers("/creatUser")
				.ignoringRequestMatchers("/add/cart").ignoringRequestMatchers("/addcash")
				.ignoringRequestMatchers("/addcash/confirm"))
		.authorizeHttpRequests(requests -> requests.requestMatchers("/home").permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/product").permitAll()
				.requestMatchers("/cart").authenticated()
				.requestMatchers("/add/cart").authenticated()
				.requestMatchers("/delete/cart").authenticated()
				.requestMatchers("/addcash/confirm").authenticated()
				.requestMatchers("/addcash").authenticated()
				.requestMatchers("/confirm").authenticated()
				.requestMatchers("/login").permitAll()
				.requestMatchers("/logout").permitAll()
				.requestMatchers("/register").permitAll()
				.requestMatchers("/creatUser").permitAll()
				.requestMatchers("/asset/**").permitAll())
		.formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
				.defaultSuccessUrl("/cart")
				.failureUrl("/login?error=true").permitAll())
		.logout(logoutConfigurer -> logoutConfigurer
				.logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true).permitAll())
		.httpBasic(Customizer.withDefaults());	
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
