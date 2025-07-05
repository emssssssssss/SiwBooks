package it.uniroma3.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
public class Utente implements UserDetails{
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username obbligatorio")
    @Size(min = 3, max = 20, message = "Username deve avere tra 3 e 20 caratteri")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password obbligatoria")
    @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
    private String password;

    @Transient // non è una colonna nel database
	private String passwordBis;

    @Email(message = "Email non valida")
    @Column(unique = true)
    private String email;
    

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo; 

    public enum Ruolo{
        USER,
        ADMIN
    }

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "autore")
    private List<Recensione> recensioni = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Libro> libriLetti = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + ruolo.name()));
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }
    
    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

        public String getPasswordBis() {
        return passwordBis;
    }

    public void setPasswordBis(String passwordBis) {
        this.passwordBis = passwordBis;
    }

        public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public List<Libro> getLibriLetti() {
        return libriLetti;
    }

    public void setLibriLetti(List<Libro> libriLetti) {
        this.libriLetti = libriLetti;
    }

    


    // Metodi di UserDetails
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void aggiungiLibroLetto(Libro libro) {
        if (!this.libriLetti.contains(libro)) {
            this.libriLetti.add(libro);
            libro.getLettori().add(this); 
    }
}


}
