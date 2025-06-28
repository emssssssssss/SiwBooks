package it.uniroma3.repository;

import it.uniroma3.model.Libro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibroRepository extends JpaRepository<Libro, Long>{
    // Primi 10 libri più recenti (ordinati per ID decrescente)
    List<Libro> findTop6ByOrderByAnnoPubblicazioneDesc();

    // Libri per genere (esatto)
    List<Libro> findByGenere(String genere);

    @Query("SELECT l FROM Libro l JOIN l.autori a " +
        "WHERE (:titolo IS NULL OR LOWER(l.titolo) LIKE LOWER(CONCAT('%', :titolo, '%'))) " +
        "AND (:genere IS NULL OR LOWER(l.genere) LIKE LOWER(CONCAT('%', :genere, '%'))) " +
        "AND (:autore IS NULL OR LOWER(CONCAT(a.nome, ' ', a.cognome)) LIKE LOWER(CONCAT('%', :autore, '%')))")
    List<Libro> findByTitoloGenereAutore(@Param("titolo") String titolo,
                                        @Param("genere") String genere,
                                        @Param("autore") String autore);
}
