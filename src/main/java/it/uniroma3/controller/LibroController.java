package it.uniroma3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.model.Libro;
import it.uniroma3.model.Recensione;
import it.uniroma3.service.AutoreService;
import it.uniroma3.service.LibroService;
import it.uniroma3.service.UtenteService;
;

@Controller
public class LibroController {
    @Autowired
    private LibroService libroService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AutoreService autoreService;

    // Mostra elenco libri
    @GetMapping("/libri")
    public String mostraLibri(@RequestParam(required = false) String titolo,
                            @RequestParam(required = false) String genere,
                            @RequestParam(required = false) String autore,
                            Model model) {

        model.addAttribute("titolo", titolo);
        model.addAttribute("genere", genere);
        model.addAttribute("autore", autore);

        model.addAttribute("libri", libroService.ricercaLibri(titolo, genere, autore));
        return "libri";
    }

    @GetMapping("/libri/ricerca")
    public String ricercaLibri(@RequestParam(required = false) String titolo,
                            @RequestParam(required = false) String genere,
                            @RequestParam(required = false) String autore,
                            Model model) {
        List<Libro> risultati = libroService.ricercaLibri(titolo, genere, autore);
        model.addAttribute("libri", risultati);
        return "libri";
    }


    // Mostra dettagli di un libro, incluse le recensioni
    @GetMapping("/libro/{id}")
    public String dettagliLibro(@PathVariable Long id,
                                Model model,
                                @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Libro> libroOpt = libroService.findById(id);
        if (libroOpt.isEmpty()) {
            return "redirect:/libri?error=LibroNonTrovato";
        }

        Libro libro = libroOpt.get();
        model.addAttribute("libro", libro);

        // Per mostrare il form di nuova recensione se l'utente è loggato
        if (userDetails != null) {
            var utenteOpt = utenteService.getUtenteByUsername(userDetails.getUsername());
            if (utenteOpt.isPresent()) {
                boolean haGiaRecensito = libro.getRecensioni().stream()
                        .anyMatch(r -> r.getAutore().getId().equals(utenteOpt.get().getId()));

                model.addAttribute("haGiaRecensito", haGiaRecensito);
                model.addAttribute("recensione", new Recensione());
            }
        }

        return "libro";
    }

    // Aggiunta (solo admin) — opzionale
    @GetMapping("/admin/nuovo")
    public String mostraFormLibro(Model model) {
        model.addAttribute("libro", new Libro());
        model.addAttribute("autori", autoreService.findAll());
        return "formLibro";
    }

    @PostMapping("/admin")
    public String salvaLibro(@ModelAttribute Libro libro) {
        libroService.save(libro);
        return "redirect:/libri";
    }

    // Eliminazione (solo admin) — opzionale
    @PostMapping("/{id}/admin/elimina")
    public String eliminaLibro(@PathVariable Long id) {
        libroService.deleteById(id);
        return "redirect:/libri";
    }
}
