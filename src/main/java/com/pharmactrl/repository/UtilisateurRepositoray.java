package com.pharmactrl.repository;

import com.pharmactrl.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepositoray extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
}