package it.uniroma3.service;

import java.util.Collections;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Utente;
import it.uniroma3.repository.UtenteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    

   @Autowired
    private UtenteRepository utenteRepository;
    

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Utente utente = utenteRepository
            .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Utente non trovato con email o username: " + usernameOrEmail));

        // Converte il ruolo enum in SimpleGrantedAuthority
        String authority = "ROLE_" + utente.getRuolo().name();
        return new org.springframework.security.core.userdetails.User(
            utente.getUsername(),        // USERNAME nel contesto Spring
            utente.getPassword(),        // password già codificata
            Collections.singletonList(new SimpleGrantedAuthority(authority))
        );
    }

}
