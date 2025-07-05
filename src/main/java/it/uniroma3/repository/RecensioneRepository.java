package it.uniroma3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.model.Libro;
import it.uniroma3.model.Recensione;
import it.uniroma3.model.Utente;

public interface RecensioneRepository extends JpaRepository<Recensione, Long>{
    
    boolean existsByLibroAndUtente(Libro libro, Utente utente);

    boolean existsByLibroIdAndUtenteId(long id, Long id2);
}
