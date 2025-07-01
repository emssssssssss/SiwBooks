package it.uniroma3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.model.Autore;

public interface AutoreRepository extends JpaRepository<Autore, Long>{
    @Query("SELECT a FROM Autore a LEFT JOIN FETCH a.libri WHERE a.id = :id")
    Optional<Autore> findByIdWithLibri(@Param("id") Long id);
}
