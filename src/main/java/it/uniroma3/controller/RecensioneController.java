package it.uniroma3.controller;

import java.security.Principal;
//import java.util.Optional;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import it.uniroma3.model.Libro;
import it.uniroma3.model.Recensione;
import it.uniroma3.model.Utente;
import it.uniroma3.model.Utente.Ruolo;
import it.uniroma3.repository.LibroRepository;
import it.uniroma3.repository.UtenteRepository;
import it.uniroma3.service.RecensioneService;
import it.uniroma3.service.UtenteService;
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

    @Autowired
    private UtenteService utenteService;

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

    @PostMapping("/{libroId}/recensione/modifica/{id}")
    public String modificaRecensione(@PathVariable Long libroId, @PathVariable Long id,
            @Valid @ModelAttribute Recensione recensioneModificata,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        Optional<Recensione> recOpt = recensioneService.findById(id);
        if (recOpt.isEmpty()) {
            return "redirect:/libro/" + libroId + "?error=RecensioneNonTrovata";
        }

        Recensione rec = recOpt.get();

        Optional<Utente> utenteOpt = utenteService.getUtenteByUsername(userDetails.getUsername());
        if (utenteOpt.isEmpty() || !rec.getUtente().getId().equals(utenteOpt.get().getId())) {
            return "redirect:/libro/" + libroId + "?error=NonAutorizzato";
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/libro/" + libroId + "?error=ValidazioneFallita";
        }

        recensioneModificata.setId(id); // essenziale per l'update
        recensioneService.update(recensioneModificata);

        return "redirect:/libro/" + libroId;
    }



    @PostMapping("/{libroId}/recensione/elimina/{id}")
    public String eliminaRecensione(@PathVariable Long libroId, @PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Recensione> recOpt = recensioneService.findById(id);
        if (recOpt.isEmpty()) {
            return "redirect:/libro/" + libroId + "?error=RecensioneNonTrovata";
        }
        Recensione rec = recOpt.get();

        Optional<Utente> utenteOpt = utenteService.getUtenteByUsername(userDetails.getUsername());
        if (utenteOpt.isEmpty()) {
            return "redirect:/libro/" + libroId + "?error=NonAutorizzato";
        }
        Utente utente = utenteOpt.get();

        if (!rec.getUtente().getId().equals(utente.getId()) && utente.getRuolo() != Ruolo.ADMIN) {
            return "redirect:/libro/" + libroId + "?error=NonAutorizzato";
        }

        recensioneService.deleteById(id);

        return "redirect:/libro/" + libroId;
    }


}
