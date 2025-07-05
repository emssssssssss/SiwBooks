package it.uniroma3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Libro;
import it.uniroma3.model.Utente;
import it.uniroma3.repository.UtenteRepository;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Utente save(Utente utente) {
        return utenteRepository.save(utente);
    }

        // Salvataggio utente
    public Utente addUtente(Utente utente) {
        return utenteRepository.save(utente);
    }

    // Controllo se email già esistente
    public Optional<Utente> getUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    // Controllo se nome già esistente
    public Optional<Utente> getUtenteByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }

        // Autenticazione tramite email o nome + password
    public Optional<Utente> autentica(String identificatore, String rawPassword) {
        Optional<Utente> utente = utenteRepository.findByEmail(identificatore);

        if (utente.isEmpty()) {
            utente = utenteRepository.findByUsername(identificatore);
        }

        if (utente.isPresent() && passwordEncoder.matches(rawPassword, utente.get().getPassword())) {
            return utente;
        }

        return Optional.empty();
    }

    public boolean isAmministratore(String codice) {
        return codice != null && codice.equals("Panino_con_pomodori");
    }

    public void rimuoviLibroLetto(Utente utente, Libro libro) {
        if (utente.getLibriLetti().contains(libro)) {
            utente.getLibriLetti().remove(libro);
            this.save(utente);
        }
    }
}
