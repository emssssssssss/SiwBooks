package it.uniroma3.model;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Il titolo non può essere nullo")
    private String titolo;

    @NotBlank(message = "La descrizione non può essere nulla")
    private String descrizione;

    @NotBlank(message = "Il genere non può essere nullo")
    private String genere;

    @NotNull(message = "L'anno è obbligatorio")
    private int annoPubblicazione;

    @ElementCollection
    private List<String> immagini;

    @ManyToMany
    private List<Autore> autori;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    private List<Recensione> recensioni;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }
    
    public List<Autore> getAutori() {
        return autori;
    }

    public void setAutori(List<Autore> autori) {
        this.autori = autori;
    }
    
    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }


    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }


    public String getUrlCopertina() {
        return (this.immagini != null && !this.immagini.isEmpty()) ? this.immagini.get(0) : "/images/default-cover.jpg";
    }



}
