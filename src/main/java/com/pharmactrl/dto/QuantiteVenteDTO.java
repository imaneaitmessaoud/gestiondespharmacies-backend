package com.pharmactrl.dto;

public class QuantiteVenteDTO {
    private Long medicamentId;
    private int quantite;


    // Getters & Setters
    public Long getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(Long medicamentId) {
        this.medicamentId = medicamentId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}