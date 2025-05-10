package com.pharmactrl.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;


import com.pharmactrl.model.Alerte;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.model.TypeAlerte;
import com.pharmactrl.repository.AlerteRepository;
import com.pharmactrl.repository.MedicamentRepository;

import jakarta.transaction.Transactional;

@Service
public class AlerteService {

    @Autowired
    private AlerteRepository alerteRepository;
    @Autowired
    private MedicamentRepository medicamentRepository;
 
    public void verifierMedicament(Medicament medicament) {
    if (medicament.getQuantite() <= medicament.getSeuilAlerte()) {
        creerAlerte(medicament, TypeAlerte.STOCK_FAIBLE, "Stock faible pour " + medicament.getNom());
    }

    if (medicament.getDateExpiration().isBefore(LocalDate.now().plusDays(30))) {
        creerAlerte(medicament, TypeAlerte.PEREMPTION, "Expiration proche pour " + medicament.getNom());
    }
}



private void creerAlerte(Medicament medicament, TypeAlerte type, String message) {
    LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
    LocalDateTime endOfDay = startOfDay.plusDays(1);

    boolean dejaAlerteAujourdHui = !alerteRepository
        .findByMedicamentAndTypeAndEstLueFalseAndDateCreationBetween(
            medicament, type, startOfDay, endOfDay
        ).isEmpty();

    if (dejaAlerteAujourdHui) return;

    Alerte alerte = new Alerte();
    alerte.setType(type);
    alerte.setMessage(message);
    alerte.setDateCreation(LocalDateTime.now());
    alerte.setMedicament(medicament);
    alerte.setEstLue(false);
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
    // Vérifie tous les médicaments pour créer des alertes automatiquement
    public void verifierTousLesMedicaments() {
        medicamentRepository.findAll().forEach(this::verifierMedicament);
    }
    
    @Scheduled(cron = "0 0 0 * * ?") // tous les jours à minuit
public void planifierVerification() {
    System.out.println(" Vérification automatique des alertes déclenchée à : " + LocalDateTime.now());
    verifierTousLesMedicaments();
}
@Transactional
public void marquerToutesCommeLues() {
    List<Alerte> nonLues = alerteRepository.findByEstLueFalse();
    for (Alerte alerte : nonLues) {
        alerte.setEstLue(true);
    }
    alerteRepository.saveAll(nonLues);
}
@Transactional
public void supprimerToutesLesAlertesLues() {
    alerteRepository.deleteByEstLueTrue();
}


}
