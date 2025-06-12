package it.uniroma3.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Utente {
    @Id 
    @GeneratedValue
    private Long id;

    private String username;

    private String password;
    
    private String ruolo; // OCCASIONALE, REGISTRATO, ADMIN

    @OneToMany(mappedBy = "autore")
    private List<Recensione> recensioni;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
