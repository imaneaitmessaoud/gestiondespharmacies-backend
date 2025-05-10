package com.pharmactrl.controller;

import com.pharmactrl.dto.VenteCreateDTO;
import com.pharmactrl.dto.VenteDTO;
import com.pharmactrl.model.Vente;
import com.pharmactrl.service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ventes")
@CrossOrigin(origins = "*")
public class VenteController {

    @Autowired
    private VenteService venteService;

    // Ajouter une vente
    @PostMapping
    public Vente ajouterVente(@RequestBody VenteCreateDTO dto) {
        return venteService.ajouterVente(dto);
    }

    // Supprimer une vente par ID
    @DeleteMapping("/delete/{id}")
    public void supprimerVente(@PathVariable Long id) {
        venteService.supprimerVente(id);
    }

    // Modifier une vente par ID  AJOUTÉ ICI
  @PutMapping("/{id}")
public Vente modifierVente(@PathVariable Long id, @RequestBody VenteCreateDTO dto) {
    return venteService.modifierVente(id, dto);
}



    // Récupérer toutes les ventes au format DTO
    @GetMapping("/dto")
    public List<VenteDTO> getAllVenteDTO() {
        return venteService.getAllVentes()
                .stream()
                .map(venteService::convertirEnDTO)
                .collect(Collectors.toList());
    }

    // Récupérer toutes les ventes (entités complètes)
    @GetMapping
    public List<Vente> getAllVentes() {
        return venteService.getAllVentes();
    }

    // Détail vente complète
    @GetMapping("/{id}")
    public Vente getVenteById(@PathVariable Long id) {
        return venteService.getVenteById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vente non trouvée avec l'id : " + id));

    }

    // Détail vente DTO
    @GetMapping("/dto/{id}")
    public VenteDTO getVenteDTOById(@PathVariable Long id) {
        Vente vente = venteService.getVenteById(id)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée avec l'id : " + id));
        return venteService.convertirEnDTO(vente);
    }
}
