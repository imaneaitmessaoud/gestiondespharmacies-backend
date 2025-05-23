package com.pharmactrl.service;

import com.pharmactrl.dto.VenteCreateDTO;
import com.pharmactrl.dto.VenteDTO;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.model.Quantite;
import com.pharmactrl.model.Utilisateur;
import com.pharmactrl.model.Vente;
import com.pharmactrl.repository.MedicamentRepository;
import com.pharmactrl.repository.QuantiteRepository;
import com.pharmactrl.repository.UtilisateurRepositoray;
import com.pharmactrl.repository.VenteRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VenteService {

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private UtilisateurRepositoray utilisateurRepository;

    @Autowired
    private QuantiteRepository quantiteRepository;

    @Autowired
    private AlerteService alerteService;

    public Vente ajouterVente(VenteCreateDTO dto) {
        Vente vente = new Vente();
        vente.setDateVente(dto.getDateVente() != null ? dto.getDateVente() : LocalDateTime.now());

        // Récupération de l'utilisateur
        if (dto.getUtilisateurId() != null) {
            Utilisateur utilisateur = utilisateurRepository.findById(dto.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
            vente.setUtilisateur(utilisateur);
        }

        // Enregistrement initial de la vente
        vente = venteRepository.save(vente);

        // Création des lignes de vente (Quantité)
        for (VenteCreateDTO.LigneVenteDTO ligne : dto.getLignes()) {
            Medicament medicament = medicamentRepository.findById(ligne.getMedicamentId())
                .orElseThrow(() -> new RuntimeException("Médicament introuvable (ID : " + ligne.getMedicamentId() + ")"));

            if (medicament.getQuantite() < ligne.getQuantite()) {
                throw new RuntimeException("Stock insuffisant pour : " + medicament.getNom());
            }

            // Réduction du stock
            medicament.setQuantite(medicament.getQuantite() - ligne.getQuantite());
            medicamentRepository.save(medicament);

            // Enregistrement de la ligne de vente
            Quantite q = new Quantite();
            q.setVente(vente);
            q.setMedicament(medicament);
            q.setQuantite(ligne.getQuantite());
            quantiteRepository.save(q);

            // Vérification des alertes
            alerteService.verifierMedicament(medicament);
        }

        return vente;
    }
    @Transactional
    public List<Vente> getAllVentes() {
        return venteRepository.findAllWithLignes();

    }

   public Optional<Vente> getVenteById(Long id) {
    return venteRepository.findByIdWithLignes(id);
}


    public void supprimerVente(Long id) {
        venteRepository.deleteById(id);
    }

    public VenteDTO convertirEnDTO(Vente vente) {
        VenteDTO dto = new VenteDTO();
        dto.setId(vente.getId());
        dto.setDateVente(vente.getDateVente());
    
        if (vente.getUtilisateur() != null) {
            dto.setUtilisateurEmail(vente.getUtilisateur().getEmail());
        }
    
        //  Forcer le chargement de la collection Lazy
        List<Quantite> lignes = vente.getLignesDeVente();
        int totalQuantite = lignes.stream().mapToInt(Quantite::getQuantite).sum();
    
        dto.setQuantiteVendue(totalQuantite);
    
        if (!lignes.isEmpty()) {
            dto.setMedicamentNom(lignes.get(0).getMedicament().getNom());
        }
    
        return dto;
    }
   // Ajoute cette méthode dans VenteService.java
@Transactional
public Vente modifierVente(Long id, VenteCreateDTO dto) {
    Vente vente = venteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Vente non trouvée"));

    vente.setDateVente(dto.getDateVente());

    if (dto.getUtilisateurId() != null) {
        Utilisateur utilisateur = utilisateurRepository.findById(dto.getUtilisateurId())
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        vente.setUtilisateur(utilisateur);
    }

    // 1. Réajuster les stocks des anciennes lignes
    List<Quantite> anciennes = quantiteRepository.findByVenteId(id);
    for (Quantite ancienne : anciennes) {
        Medicament med = ancienne.getMedicament();
        med.setQuantite(med.getQuantite() + ancienne.getQuantite());
        medicamentRepository.save(med);
    }

    // 2. Supprimer les anciennes lignes
    quantiteRepository.deleteAll(anciennes);

    // 3. Ajouter les nouvelles lignes en ajustant le stock
    for (VenteCreateDTO.LigneVenteDTO ligne : dto.getLignes()) {
        Medicament medicament = medicamentRepository.findById(ligne.getMedicamentId())
            .orElseThrow(() -> new RuntimeException("Médicament introuvable"));

        if (medicament.getQuantite() < ligne.getQuantite()) {
            throw new RuntimeException("Stock insuffisant pour : " + medicament.getNom());
        }

        medicament.setQuantite(medicament.getQuantite() - ligne.getQuantite());
        medicamentRepository.save(medicament);

        Quantite q = new Quantite();
        q.setVente(vente);
        q.setMedicament(medicament);
        q.setQuantite(ligne.getQuantite());
        quantiteRepository.save(q);

        // Vérifie s’il faut déclencher une alerte
        alerteService.verifierMedicament(medicament);
    }

    return venteRepository.save(vente);
}

    
}
