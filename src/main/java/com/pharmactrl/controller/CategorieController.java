package com.pharmactrl.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.pharmactrl.model.Categorie;
import com.pharmactrl.service.CategorieService;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {
    @Autowired
    private CategorieService categorieService;

    @GetMapping
    public List<Categorie> getAll() {
        return categorieService.getAll();
    }

    @PostMapping
    public Categorie add(@RequestBody Categorie c) {
        return categorieService.add(c);
    }
    @GetMapping("/{id}")
    public Categorie getCategorieById(@PathVariable Long id) {
        return categorieService.getCategorieById(id)
            .orElseThrow(() -> new RuntimeException("Catégorie non trouvée avec l'id : " + id));
    }
    
    @PutMapping("/{id}")
    public Categorie update(@PathVariable Long id, @RequestBody Categorie c) {
        return categorieService.update(id, c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categorieService.delete(id);
    }
   
@GetMapping("/search")
public List<Categorie> searchByNom(@RequestParam String nom) {
    return categorieService.searchByNom(nom);
}
}
