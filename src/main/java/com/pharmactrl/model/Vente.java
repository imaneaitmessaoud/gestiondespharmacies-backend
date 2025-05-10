package com.pharmactrl.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateVente;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    // Association avec les quantités (ou lignes de vente)
    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<Quantite> lignesDeVente;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDateTime dateVente) {
        this.dateVente = dateVente;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Quantite> getLignesDeVente() {
        return lignesDeVente;
    }

    public void setLignesDeVente(List<Quantite> lignesDeVente) {
        this.lignesDeVente = lignesDeVente;
    }
}
