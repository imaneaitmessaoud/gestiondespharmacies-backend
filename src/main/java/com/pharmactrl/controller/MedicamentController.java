package com.pharmactrl.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pharmactrl.dto.MedicamentCreateDTO;
import com.pharmactrl.dto.MedicamentDTO;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.service.MedicamentService;

@RestController
@RequestMapping("/api/medicaments")
public class MedicamentController {

    @Autowired
    private MedicamentService medicamentService;

    @GetMapping
    public List<Medicament> getAllMedicaments() {
        return medicamentService.getAllMedicaments();
    }

    //  version correcte avec MedicamentCreateDTO
    @PostMapping
    public Medicament ajouterMedicament(@RequestBody MedicamentCreateDTO dto) {
        return medicamentService.ajouterMedicament(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicament> getMedicamentById(@PathVariable Long id) {
        Medicament medicament = medicamentService.getMedicamentById(id)
                .orElseThrow(() -> new RuntimeException("Médicament non trouvé avec l'id : " + id));
        return ResponseEntity.ok(medicament);
    }

    @PutMapping("/{id}")
    public Medicament update(@PathVariable Long id, @RequestBody Medicament m) {
        return medicamentService.update(id, m);
    }

    @DeleteMapping("/{id}")
    public void supprimerMedicament(@PathVariable Long id) {
        medicamentService.supprimerMedicament(id);
    }

    @GetMapping("/dto")
    public List<MedicamentDTO> getAllMedicamentDTOs() {
        return medicamentService.getAllMedicaments().stream()
                .map(medicamentService::convertirEnDTO)
                .collect(Collectors.toList());
    }
}
