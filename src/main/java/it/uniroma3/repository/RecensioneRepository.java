package it.uniroma3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.model.Recensione;

public interface RecensioneRepository extends JpaRepository<Recensione, Long>{
    
}
