package it.uniroma3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.model.Libro;
import it.uniroma3.model.Recensione;
import it.uniroma3.model.Utente;
import it.uniroma3.service.LibroService;
import it.uniroma3.service.RecensioneService;
import it.uniroma3.service.UtenteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/recensioni")
public class RecensioneController {
    @Autowired
    private RecensioneService recensioneService;

    @Autowired
    private LibroService libroService;

     @Autowired
    private UtenteService utenteService;

    // Aggiunta recensione (POST /recensioni/libro/{id})
    @PostMapping("/libro/{id}")
    public String aggiungiRecensione(@PathVariable Long id,
                                     @Valid @ModelAttribute("recensione") Recensione recensione,
                                     BindingResult bindingResult,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/libri/" + id + "?error=validation";
        }

        Optional<Libro> libroOpt = libroService.findById(id);
        Optional<Utente> utenteOpt = utenteService.getUtenteByUsername(userDetails.getUsername());

        if (libroOpt.isPresent() && utenteOpt.isPresent()) {
            Libro libro = libroOpt.get();
            Utente utente = utenteOpt.get();

            boolean giàRecensito = libro.getRecensioni().stream()
                    .anyMatch(r -> r.getAutore().getId().equals(utente.getId()));

            if (giàRecensito) {
                return "redirect:/libri/" + id + "?error=giàRecensito";
            }

            recensione.setLibro(libro);
            recensione.setAutore(utente);
            recensioneService.save(recensione);
        }

        return "redirect:/libri/" + id;
    }

    // Elimina recensione (solo admin)
    @PostMapping("/{id}/elimina")
    public String eliminaRecensione(@PathVariable Long id,
                                    @RequestParam("libroId") Long libroId) {
        recensioneService.deleteById(id);
        return "redirect:/libri/" + libroId;
    }
}
