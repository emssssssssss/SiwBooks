package it.uniroma3.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Libro;
import it.uniroma3.repository.LibroRepository;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }

    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }

    public List<Libro> findUltimiLibri() {
        return libroRepository.findTop6ByOrderByAnnoPubblicazioneDesc();
    }

    public List<Libro> findByGenere(String genere) {
        return libroRepository.findByGenere(genere);
    }

    public List<Libro> ricercaLibri(String titolo, String genere, String autore) {
        if ((titolo == null || titolo.isEmpty()) &&
            (genere == null || genere.isEmpty()) &&
            (autore == null || autore.isEmpty())) {
            return libroRepository.findAll();
        }

        return libroRepository.findByTitoloGenereAutore(titolo, genere, autore);
    }

     public Optional<Libro> findByIdWithAutori(Long id) {
        return libroRepository.findByIdWithAutori(id);
    }

}
