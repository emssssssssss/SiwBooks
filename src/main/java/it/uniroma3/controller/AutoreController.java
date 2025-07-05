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


import it.uniroma3.model.Autore;
import it.uniroma3.service.AutoreService;
import jakarta.validation.Valid;

@Controller
public class AutoreController {


    @Autowired
    private AutoreService autoreService;

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
        if (bindingResult.hasErrors()) {
            return "formAutore";
        }

        autore.setId(null);

        if (!fotoFile.isEmpty()) {
            try {
                // Percorso assoluto alla cartella /static/images/autori
                Path staticImagesPath = Paths.get(System.getProperty("user.dir"),
                                                "src", "main", "resources", "static", "images", "autori");

                Files.createDirectories(staticImagesPath);

                String filename = UUID.randomUUID() + "_" + fotoFile.getOriginalFilename().replaceAll("\\s+", "_");
                Path filePath = staticImagesPath.resolve(filename);
                fotoFile.transferTo(filePath.toFile());

                // URL per visualizzarla nel browser
                autore.setFotoUrl("/images/autori/" + filename);

            } catch (IOException ex) {
                ex.printStackTrace();
                return "redirect:/autori/admin/nuovo?error=uploadFail";
            }
        }

        autoreService.save(autore);
        return "redirect:/autori/" + autore.getId();
    }


}
