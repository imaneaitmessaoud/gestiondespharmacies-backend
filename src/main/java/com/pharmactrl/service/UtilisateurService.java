package com.pharmactrl.service;

import com.pharmactrl.dto.UtilisateurDTO;
import com.pharmactrl.model.Utilisateur;
import com.pharmactrl.repository.UtilisateurRepositoray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepositoray utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }
    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getById(Long id) {
        return utilisateurRepository.findById(id);
    }
    public Optional<Utilisateur> findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }
 
    public Utilisateur create(Utilisateur utilisateur) {
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé.");
        }
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }
    

    public Utilisateur update(Long id, Utilisateur updated) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        utilisateur.setNom(updated.getNom());
        utilisateur.setPrenom(updated.getPrenom());
        utilisateur.setEmail(updated.getEmail());
        utilisateur.setRole(updated.getRole());

        // Si le mot de passe est modifié, le réencoder
        if (updated.getMotDePasse() != null && !updated.getMotDePasse().isEmpty()) {
            utilisateur.setMotDePasse(passwordEncoder.encode(updated.getMotDePasse()));
        }

        return utilisateurRepository.save(utilisateur);
    }

    public void delete(Long id) {
        utilisateurRepository.deleteById(id);
    }
    public UtilisateurDTO convertirEnDTO(Utilisateur utilisateur) {
    UtilisateurDTO dto = new UtilisateurDTO();
    dto.setNom(utilisateur.getNom());
    dto.setPrenom(utilisateur.getPrenom());
    dto.setEmail(utilisateur.getEmail());
    dto.setRole(utilisateur.getRole().name());
    return dto;
}

public void changePassword(Long id, String newPassword) {
    Utilisateur utilisateur = utilisateurRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    utilisateur.setMotDePasse(passwordEncoder.encode(newPassword));
    utilisateurRepository.save(utilisateur);
}

}
