package it.uniroma3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.model.Libro;
import it.uniroma3.service.LibroService;

@Controller
public class HomeController {

    @Autowired
    private LibroService libroService;

    @GetMapping({"/","/home"})
    public String homepage(Model model) {
        List<Libro> fantasy = libroService.findByGenere("Fantasy");
        List<Libro> thriller = libroService.findByGenere("Thriller");

        model.addAttribute("fantasy", fantasy);
        model.addAttribute("thriller", thriller);
        return "home";
    }
}
