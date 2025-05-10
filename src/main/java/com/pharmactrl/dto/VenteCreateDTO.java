package com.pharmactrl.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VenteCreateDTO {

    private LocalDateTime dateVente;
    private Long utilisateurId;
    private List<LigneVenteDTO> lignes;

    public LocalDateTime getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDateTime dateVente) {
        this.dateVente = dateVente;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public List<LigneVenteDTO> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneVenteDTO> lignes) {
        this.lignes = lignes;
    }

    public static class LigneVenteDTO {
        private Long medicamentId;
        private int quantite;

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
}
