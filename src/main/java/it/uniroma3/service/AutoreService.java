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
public class AutoreService {
    @Autowired
    private AutoreRepository autoreRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public List<Autore> findAll() {
        return autoreRepository.findAll();
    }

    public List<Autore> findAllById(List<Long> ids) {
        return autoreRepository.findAllById(ids);
    }

    public Optional<Autore> findById(Long id) {
        return autoreRepository.findById(id);
    }

    public Autore save(Autore autore) {
        if (autore.getLibri() != null) {
            for (Libro libro : autore.getLibri()) {
                if (!libro.getAutori().contains(autore)) {
                    libro.getAutori().add(autore);  
                }
            }
        }
        return autoreRepository.save(autore);
    }

    public void deleteById(Long id) {
        autoreRepository.deleteById(id);
    }

    public Optional<Autore> findByIdWithLibri(Long id) {
        return autoreRepository.findByIdWithLibri(id);
    }

    @Transactional
    public void deleteAutoreAndLibri(Long autoreId) {
        // 1) Carica autore insieme ai libri
        Autore autore = autoreRepository.findByIdWithLibri(autoreId)
            .orElseThrow(() -> new EntityNotFoundException("Autore non trovato"));

        // 2) Per ciascun libro:
        for (Libro libro : autore.getLibri()) {
            // 2a) Rimuovi l’autore dalla join autore_libri
            libro.getAutori().remove(autore);

            // 2b) Rimuovi TUTTE le associazioni “letto” in utente_libri_letti
            if (libro.getLettori() != null) {
                for (Utente u : new ArrayList<>(libro.getLettori())) {
                    u.getLibriLetti().remove(libro);
                    utenteRepository.save(u);
                }
            }
        }

        // 3) Elimina tutti i libri fino ad ora “sganciati”
        libroRepository.deleteAll(autore.getLibri());

        // 4) Elimina l’autore
        autoreRepository.delete(autore);
    }
}
