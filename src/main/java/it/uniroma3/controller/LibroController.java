package it.uniroma3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.model.Libro;
import it.uniroma3.model.Recensione;
import it.uniroma3.service.LibroService;
import it.uniroma3.service.UtenteService;
;

public class LibroController {
        @Autowired
    private LibroService libroService;

    @Autowired
    private UtenteService utenteService;

    // Mostra elenco libri
    @GetMapping
    public String elencoLibri(Model model) {
        model.addAttribute("libri", libroService.findAll());
        return "libri/elenco";
    }

    // Mostra dettagli di un libro, incluse le recensioni
    @GetMapping("/{id}")
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

        return "libri/dettagli";
    }

    // Aggiunta (solo admin) — opzionale
    @GetMapping("/admin/nuovo")
    public String mostraFormLibro(Model model) {
        model.addAttribute("libro", new Libro());
        return "libri/form";
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
