package com.pharmactrl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmactrl.dto.MedicamentCreateDTO;
import com.pharmactrl.dto.MedicamentDTO;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.repository.MedicamentRepository;
import com.pharmactrl.repository.CategorieRepository;

@Service
public class MedicamentService {

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private AlerteService alerteService;

    public Medicament ajouterMedicament(Medicament medicament) {
        Long categorieId = medicament.getCategorie().getId();

        // On récupère la catégorie existante depuis la base
        medicament.setCategorie(
            categorieRepository.findById(categorieId)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"))
        );

        Medicament saved = medicamentRepository.save(medicament);
        alerteService.verifierMedicament(saved);
        return saved;
    }
    public Optional<Medicament> getMedicamentById(Long id) {
        return medicamentRepository.findById(id);
    }
    

    public void supprimerMedicament(Long id) {
        medicamentRepository.deleteById(id);
    }

    public List<Medicament> getAllMedicaments() {
        return medicamentRepository.findAll();
    }

    public Medicament update(Long id, Medicament m) {
        Medicament med = medicamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médicament non trouvé"));

        med.setNom(m.getNom());
        med.setCode(m.getCode());
        med.setCategorie(
            categorieRepository.findById(m.getCategorie().getId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"))
        );
        med.setDateExpiration(m.getDateExpiration());
        med.setPrix(m.getPrix());
        med.setQuantite(m.getQuantite());
        med.setSeuilAlerte(m.getSeuilAlerte());

        return medicamentRepository.save(med);
    }
    public MedicamentDTO convertirEnDTO(Medicament medicament) {
    MedicamentDTO dto = new MedicamentDTO();
    dto.setId(medicament.getId());
    dto.setNom(medicament.getNom());
    dto.setCode(medicament.getCode());
    dto.setPrix(medicament.getPrix());
    if (medicament.getCategorie() != null) {
        dto.setCategorieNom(medicament.getCategorie().getNom());
    }
    return dto;
}
public Medicament ajouterMedicament(MedicamentCreateDTO dto) {
    Medicament medicament = new Medicament();

    medicament.setNom(dto.getNom());
    medicament.setCode(dto.getCode());
    medicament.setDateExpiration(dto.getDateExpiration());
    medicament.setPrix(dto.getPrix());
    medicament.setQuantite(dto.getQuantite());
    medicament.setSeuilAlerte(dto.getSeuilAlerte());

    // Associer la catégorie via ID
    medicament.setCategorie(
        categorieRepository.findById(dto.getCategorieId())
            .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"))
    );

    Medicament saved = medicamentRepository.save(medicament);

    // Vérification d'alerte après ajout
    alerteService.verifierMedicament(saved);

    return saved;
}

}
