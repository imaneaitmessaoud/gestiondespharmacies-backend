package com.pharmactrl.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String code;
//@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categorie_id") // FK dans la table "medicament"
    
    private Categorie categorie;
   
    
    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    private LocalDate dateExpiration;
    private int quantite;
    private int seuilAlerte;
    private double prix; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(int seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }

    @OneToMany(mappedBy = "medicament")
@JsonIgnore
private List<Quantite> quantites;

// + getter/setter
    public List<Quantite> getQuantites() {
        return quantites;
    }

    public void setQuantites(List<Quantite> quantites) {
        this.quantites = quantites;
} 
@OneToMany(mappedBy = "medicament", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<Alerte> alertes;
    
        public List<Alerte> getAlertes() {
            return alertes;
        }
    
        public void setAlertes(List<Alerte> alertes) {
            this.alertes = alertes;
        }
}
