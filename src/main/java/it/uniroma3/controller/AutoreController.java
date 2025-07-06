package it.uniroma3.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.authentication.AppProperties;
import it.uniroma3.model.Autore;
import it.uniroma3.service.AutoreService;
import jakarta.validation.Valid;

@Controller
public class AutoreController {


    @Autowired
    private AutoreService autoreService;

    @Autowired
    private AppProperties props;

    @GetMapping("/autori")
    public String elencoAutori(Model model) {
        List<Autore> autori = autoreService.findAll();
        model.addAttribute("autori", autori);
        return "autori";
    }

    @GetMapping("/autori/{id}")
    public String dettaglioAutore(@PathVariable Long id, Model model) {
        Optional<Autore> autoreOpt = autoreService.findByIdWithLibri(id);
        if (autoreOpt.isEmpty()) {
            return "redirect:/autori";
        }
        model.addAttribute("autore", autoreOpt.get());
        return "autore";
    }

    @GetMapping("/autori/admin/nuovo")
    public String formNuovoAutore(Model model) {
        model.addAttribute("autore", new Autore());
        return "formAutore";
    }

    @PostMapping("/autori/admin/salva")
    public String salva(
        @Valid Autore autore,
        BindingResult bindingResult,
        @RequestParam("fotoFile") MultipartFile fotoFile,
        Model model
    ) {
        // Se ci sono errori di validazione, torna al form
        if (bindingResult.hasErrors()) {
            return "formAutore";
        }

        // Assicura che l'autore abbia un ID nullo per creare un nuovo record
        autore.setId(null);

        // Se è stata caricata una foto
        if (!fotoFile.isEmpty()) {
            try {
                // Directory di upload da application.properties
                Path uploadDir = Paths.get(props.getUploadDir());
                Files.createDirectories(uploadDir); // crea se non esiste

                // Crea un nome file unico e sicuro
                String originalFilename = fotoFile.getOriginalFilename();
                String sanitizedFilename = (originalFilename != null) 
                    ? originalFilename.replaceAll("\\s+", "_") 
                    : "image";
                String filename = UUID.randomUUID() + "_" + sanitizedFilename;

                Path filePath = uploadDir.resolve(filename);

                // Salva il file fisicamente nel filesystem
                fotoFile.transferTo(filePath.toFile());

                // Imposta l'URL della foto per l'autore
                autore.setFotoUrl("/uploads/images/" + filename);

            } catch (IOException ex) {
                ex.printStackTrace();
                // Redireziona al form con messaggio di errore
                return "redirect:/autori/admin/nuovo?error=uploadFail";
            }
        }

        // Salva l'autore nel database
        autoreService.save(autore);

        // Reindirizza alla pagina dell'autore appena creato
        return "redirect:/autori/" + autore.getId();
    }


    @PostMapping("/admin/autore/elimina/{id}")
    public String eliminaAutore(@PathVariable Long id) {
        Optional<Autore> autoreOpt = autoreService.findById(id);

        if (autoreOpt.isPresent()) {
            Autore autore = autoreOpt.get();

            // Elimina fisicamente l'immagine se presente
            String fotoUrl = autore.getFotoUrl();
            if (fotoUrl != null && !fotoUrl.isEmpty()) {
                try {
                    // Rimuove il prefisso "/uploads/images/" dall'URL
                    String fileName = fotoUrl.replace("/uploads/images/", "");
                    Path filePath = Paths.get(props.getUploadDir(), fileName);
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    e.printStackTrace(); // Logga l'errore ma continua comunque
                }
            }

            autoreService.deleteAutoreAndLibri(id);
        }


        return "redirect:/autori";
    }


    @GetMapping("/autori/admin/modifica/{id}")
    public String modificaAutoreForm(@PathVariable Long id, Model model) {
        Optional<Autore> autoreOpt = autoreService.findById(id);
        if (autoreOpt.isPresent()) {
            model.addAttribute("autore", autoreOpt.get());
            return "formAutore";
        } else {
            return "redirect:/autori";
        }
    }

    @PostMapping("/admin/autori/modifica/{id}")
    public String modificaAutore(
            @PathVariable Long id,
            @Valid Autore autore,
            BindingResult bindingResult,
            @RequestParam("fotoFile") MultipartFile fotoFile,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "formAutore";
        }

        Optional<Autore> esistente = autoreService.findById(id);
        if (esistente.isEmpty()) {
            return "redirect:/autori";
        }

        Autore vecchio = esistente.get();

        // Imposta manualmente l'ID per assicurarsi che stiamo modificando l'esistente
        autore.setId(id);

        // Mantiene la foto esistente se non ne viene caricata una nuova
        if (!fotoFile.isEmpty()) {
            try {
                Path uploadDir = Paths.get(props.getUploadDir());
                Files.createDirectories(uploadDir);
                String originalFilename = fotoFile.getOriginalFilename();
                String sanitizedFilename = (originalFilename != null) ? originalFilename.replaceAll("\\s+", "_") : "image";
                String filename = UUID.randomUUID() + "_" + sanitizedFilename;
                Path filePath = uploadDir.resolve(filename);
                fotoFile.transferTo(filePath.toFile());

                // elimina vecchia foto
                String oldFotoUrl = vecchio.getFotoUrl();
                if (oldFotoUrl != null) {
                    Path oldFile = Paths.get(props.getUploadDir(), oldFotoUrl.replace("/uploads/images/", ""));
                    Files.deleteIfExists(oldFile);
                }

                autore.setFotoUrl("/uploads/images/" + filename);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/autori/admin/modifica/" + id + "?error=upload";
            }
        } else {
            autore.setFotoUrl(vecchio.getFotoUrl());
        }

        autoreService.save(autore);
        return "redirect:/autori/" + autore.getId();
    }




}
