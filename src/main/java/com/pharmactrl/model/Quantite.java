package com.pharmactrl.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)


@Entity
public class Quantite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantite;

    @ManyToOne
@JsonIgnoreProperties("quantites") // ignore la liste des quantités dans Medicament
private Medicament medicament;

@ManyToOne
@JsonIgnoreProperties("quantites")
private Vente vente;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }
}
