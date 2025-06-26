package it.uniroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.model.Utente;
import it.uniroma3.service.CustomUserDetailsService;
import it.uniroma3.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    // Mostra pagina login e gestisce errori/logout tramite parametri URL
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errore", "Credenziali non valide");
        }
        if (logout != null) {
            model.addAttribute("message", "Logout effettuato con successo");
        }
        return "login";  
    }

    @GetMapping("/registrazione")
    public String mostraRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "registrazione"; 
    }

        // Gestisce il form di registrazione
    @PostMapping("/registrazione")
    public String registrazione(@Valid @ModelAttribute("utente") Utente utente,
                                BindingResult errors,
                                @RequestParam String passwordBis,
                                @RequestParam(required = false) String codiceAmministratore,
                                Model model,
                                HttpServletRequest request) {

        if (errors.hasErrors()) {
            // Riporta oggetto utente con errori e ritorna form
            model.addAttribute("utente", utente);
            return "registrazione";
        }

        // Controllo manuale password conferma
        if (!utente.getPassword().equals(passwordBis)) {
            model.addAttribute("errore", "Le password non coincidono");
            model.addAttribute("utente", utente);
            return "registrazione";
        }

        // Controlla se l'email è già registrata
        if (utenteService.getUtenteByEmail(utente.getEmail()).isPresent()) {
            model.addAttribute("errore", "Email già registrata");
            model.addAttribute("utente", utente);
            return "registrazione";
        }

        // Codifica la password prima di salvare
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));

        // Assegna ruolo in base al codice admin
        if (utenteService.isAmministratore(codiceAmministratore)) {
            utente.setRuolo(Utente.Ruolo.ADMIN);
        } else {
            utente.setRuolo(Utente.Ruolo.USER);
        }

        try {
            utenteService.addUtente(utente);
        } catch (RuntimeException e) {
            model.addAttribute("errore", "Errore durante la registrazione: " + e.getMessage());
            model.addAttribute("utente", utente);
            return "registrazione";
        }


        // Auto-login dell'utente registrato
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(utente.getEmail());
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "redirect:/home";

    }
    
}
