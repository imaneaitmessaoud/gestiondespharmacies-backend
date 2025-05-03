package com.pharmactrl.dto;

import java.time.LocalDate;

public class MedicamentCreateDTO {
    private String nom;
    private String code;
    private LocalDate dateExpiration;
    private int quantite;
    private int seuilAlerte;
    private double prix;
    private Long categorieId;

    // Getters & Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public LocalDate getDateExpiration() { return dateExpiration; }
    public void setDateExpiration(LocalDate dateExpiration) { this.dateExpiration = dateExpiration; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public int getSeuilAlerte() { return seuilAlerte; }
    public void setSeuilAlerte(int seuilAlerte) { this.seuilAlerte = seuilAlerte; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public Long getCategorieId() { return categorieId; }
    public void setCategorieId(Long categorieId) { this.categorieId = categorieId; }
}
