package com.pharmactrl.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmactrl.model.Alerte;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.model.TypeAlerte;
import com.pharmactrl.repository.AlerteRepository;

@Service
public class AlerteService {

    @Autowired
    private AlerteRepository alerteRepository;

    public void verifierMedicament(Medicament medicament) {
        if (medicament.getQuantite() <= medicament.getSeuilAlerte()) {
            creerAlerte(medicament, TypeAlerte.STOCK_FAIBLE, "Stock faible pour " + medicament.getNom());
        }

        if (medicament.getDateExpiration().isBefore(LocalDate.now().plusDays(30))) {
            creerAlerte(medicament, TypeAlerte.PEREMPTION, "Expiration proche pour " + medicament.getNom());
        }
    }

    private void creerAlerte(Medicament medicament, TypeAlerte type, String message) {
        Alerte alerte = new Alerte();
        alerte.setType(type);
        alerte.setMessage(message);
        alerte.setDateCreation(LocalDateTime.now());
        alerte.setMedicament(medicament);
        alerteRepository.save(alerte);
    }

    public List<Alerte> getAlertes() {
        return alerteRepository.findAll();
    }

    public Alerte marquerCommeLue(Long id) {
        Alerte alerte = alerteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerte non trouvée"));
        alerte.setEstLue(true);
        return alerteRepository.save(alerte);
    }
}
