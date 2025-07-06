package it.uniroma3.controller;

//import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
//import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.authentication.AppProperties;
import it.uniroma3.model.Autore;
import it.uniroma3.model.Libro;
import it.uniroma3.model.Recensione;
import it.uniroma3.model.Utente;
import it.uniroma3.service.AutoreService;
import it.uniroma3.service.LibroService;
import it.uniroma3.service.UtenteService;
import jakarta.validation.Valid;
;

@Controller
public class LibroController {
    @Autowired
    private LibroService libroService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AutoreService autoreService;

    @Autowired
    private AppProperties props;

    // Mostra elenco libri
    @GetMapping("/libri")
    public String mostraLibri(@RequestParam(required = false) String titolo,
                            @RequestParam(required = false) String genere,
                            @RequestParam(required = false) String autore,
                            Model model) {

        model.addAttribute("titolo", titolo);
        model.addAttribute("genere", genere);
        model.addAttribute("autore", autore);
        model.addAttribute("recensione", new Recensione());

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

        Utente utenteCorrente = null;

        if (userDetails != null) {
            Optional<Utente> utenteOpt = utenteService.getUtenteByUsername(userDetails.getUsername());
            if (utenteOpt.isPresent()) {
                utenteCorrente = utenteOpt.get();
            }
        }
        model.addAttribute("utenteCorrente", utenteCorrente);

        boolean haGiaRecensito = false;
        boolean haGiaLetto = false;

        if (utenteCorrente != null) {
            final Utente finalUtente = utenteCorrente;  // variabile finale per le lambda

            haGiaRecensito = libro.getRecensioni().stream()
                .anyMatch(r -> r.getUtente().getId().equals(finalUtente.getId()));

            haGiaLetto = finalUtente.getLibriLetti().stream()
                .anyMatch(l -> java.util.Objects.equals(l.getId(), libro.getId()));
        }

        model.addAttribute("haGiaRecensito", haGiaRecensito);
        model.addAttribute("haGiaLetto", haGiaLetto);

        model.addAttribute("recensione", new Recensione());
        model.addAttribute("recensioni", libro.getRecensioni());

        return "libro";
    }

    @PostMapping("/admin/libro/nuovo")
    public String salvaLibro(@ModelAttribute Libro libro,
                            @RequestParam("immaginiFiles") List<MultipartFile> immaginiFiles,
                            @RequestParam("autori") List<Long> autoriIds) {
        try {
            // 1) Recupera gli Autori e costruisci un Set
            Set<Autore> autoriSelezionati = new HashSet<>(autoreService.findAllById(autoriIds));
            libro.setAutori(autoriSelezionati);

            // 2) Prepara la directory di upload
            Path uploadDir = Paths.get(props.getUploadDir());
            Files.createDirectories(uploadDir);

            // 3) Inizializza la lista di immagini, se serve
            if (libro.getImmagini() == null) {
                libro.setImmagini(new ArrayList<>());
            }

            // 4) Salva ciascun file e aggiungi l’URL
            for (MultipartFile file : immaginiFiles) {
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    String sanitizedFilename = (originalFilename != null)
                        ? originalFilename.replaceAll("\\s+", "_")
                        : "image";
                    String filename = UUID.randomUUID() + "_" + sanitizedFilename;
                    Path filePath = uploadDir.resolve(filename);
                    file.transferTo(filePath.toFile());
                    libro.getImmagini().add("/uploads/images/" + filename);
                }
            }

            // 5) Salva il libro (con Set<Autore>)
            libroService.save(libro);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/libro/nuovo?error=uploadFallito";
        }

        return "redirect:/libri";
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
    @GetMapping("/admin/libro/nuovo")
    public String mostraFormLibro(Model model) {
        model.addAttribute("libro", new Libro());
        model.addAttribute("autori", autoreService.findAll());

        return "formLibro";
    }


    // Eliminazione (solo admin) — opzionale
    @PostMapping("/admin/libro/{id}/elimina")
    public String eliminaLibro(@PathVariable Long id) {
        libroService.deleteLibroAndCleanup(id);
        return "redirect:/libri";
    }

    @GetMapping("libro/{id}/aggiungiRecensione")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/admin/libro/modifica/{id}")
    public String mostraFormModificaLibro(@PathVariable Long id, Model model) {
        Optional<Libro> libroOpt = libroService.findByIdWithAutori(id);
        if (libroOpt.isEmpty()) {
            return "redirect:/libri";
        }

        model.addAttribute("libro", libroOpt.get());
        model.addAttribute("autori", autoreService.findAll());
        return "formModificaLibro";
    }
    @PostMapping("/admin/libro/modifica/{id}")
    public String modificaLibro(@PathVariable Long id,
                                @Valid @ModelAttribute Libro libro,
                                BindingResult bindingResult,
                                @RequestParam("copertinaFile") MultipartFile copertinaFile,
                                @RequestParam("autori") List<Long> autoriIds,
                                Model model) {

        Optional<Libro> libroEsistenteOpt = libroService.findById(id);
        if (libroEsistenteOpt.isEmpty()) {
            return "redirect:/libri";
        }

        Libro libroEsistente = libroEsistenteOpt.get();
        libro.setId(id);

        // Imposta gli autori come Set, non List
        Set<Autore> autoriSelezionati = new HashSet<>(autoreService.findAllById(autoriIds));
        libro.setAutori(autoriSelezionati);

        // Mantieni URL copertina esistente se non ne carichi una nuova
        String urlEsistente = libroEsistente.getUrlCopertina();

        try {
            Path uploadDir = Paths.get(props.getUploadDir());
            Files.createDirectories(uploadDir);

            if (copertinaFile != null && !copertinaFile.isEmpty()) {
                String originalFilename = copertinaFile.getOriginalFilename();
                String sanitizedFilename = (originalFilename != null)
                    ? originalFilename.replaceAll("\\s+", "_") 
                    : "cover";
                String filename = UUID.randomUUID() + "_" + sanitizedFilename;
                Path filePath = uploadDir.resolve(filename);
                copertinaFile.transferTo(filePath.toFile());
                libro.setUrlCopertina("/uploads/images/" + filename);
            } else {
                libro.setUrlCopertina(urlEsistente);
            }

            libroService.save(libro);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/libro/modifica/" + id + "?error=upload";
        }

        return "redirect:/libro/" + id;
    }



    
}
