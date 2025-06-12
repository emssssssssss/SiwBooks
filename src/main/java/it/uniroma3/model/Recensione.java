package it.uniroma3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Recensione {
    @Id @GeneratedValue
    private Long id;

    private String titolo;

    private int voto; // 1-5
   

    @Lob
    private String testo;

    @ManyToOne
    private Libro libro;

    @ManyToOne
    private Utente autore;

    
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

}
