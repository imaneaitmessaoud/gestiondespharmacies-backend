package com.pharmactrl.dto;

public class QuantiteDTO {
    private Long id;
    private int quantite;
    private String medicamentNom;
    private String venteDate;

    public QuantiteDTO(Long id, int quantite, String medicamentNom, String venteDate) {
        this.id = id;
        this.quantite = quantite;
        this.medicamentNom = medicamentNom;
        this.venteDate = venteDate;
    }

    public Long getId() {
        return id;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getMedicamentNom() {
        return medicamentNom;
    }

    public String getVenteDate() {
        return venteDate;
    }
}
