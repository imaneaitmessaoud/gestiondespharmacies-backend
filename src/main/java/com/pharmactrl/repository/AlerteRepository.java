package com.pharmactrl.repository;

import com.pharmactrl.model.Alerte;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.model.TypeAlerte;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlerteRepository extends JpaRepository<Alerte, Long> {

    // Trouver toutes les alertes non lues pour un médicament et un type
    List<Alerte> findByMedicamentAndTypeAndEstLueFalse(Medicament medicament, TypeAlerte type);

    //  Trouver les alertes non lues pour un médicament + type créées aujourd'hui
    List<Alerte> findByMedicamentAndTypeAndEstLueFalseAndDateCreationBetween(
        Medicament medicament,
        TypeAlerte type,
        LocalDateTime start,
        LocalDateTime end
    );
    
    // (Optionnel) Liste toutes les alertes non lues
    List<Alerte> findByEstLueFalse();
    void deleteByEstLueTrue();

}
