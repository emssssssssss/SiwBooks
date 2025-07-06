package it.uniroma3.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Autore;
import it.uniroma3.model.Libro;
import it.uniroma3.model.Utente;
import it.uniroma3.repository.AutoreRepository;
import it.uniroma3.repository.LibroRepository;
import it.uniroma3.repository.UtenteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired 
    private AutoreRepository autoreRepository;

    @Autowired 
    private UtenteRepository utenteRepository;

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

    @Transactional
    public void deleteLibroAndCleanup(Long libroId) {
        // 1) Carica libro + associations
        Libro libro = libroRepository.findByIdWithAutoriAndLettori(libroId)
                       .orElseThrow(() -> new EntityNotFoundException("Libro non trovato"));

        // 2) Stacca dagli autori
        for (Autore a : new ArrayList<>(libro.getAutori())) {
            a.getLibri().remove(libro);
            autoreRepository.save(a);
        }

        // 3) Stacca dagli utenti che l'hanno letto
        for (Utente u : new ArrayList<>(libro.getLettori())) {
            u.getLibriLetti().remove(libro);
            utenteRepository.save(u);
        }

        // 4) Le recensioni verranno cascade-delete
        // 5) Ora cancella il libro
        libroRepository.delete(libro);
    }

}
