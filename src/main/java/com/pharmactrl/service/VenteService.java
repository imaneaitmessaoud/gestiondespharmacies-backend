package com.pharmactrl.service;

import com.pharmactrl.dto.VenteDTO;
import com.pharmactrl.dto.VenteRequestDTO;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.model.Vente;
import com.pharmactrl.model.Utilisateur;
import com.pharmactrl.repository.MedicamentRepository;
import com.pharmactrl.repository.UtilisateurRepositoray;
import com.pharmactrl.repository.VenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VenteService {

    private final VenteRepository venteRepository;

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private UtilisateurRepositoray utilisateurRepository;

    @Autowired
    public VenteService(VenteRepository venteRepository) {
        this.venteRepository = venteRepository;
    }

    public Vente ajouterVente(Vente vente) {
        vente.setDateVente(LocalDateTime.now());
        return venteRepository.save(vente);
    }

    public List<Vente> getAllVentes() {
        return venteRepository.findAll();
    }

    public Optional<Vente> getVenteById(Long id) {
        return venteRepository.findById(id);
    }

    public Vente updateVente(Long id, Vente venteDetails) {
        return venteRepository.findById(id).map(vente -> {
            vente.setDateVente(venteDetails.getDateVente());
            vente.setQuantiteVendue(venteDetails.getQuantiteVendue());
            vente.setMedicament(venteDetails.getMedicament());
            return venteRepository.save(vente);
        }).orElseThrow(() -> new RuntimeException("Vente non trouvée avec l'id : " + id));
    }

    public void supprimerVente(Long id) {
        venteRepository.deleteById(id);
    }

    public VenteDTO convertirEnDTO(Vente vente) {
        VenteDTO dto = new VenteDTO();
        dto.setId(vente.getId());
        dto.setQuantiteVendue(vente.getQuantiteVendue());
        dto.setDateVente(vente.getDateVente());
    
        if (vente.getMedicament() != null) {
            dto.setMedicamentNom(vente.getMedicament().getNom());
        }
    
        if (vente.getUtilisateur() != null) {
            dto.setUtilisateurEmail(vente.getUtilisateur().getEmail());
        }
    
        return dto;
    }
    

    public Vente ajouterVenteDepuisDTO(VenteRequestDTO dto) {
        Medicament medicament = medicamentRepository.findById(dto.getMedicamentId())
            .orElseThrow(() -> new RuntimeException("Médicament introuvable"));

        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Vente vente = new Vente();
        vente.setDateVente(dto.getDateVente());
        vente.setQuantiteVendue(dto.getQuantiteVendue());
        vente.setMedicament(medicament);
        vente.setUtilisateur(utilisateur); // <-- Lien utilisateur

        // Décrémenter le stock
        medicament.setQuantite(medicament.getQuantite() - dto.getQuantiteVendue());
        medicamentRepository.save(medicament);

        return venteRepository.save(vente);
    }
}
