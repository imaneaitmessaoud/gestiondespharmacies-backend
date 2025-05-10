package com.pharmactrl.repository;

import com.pharmactrl.model.Categorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    List<Categorie> findByNomContainingIgnoreCase(String nom);

    // Tu peux ajouter des méthodes personnalisées ici si besoin
}
