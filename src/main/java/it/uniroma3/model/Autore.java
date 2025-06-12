package it.uniroma3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
public class Autore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il nome non può essere nullo")
    private String nome;

    @NotBlank(message = "Il cognome non può essere nullo")
    private String cognome;

    @NotNull(message = "L'anno è obbligatorio")
    @Max(value = 2025, message = "L'anno di nascita non può essere nel futuro")
    private int dataNascita;

    @Max(value = 2025, message = "L'anno di morte non può essere nel futuro")
    private int dataMorte;

    @NotBlank(message = "La nazionalità non può essere nulla")
    private String nazionalita;

	@Size(max = 2048, message = "L'URL dell'immagine è troppo lungo")
	@Pattern(regexp = "^(http|https)://.*\\.(jpg|jpeg|png|gif)$", message = "L'URL deve essere valido e puntare a un'immagine (jpg, jpeg, png, gif)")
    private String fotoUrl;


    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getDataNascita() {
        return dataNascita;
    }
    public void setDataNascita(int dataNascita) {
        this.dataNascita = dataNascita;
    }

    public int getDataMorte() {
        return dataMorte;
    }
    public void setDataMorte(int dataMorte) {
        this.dataMorte = dataMorte;
    }

    public String getNazionalita() {
        return nazionalita;
    }
    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }
    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
