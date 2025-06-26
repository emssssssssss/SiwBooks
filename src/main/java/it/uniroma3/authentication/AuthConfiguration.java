package it.uniroma3.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import it.uniroma3.repository.UtenteRepository;


@Configuration
@EnableWebSecurity
public class AuthConfiguration {
    

    @Autowired
    private UtenteRepository utenteRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/registrazione", "/css/**", "/home", "/images/**", "/error").permitAll()
                
                                // Solo ADMIN può accedere a /admin/**
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // Solo USER o ADMIN possono accedere a /user/**
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> utenteRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
