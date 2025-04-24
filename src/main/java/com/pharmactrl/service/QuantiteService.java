package com.pharmactrl.service;

import com.pharmactrl.model.Quantite;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.model.Vente;
import com.pharmactrl.repository.QuantiteRepository;
import com.pharmactrl.repository.MedicamentRepository;
import com.pharmactrl.repository.VenteRepository;
import com.pharmactrl.dto.QuantiteDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuantiteService {

    @Autowired
    private QuantiteRepository quantiteRepository;

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private VenteRepository venteRepository;

    //  Ajouter une quantité
    public Quantite ajouterQuantite(Quantite quantite) {
        // vérifier si medicament existe
        Optional<Medicament> medicamentOpt = medicamentRepository.findById(quantite.getMedicament().getId());
        Optional<Vente> venteOpt = venteRepository.findById(quantite.getVente().getId());

        if (medicamentOpt.isEmpty() || venteOpt.isEmpty()) {
            throw new IllegalArgumentException("Medicament ou Vente introuvable !");
        }

        quantite.setMedicament(medicamentOpt.get());
        quantite.setVente(venteOpt.get());

        return quantiteRepository.save(quantite);
    }

    // Liste complète des quantités
    public List<Quantite> getAll() {
        return quantiteRepository.findAll();
    }

    //  Obtenir une quantité par ID
    public Quantite getById(Long id) {
        return quantiteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quantité non trouvée avec l'id: " + id));
    }

    // Supprimer une quantité
    public void delete(Long id) {
        quantiteRepository.deleteById(id);
    }

    // Convertir en DTO
    public QuantiteDTO toDTO(Quantite quantite) {
        String nomMed = quantite.getMedicament() != null ? quantite.getMedicament().getNom() : null;
        String dateVente = quantite.getVente() != null && quantite.getVente().getDateVente() != null
                ? quantite.getVente().getDateVente().toString()
                : null;

        return new QuantiteDTO(
                quantite.getId(),
                quantite.getQuantite(),
                nomMed,
                dateVente
        );
    }

    //  Liste des DTOs
    public List<QuantiteDTO> getAllDTOs() {
        List<Quantite> quantites = quantiteRepository.findAll();
    
        return quantites.stream().map(q -> {
            String nomMed = q.getMedicament() != null ? q.getMedicament().getNom() : null;
            String dateVente = q.getVente() != null && q.getVente().getDateVente() != null
                               ? q.getVente().getDateVente().toString()
                               : null;
    
            return new QuantiteDTO(q.getId(), q.getQuantite(), nomMed, dateVente);
        }).collect(Collectors.toList());
    }
    
}
