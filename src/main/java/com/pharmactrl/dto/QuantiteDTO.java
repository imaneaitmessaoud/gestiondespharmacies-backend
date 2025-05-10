package com.pharmactrl.dto;

public class QuantiteDTO {
    private Long id;
    private int quantite;
    private Long medicamentId;
    private String medicamentNom;
    private Long venteId;
    private String dateVente;
    private String utilisateurEmail;

    // Le constructeur complet que tu dois coller ici :
    public QuantiteDTO(Long id, int quantite, Long medicamentId, String medicamentNom,
                       Long venteId, String dateVente, String utilisateurEmail) {
        this.id = id;
        this.quantite = quantite;
        this.medicamentId = medicamentId;
        this.medicamentNom = medicamentNom;
        this.venteId = venteId;
        this.dateVente = dateVente;
        this.utilisateurEmail = utilisateurEmail;
    }

    // (optionnel) Ajoute aussi un constructeur vide si tu utilises des frameworks comme Jackson :
    public QuantiteDTO() {
    }

    //  Getters & Setters en dessous
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Long getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(Long medicamentId) {
        this.medicamentId = medicamentId;
    }

    public String getMedicamentNom() {
        return medicamentNom;
    }

    public void setMedicamentNom(String medicamentNom) {
        this.medicamentNom = medicamentNom;
    }

    public Long getVenteId() {
        return venteId;
    }

    public void setVenteId(Long venteId) {
        this.venteId = venteId;
    }

    public String getDateVente() {
        return dateVente;
    }

    public void setDateVente(String dateVente) {
        this.dateVente = dateVente;
    }

    public String getUtilisateurEmail() {
        return utilisateurEmail;
    }

    public void setUtilisateurEmail(String utilisateurEmail) {
        this.utilisateurEmail = utilisateurEmail;
    }
}
