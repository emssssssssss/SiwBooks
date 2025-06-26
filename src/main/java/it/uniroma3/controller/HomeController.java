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
        List<Libro> romance = libroService.findByGenere("Romance");
        List<Libro> horror = libroService.findByGenere("Horror");

        model.addAttribute("fantasy", fantasy);
        model.addAttribute("thriller", thriller);
        model.addAttribute("romance", romance);
        model.addAttribute("horror", horror);
        return "home";
    }
}
