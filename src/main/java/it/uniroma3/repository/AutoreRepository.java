package it.uniroma3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.model.Autore;

public interface AutoreRepository extends JpaRepository<Autore, Long>{
    
}
