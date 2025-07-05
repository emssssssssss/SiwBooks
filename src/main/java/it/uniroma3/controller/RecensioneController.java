package it.uniroma3.controller;

import java.security.Principal;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import it.uniroma3.model.Libro;
import it.uniroma3.model.Recensione;
import it.uniroma3.model.Utente;
import it.uniroma3.repository.LibroRepository;
import it.uniroma3.repository.UtenteRepository;
import it.uniroma3.service.RecensioneService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/libro")
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private LibroRepository libroRepository;

    @PostMapping("/{id}/recensisci")
    public String inserisciRecensione(@PathVariable Long id,
            @Valid @ModelAttribute("recensione") Recensione recensioneForm,
            BindingResult bindingResult,
            Principal principal,
            Model model) {

        // prendi libro e utente
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro non trovato"));
        Utente utente = utenteRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));

        // se validazione fallisce, ripopola la pagina
        if (bindingResult.hasErrors()) {
            model.addAttribute("libro", libro);
            model.addAttribute("recensioni", libro.getRecensioni());
            model.addAttribute("haGiaRecensito",
                    recensioneService.esisteRecensionePerLibroEAutore(id, utente.getId()));
            model.addAttribute("haGiaLetto", utente.getLibriLetti().contains(libro));
            return "libro";
        }

        // blocca duplicati
        if (recensioneService.esisteRecensionePerLibroEAutore(id, utente.getId())) {
            model.addAttribute("libro", libro);
            model.addAttribute("recensioni", libro.getRecensioni());
            model.addAttribute("haGiaRecensito", true);
            model.addAttribute("haGiaLetto", utente.getLibriLetti().contains(libro));
            return "libro";
        }

        // crea nuova recensione, evita di usare quella del form
        Recensione nuovaRecensione = new Recensione();
        nuovaRecensione.setTitolo(recensioneForm.getTitolo());
        nuovaRecensione.setTesto(recensioneForm.getTesto());
        nuovaRecensione.setVoto(recensioneForm.getVoto());
        nuovaRecensione.setLibro(libro);
        nuovaRecensione.setUtente(utente);

        recensioneService.save(nuovaRecensione);

        return "redirect:/libro/" + id;
    }

    // POST /libro/{id}/elimina (se necessario)
    @PostMapping("/{id}/elimina")
    public String eliminaRecensione(
            @PathVariable Long id,
            @RequestParam("libroId") Long libroId,
            Principal principal) {

        // se non serve, commenta o rimuovi questo metodo
        return "redirect:/libro/" + libroId;
    }
}
