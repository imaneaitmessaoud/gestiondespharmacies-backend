package com.pharmactrl.repository;

import com.pharmactrl.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si besoin
}
