package it.uniroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.model.Utente;
import it.uniroma3.service.UtenteService;


@Controller
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    // Visualizza profilo dell'utente loggato
    @GetMapping("/profilo")
    public String profiloUtente(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Utente utente = utenteService.getUtenteByUsername(userDetails.getUsername()).orElse(null);
        if (utente == null) return "redirect:/login";
        model.addAttribute("utente", utente);
        return "profilo";
    }
}
