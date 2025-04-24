package com.pharmactrl.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pharmactrl.dto.VenteDTO;
import com.pharmactrl.dto.VenteRequestDTO;
import com.pharmactrl.model.Vente;
import com.pharmactrl.service.VenteService;

@RestController
@RequestMapping("/api/ventes")
public class VenteController {

    @Autowired
    private VenteService venteService;

    //  Ajouter une vente
    @PostMapping
    public Vente ajouterVente(@RequestBody Vente vente) {
        return venteService.ajouterVente(vente);
    }

    //  Supprimer une vente par ID
    @DeleteMapping("/{id}")
    public void supprimerVente(@PathVariable Long id) {
        venteService.supprimerVente(id);
    }

    // Récupérer toutes les ventes
    @GetMapping
    public List<Vente> getAllVentes() {
        return venteService.getAllVentes();
    }

    // Récupérer toutes les ventes au format DTO
    @GetMapping("/dto")
    public List<VenteDTO> getAllVenteDTO() {
        return venteService.getAllVentes().stream()
                .map(venteService::convertirEnDTO)
                .collect(Collectors.toList());
    }

    // Récupérer une vente par ID
    @GetMapping("/{id}")
    public Vente getVenteById(@PathVariable Long id) {
        return venteService.getVenteById(id)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée avec l'id : " + id));
    }

    //  Récupérer une vente au format DTO par ID
    @GetMapping("/dto/{id}")
    public VenteDTO getVenteDTOById(@PathVariable Long id) {
        Vente vente = venteService.getVenteById(id)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée avec l'id : " + id));
        return venteService.convertirEnDTO(vente);
    }
    @PostMapping("/dto")
public Vente ajouterVenteDepuisDTO(@RequestBody VenteRequestDTO dto) {
    return venteService.ajouterVenteDepuisDTO(dto);
}
}
