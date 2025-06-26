package it.uniroma3.repository;

import it.uniroma3.model.Libro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long>{
    // Primi 10 libri più recenti (ordinati per ID decrescente)
    List<Libro> findTop6ByOrderByAnnoPubblicazioneDesc();

    // Libri per genere (esatto)
    List<Libro> findByGenere(String genere);
}
