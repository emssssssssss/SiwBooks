package it.uniroma3.model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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


    //@ManyToMany(cascade = { CascadeType.ALL })
    //@JoinTable(
    //name = "autore_libri", // <-- Nome esplicito della tabella di join
    //joinColumns = @JoinColumn(name = "libro_id"),
    //inverseJoinColumns = @JoinColumn(name = "autore_id"))
    //@Fetch(FetchMode.SUBSELECT)
    //private List<Autore> autori;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
      name = "autore_libri",
      joinColumns = @JoinColumn(name = "libro_id"),
      inverseJoinColumns = @JoinColumn(name = "autore_id")
    )
    private Set<Autore> autori = new HashSet<>();
    

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    private List<Recensione> recensioni;

    //@ManyToMany(mappedBy = "libriLetti")
    //@Fetch(FetchMode.SUBSELECT)
    //private List<Utente> lettori;

    @ManyToMany(mappedBy = "libriLetti")
    @Fetch(FetchMode.SUBSELECT)
    private List<Utente> lettori = new ArrayList<>();



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
    
    public Set<Autore> getAutori() {
        return autori;
    }

    public void setAutori(Set<Autore> autori) {
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

        public void setUrlCopertina(String url) {
        if (this.immagini == null) {
            this.immagini = new ArrayList<>();
        }
        if (this.immagini.isEmpty()) {
            this.immagini.add(url);
        } else {
            this.immagini.set(0, url);
        }
    }

    public List<String> getImmagini() {
    return immagini;
    }

    public void setImmagini(List<String> immagini) {
        this.immagini = immagini;
    }

    public List<Utente> getLettori() {
        return lettori;
    }

    public void setLettori(List<Utente> lettori) {
        this.lettori = lettori;
    }

}
