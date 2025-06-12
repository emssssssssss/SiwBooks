package it.uniroma3.repository;

import it.uniroma3.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long>{
    
}
