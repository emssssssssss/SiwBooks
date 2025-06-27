package it.uniroma3.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import it.uniroma3.repository.UtenteRepository;

@Component
public class Authenticated {
    @Autowired
    private UtenteRepository utenteRepository;

    public Utente get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;

        Object principal = auth.getPrincipal();


        // Se Spring ti ha messo dentro un org.springframework.security.core.userdetails.User
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            // qui usi il repository per recuperare il tuo Utente
            return utenteRepository.findByUsername(username)
                    .orElse(null);
        }

        return null;
    }
}
