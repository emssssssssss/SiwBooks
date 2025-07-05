package it.uniroma3.authentication;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



import org.springframework.security.web.SecurityFilterChain;

import it.uniroma3.service.CustomUserDetailsService;

//import it.uniroma3.repository.UtenteRepository;


@Configuration
@EnableWebSecurity
public class AuthConfiguration {


    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // usa il costruttore non deprecato che riceve il UserDetailsService
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/registrazione", "/css/**", "/home", "/images/**", "/error", "/libri","/libri/**", "/libro", "/libro/**", "/autori",
                                    "/autore", "/autori/**").permitAll()
                
                                // Solo ADMIN può accedere a /admin/**
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // Solo USER o ADMIN possono accedere
                .requestMatchers("/user/**", "/profilo", "/profilo/**").hasAnyRole("USER", "ADMIN")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureUrl("/loginError")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

}
