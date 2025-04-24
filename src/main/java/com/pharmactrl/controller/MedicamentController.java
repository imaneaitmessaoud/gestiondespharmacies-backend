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
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public Medicament ajouterMedicament(@RequestBody Medicament medicament) {
        return medicamentService.ajouterMedicament(medicament);
    }

    @PutMapping("/{id}")
    public Medicament update(@PathVariable Long id, @RequestBody Medicament m) {
        return medicamentService.update(id, m);
    }

    @DeleteMapping("/{id}")
    public void supprimerMedicament(@PathVariable Long id) {
        medicamentService.supprimerMedicament(id);
    }
}
