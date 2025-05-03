package com.pharmactrl.dto;

import java.time.LocalDateTime;

public class VenteDTO {
    private Long id;
    private LocalDateTime dateVente;
    private int quantiteVendue;
    private String medicamentNom;
    private String utilisateurEmail; // <-- Nouveau champ

    // Getters & Setters
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

    public int getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(int quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }

    public String getMedicamentNom() {
        return medicamentNom;
    }

    public void setMedicamentNom(String medicamentNom) {
        this.medicamentNom = medicamentNom;
    }

    public String getUtilisateurEmail() {
        return utilisateurEmail;
    }

    public void setUtilisateurEmail(String utilisateurEmail) {
        this.utilisateurEmail = utilisateurEmail;
    }
}
