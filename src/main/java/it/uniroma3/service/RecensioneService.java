package it.uniroma3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Recensione;
import it.uniroma3.repository.RecensioneRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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

    public void save(Recensione recensione) {
        if (recensione.getId() != null) {
            throw new IllegalStateException("Non puoi riusare una recensione già esistente");
        }
        recensioneRepository.save(recensione);
    }

    public void deleteById(Long id) {
        recensioneRepository.deleteById(id);
    }

    @Transactional
    public Recensione aggiornaRecensione(Recensione nuovaRecensione) {
        Recensione recensioneDB = recensioneRepository.findById(nuovaRecensione.getId())
                .orElseThrow(() -> new EntityNotFoundException("Recensione non trovata"));

        recensioneDB.setTesto(nuovaRecensione.getTesto());
        recensioneDB.setTitolo(nuovaRecensione.getTitolo());
        recensioneDB.setVoto(nuovaRecensione.getVoto());

        return recensioneRepository.save(recensioneDB);
    }

    public boolean esisteRecensionePerLibroEAutore(long id, Long id2) {
        return recensioneRepository.existsByLibroIdAndUtenteId(id, id2);
    }

}
