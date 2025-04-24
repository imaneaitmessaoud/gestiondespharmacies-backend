package com.pharmactrl.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

 @JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Vente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateVente;

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
    private int quantiteVendue;

    public int getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(int quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }
    public Medicament getMedicament() {
        return medicament;
    }
    
    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }
    
    @ManyToOne
    private Medicament medicament;
    
    @OneToMany(mappedBy = "vente")
@JsonIgnore
private List<Quantite> quantites;

    
        public List<Quantite> getQuantites() {
            return quantites;
        }
    
        public void setQuantites(List<Quantite> quantites) {
            this.quantites = quantites;
        }

      
        
        

}
