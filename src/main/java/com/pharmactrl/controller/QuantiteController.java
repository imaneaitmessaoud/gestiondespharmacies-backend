package com.pharmactrl.controller;

import com.pharmactrl.model.Quantite;
import com.pharmactrl.service.QuantiteService;
import com.pharmactrl.dto.QuantiteDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quantites")
@CrossOrigin(origins = "*") // pour autoriser l'accès depuis Postman ou le front
public class QuantiteController {

    @Autowired
    private QuantiteService quantiteService;

    // Ajouter une quantité (reçoit JSON complet avec medicament et vente ID)
    @PostMapping
    public Quantite add(@RequestBody Quantite quantite) {
        return quantiteService.ajouterQuantite(quantite);
    }

    // Récupérer toutes les quantités
    @GetMapping
    public List<QuantiteDTO> getAll() {
        return quantiteService.getAllDTOs();
    }
    //  Récupérer toutes les quantités en DTO
    @GetMapping("/dto")
    public List<QuantiteDTO> getAllDTO() {
        return quantiteService.getAllDTOs();
    }

    //  Récupérer une quantité par ID
    @GetMapping("/{id}")
    public Quantite getById(@PathVariable Long id) {
        return quantiteService.getById(id);
    }

    // Supprimer une quantité par ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        quantiteService.delete(id);
    }
}
