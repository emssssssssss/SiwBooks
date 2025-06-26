package it.uniroma3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.model.Utente;


public interface UtenteRepository extends JpaRepository<Utente, Long>{
    Optional<Utente> findByEmail(String email);
    Optional<Utente> findByUsername(String username);
}
