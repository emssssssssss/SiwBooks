package it.uniroma3.service;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Utente;
import it.uniroma3.repository.UtenteRepository;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

   // public Optional<Utente> findByUsername(String username) {
   //     return utenteRepository.findByUsername(username);
   // }

    public Utente save(Utente utente) {
        return utenteRepository.save(utente);
    }
}
