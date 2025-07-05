package it.uniroma3.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.model.Libro;
import it.uniroma3.model.Recensione;
import it.uniroma3.model.Utente;
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


    @GetMapping("/libro/{id}")
    public String dettagliLibro(@PathVariable Long id,
                                Model model,
                                @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Libro> libroOpt = libroService.findByIdWithAutori(id);
        if (libroOpt.isEmpty()) {
            return "redirect:/libri?error=LibroNonTrovato";
        }

        Libro libro = libroOpt.get();
        model.addAttribute("libro", libro);

        boolean haGiaRecensito = false;
        boolean haGiaLetto = false;

        if (userDetails != null) {
            Optional<Utente> utenteOpt = utenteService.getUtenteByUsername(userDetails.getUsername());
            if (utenteOpt.isPresent()) {
                Utente utente = utenteOpt.get();

                // Verifica se l'utente ha già scritto una recensione per questo libro
                haGiaRecensito = libro.getRecensioni().stream()
                    .anyMatch(r -> r.getAutore().getId().equals(utente.getId()));

                // Verifica se l'utente ha già segnato il libro come letto
                haGiaLetto = utente.getLibriLetti().stream()
                    .anyMatch(l -> l.getId() == libro.getId());

            }
        }

        model.addAttribute("haGiaRecensito", haGiaRecensito);
        model.addAttribute("haGiaLetto", haGiaLetto);

        // Passa un oggetto recensione vuoto per il form, se serve
        model.addAttribute("recensione", new Recensione());

        return "libro";
    }


    @PostMapping("/libro/{id}/letto")
    public String segnaComeLetto(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Optional<Libro> libroOpt = libroService.findById(id);
            Optional<Utente> utenteOpt = utenteService.getUtenteByUsername(userDetails.getUsername());

            if (libroOpt.isPresent() && utenteOpt.isPresent()) {
                Utente utente = utenteOpt.get();
                utente.aggiungiLibroLetto(libroOpt.get());
                utenteService.save(utente);
            }
        }
        return "redirect:/libro/" + id;
    }

    @PostMapping("/libro/{id}/non-letto")
    public String segnaComeNonLetto(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Optional<Libro> libroOpt = libroService.findById(id);
            Optional<Utente> utenteOpt = utenteService.getUtenteByUsername(userDetails.getUsername());

            if (libroOpt.isPresent() && utenteOpt.isPresent()) {
                Utente utente = utenteOpt.get();
                Libro libro = libroOpt.get();

                utenteService.rimuoviLibroLetto(utente, libro);
            }
        }
        return "redirect:/libro/" + id;
    }




    // Aggiunta (solo admin) — opzionale
    @GetMapping("/admin/nuovo")
    public String mostraFormLibro(Model model) {
        model.addAttribute("libro", new Libro());
        model.addAttribute("autori", autoreService.findAll());
        return "formLibro";
    }

    @PostMapping("/admin/nuovo")
    public String salvaLibro(@ModelAttribute Libro libro,
                            @RequestParam("copertinaFile") MultipartFile copertinaFile) {
        try {
            if (!copertinaFile.isEmpty()) {
                String folder = "src/main/resources/static/images";
                byte[] bytes = copertinaFile.getBytes();
                Path path = Paths.get(folder + copertinaFile.getOriginalFilename());
                Files.write(path, bytes);

                // Controlla se la lista immagini è null, crea nuova
                if (libro.getImmagini() == null) {
                    libro.setImmagini(new ArrayList<>());
                }
                // Aggiungi la nuova immagine (path relativo)
                libro.getImmagini().add("/images" + copertinaFile.getOriginalFilename());
            }
            libroService.save(libro);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/nuovo?error=uploadFallito";
        }
        return "redirect:/libri";
    }

    // Eliminazione (solo admin) — opzionale
    @PostMapping("/{id}/admin/elimina")
    public String eliminaLibro(@PathVariable Long id) {
        libroService.deleteById(id);
        return "redirect:/libri";
    }
}
