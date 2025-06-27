package it.uniroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.model.Utente;
import it.uniroma3.service.CustomUserDetailsService;
import it.uniroma3.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Mostra pagina login e gestisce errori/logout tramite parametri URL
    /*@GetMapping("/login")
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
    }*/

    @GetMapping("/login")
    public String login(@RequestParam(name = "redirect", required = false) String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login"; // il template login.html
    }

    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("errore", "Credenziali non valide");
        return "login";
    }

    @GetMapping("/registrazione")
    public String mostraRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "registrazione"; 
    }



    @PostMapping("/registrazione")
    public String registrazione(@RequestParam String email,
                                @RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String passwordBis,
                                @RequestParam(required = false) String codiceAmministratore,
                                Model model,
                                HttpServletRequest request) {

        if (!password.equals(passwordBis)) {
            model.addAttribute("errore", "Le password non coincidono");
            return "registrazione";
        }

        Utente utente = new Utente();
        utente.setEmail(email);
        utente.setUsername(username);
        utente.setPassword(passwordEncoder.encode(password));
        utente.setRuolo(utenteService.isAmministratore(codiceAmministratore)
                        ? Utente.Ruolo.ADMIN
                        : Utente.Ruolo.USER);

        try {
            utenteService.addUtente(utente);
        } catch (Exception e) {
            model.addAttribute("errore", "Errore durante la registrazione: " + e.getMessage());
            return "registrazione";
        }

        // Auto-login
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                             SecurityContextHolder.getContext());

        return "redirect:/home";
    }
    
}
