package it.uniroma3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{
    
}
