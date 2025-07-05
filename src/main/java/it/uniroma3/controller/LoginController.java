package it.uniroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostra la pagina di login
    @GetMapping("/login")
    public String login(@RequestParam(name = "redirect", required = false) String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }

    // Mostra la pagina di login in caso di errore
    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("errore", "Credenziali non valide");
        return "login";
    }

    // Mostra il form di registrazione
    @GetMapping("/registrazione")
    public String mostraRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "registrazione";
    }

    // Gestione POST della registrazione
    @PostMapping("/registrazione")
    public String registrazione(@Valid @ModelAttribute("utente") Utente utente,
                                 BindingResult bindingResult,
                                 @RequestParam String passwordBis,
                                 @RequestParam(required = false) String codiceAmministratore,
                                 Model model,
                                 HttpServletRequest request) {

        // Verifica password e conferma
        if (!utente.getPassword().equals(passwordBis)) {
            bindingResult.rejectValue("password", "error.utente", "Le password non coincidono");
        }

        // Unicità username
        if (utenteService.getUtenteByUsername(utente.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.utente", "Username già in uso");
        }

        // Unicità email
        if (utenteService.getUtenteByEmail(utente.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.utente", "Email già in uso");
        }

        // Se ci sono errori, torna alla pagina
        if (bindingResult.hasErrors()) {
            return "registrazione";
        }

        // Assegna ruolo
        utente.setRuolo(utenteService.isAmministratore(codiceAmministratore)
                        ? Utente.Ruolo.ADMIN
                        : Utente.Ruolo.USER);

        // Codifica password
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));

        try {
            utenteService.addUtente(utente);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errore", "Username o email già in uso");
            return "registrazione";
        } catch (Exception e) {
            model.addAttribute("errore", "Errore durante la registrazione: " + e.getMessage());
            return "registrazione";
        }

        // Auto-login dopo registrazione
        UserDetails userDetails = userDetailsService.loadUserByUsername(utente.getUsername());
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                             SecurityContextHolder.getContext());

        return "redirect:/home";
    }
}
