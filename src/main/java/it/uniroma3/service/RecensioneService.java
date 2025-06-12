package it.uniroma3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Recensione;
import it.uniroma3.repository.RecensioneRepository;

@Service
public class RecensioneService {
    @Autowired
    private RecensioneRepository recensioneRepository;

    public List<Recensione> findAll() {
        return recensioneRepository.findAll();
    }

    public Optional<Recensione> findById(Long id) {
        return recensioneRepository.findById(id);
    }

    public Recensione save(Recensione recensione) {
        return recensioneRepository.save(recensione);
    }

    public void deleteById(Long id) {
        recensioneRepository.deleteById(id);
    }
}
