package com.example.bibliothequecours.security;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.bibliothequecours.service.UserDetailsServiceImpl;

import jakarta.activation.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
		System.out.println("SecurityConfig - jdbcUserDetailsManager");
		return jdbcUserDetailsManager(dataSource);
	}
	
	/* @Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		return new InMemoryUserDetailsManager(
				//User.withUsername("tophe").password("{noop}tophe").roles("USER","ADMIN").build()
				User.withUsername("abonne").password(passwordEncoder.encode("abonne")).roles("ABONNE").build(),
				User.withUsername("tophe").password(passwordEncoder.encode("tophe")).roles("ABONNE","ADMIN").build()
				);
	} */
	
	@Bean //exécute la méthode au démarrage
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		System.out.println("SecurityConfig - securityFilterChain");
		httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();
		httpSecurity.authorizeHttpRequests().requestMatchers("/images/**", "/css/**").permitAll();
		
		//httpSecurity.authorizeHttpRequests().requestMatchers("/abonne/**").hasRole("ABONNE");
		//httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
		httpSecurity.authorizeHttpRequests().requestMatchers("/", "/accueil", "/afficher-livres", "/afficher-livre/**",
				"/login", "/creer-compte-validation", "/creer-compte", "/mentions-legales", "/cookies", "/donnees-personnelles").permitAll();
		httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
		httpSecurity.userDetailsService(userDetailsService);
		return httpSecurity.build();
	}

}
