package it.uniroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.model.Utente;
import it.uniroma3.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class RegistrazioneController {

    @Autowired
    private UtenteService utenteService;

    // Mostra il modulo di registrazione
    @GetMapping("/registrazione")
    public String mostraRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "registrazione";
    }

    // Gestisce l'invio del form
@PostMapping("/registrazione")
public String registraUtente(
        @Valid @ModelAttribute("utente") Utente utente,
        BindingResult errors,
        @RequestParam String passwordBis,
        Model model) {

    // Controlla errori di validazione nei campi base
    if (errors.hasErrors()) {
        return "registrazione";
    }

    // Controllo manuale della conferma password
    if (!utente.getPassword().equals(passwordBis)) {
        model.addAttribute("errore", "Le password non coincidono");
        return "registrazione";
    }

    // Verifica se l'email è già in uso
    if (utenteService.getUtenteByEmail(utente.getEmail()).isPresent()) {
        model.addAttribute("errore", "Email già registrata");
        return "registrazione";
    }

    // Imposta sempre ruolo USER per i nuovi registrati
    utente.setRuolo(Utente.Ruolo.USER);

    // Salva utente nel database
    try {
        utenteService.addUtente(utente);
    } catch (RuntimeException e) {
        model.addAttribute("errore", "Errore durante la registrazione: " + e.getMessage());
        return "registrazione";
    }

    // Reindirizza al login con messaggio di successo
    return "redirect:/login?registrazione=successo";
}

}
