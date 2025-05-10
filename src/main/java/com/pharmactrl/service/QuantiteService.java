package com.pharmactrl.service;

import com.pharmactrl.model.Quantite;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.model.Vente;
import com.pharmactrl.model.Utilisateur;
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

    // Ajouter une quantité
    public Quantite ajouterQuantite(Quantite quantite) {
        Optional<Medicament> medicamentOpt = medicamentRepository.findById(quantite.getMedicament().getId());
        Optional<Vente> venteOpt = venteRepository.findById(quantite.getVente().getId());

        if (medicamentOpt.isEmpty() || venteOpt.isEmpty()) {
            throw new IllegalArgumentException("Médicament ou Vente introuvable !");
        }

        quantite.setMedicament(medicamentOpt.get());
        quantite.setVente(venteOpt.get());

        return quantiteRepository.save(quantite);
    }

    // Liste complète des quantités
    public List<Quantite> getAll() {
        return quantiteRepository.findAll();
    }

    // Obtenir une quantité par ID
    public Quantite getById(Long id) {
        return quantiteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quantité non trouvée avec l'id: " + id));
    }

    // Supprimer une quantité
    public void delete(Long id) {
        quantiteRepository.deleteById(id);
    }

    // Convertir en DTO complet
    public QuantiteDTO toDTO(Quantite quantite) {
        Long medicamentId = null;
        String medicamentNom = null;
        Long venteId = null;
        String dateVente = null;
        String utilisateurEmail = null;

        if (quantite.getMedicament() != null) {
            medicamentId = quantite.getMedicament().getId();
            medicamentNom = quantite.getMedicament().getNom();
        }

        if (quantite.getVente() != null) {
            venteId = quantite.getVente().getId();
            if (quantite.getVente().getDateVente() != null) {
                dateVente = quantite.getVente().getDateVente().toString();
            }
            Utilisateur utilisateur = quantite.getVente().getUtilisateur();
            if (utilisateur != null) {
                utilisateurEmail = utilisateur.getEmail();
            }
        }

        return new QuantiteDTO(
                quantite.getId(),
                quantite.getQuantite(),
                medicamentId,
                medicamentNom,
                venteId,
                dateVente,
                utilisateurEmail
        );
    }

    // Liste des DTOs
    public List<QuantiteDTO> getAllDTOs() {
        return quantiteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
