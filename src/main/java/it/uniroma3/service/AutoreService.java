package it.uniroma3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Autore;
import it.uniroma3.model.Libro;
import it.uniroma3.repository.AutoreRepository;

@Service
public class AutoreService {
    @Autowired
    private AutoreRepository autoreRepository;

    public List<Autore> findAll() {
        return autoreRepository.findAll();
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
}
