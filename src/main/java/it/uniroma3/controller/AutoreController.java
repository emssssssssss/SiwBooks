package it.uniroma3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.model.Autore;
import it.uniroma3.service.AutoreService;


@Controller
@RequestMapping("/autori")
public class AutoreController {
    @Autowired
    private AutoreService autoreService;

    @GetMapping
    public String elencoAutori(Model model) {
        List<Autore> autori = autoreService.findAll();
        model.addAttribute("autori", autori);
        return "autori/lista";
    }

    @GetMapping("/{id}")
    public String dettaglioAutore(@PathVariable Long id, Model model) {
        Optional<Autore> autoreOpt = autoreService.findById(id);
        if (autoreOpt.isEmpty())
            return "redirect:/autori";

        model.addAttribute("autore", autoreOpt.get());
        return "autori/dettagli";
    }

    @GetMapping("/admin/nuovo")
    public String formNuovoAutore(Model model) {
        model.addAttribute("autore", new Autore());
        return "autori/form";
    }

    @PostMapping("/admin/nuovo")
    public String salvaAutore(@ModelAttribute Autore autore) {
        autoreService.save(autore);
        return "redirect:/autori";
    }

    @PostMapping("/admin/{id}/elimina")
    public String eliminaAutore(@PathVariable Long id) {
        autoreService.deleteById(id);
        return "redirect:/autori";
    }
}
