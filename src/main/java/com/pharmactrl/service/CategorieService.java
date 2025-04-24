package com.pharmactrl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmactrl.model.Categorie;
import com.pharmactrl.repository.CategorieRepository;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> getAll() {
        return categorieRepository.findAll();
    }

    public Categorie add(Categorie c) {
        return categorieRepository.save(c);
    }

    public Categorie update(Long id, Categorie updated) {
        Categorie c = categorieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categorie non trouvée"));
        c.setNom(updated.getNom());
        c.setDescription(updated.getDescription());
        return categorieRepository.save(c);
    }

    public void delete(Long id) {
        categorieRepository.deleteById(id);
    }
}
