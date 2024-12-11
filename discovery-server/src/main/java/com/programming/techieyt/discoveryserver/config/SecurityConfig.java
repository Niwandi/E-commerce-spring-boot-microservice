package com.programming.techieyt.discoveryserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${eureka.username}")
    private String username;

    @Value("${eureka.password}")
    private String password;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection (optional)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/eureka/**").permitAll()  // Permit access to Eureka endpoints
                        .anyRequest().authenticated()              // Require authentication for other requests
                )
                .httpBasic();  // Enable HTTP Basic Authentication

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();  // Secure Password Encoder

        UserDetails user = User.builder()
                .username(username)                        // Use the username from application properties
                .password(encoder.encode(password))        // Encode the password
                .roles("USER")                             // Assign a role to the user
                .build();

        return new InMemoryUserDetailsManager(user);   // Store the user in memory
    }
}
