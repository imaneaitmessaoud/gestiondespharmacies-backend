package com.pharmactrl.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Alerte {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeAlerte type;
    private String message;
    private boolean estLue = false;
    private LocalDateTime dateCreation;

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isEstLue() {
        return estLue;
    }

    public void setEstLue(boolean estLue) {
        this.estLue = estLue;
    }

    @ManyToOne
    private Medicament medicament;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}