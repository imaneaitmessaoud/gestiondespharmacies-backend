package com.pharmactrl.service;

import com.pharmactrl.dto.MedicamentCreateDTO;
import com.pharmactrl.dto.MedicamentDTO;
import com.pharmactrl.dto.MedicamentUpdateDTO;
import com.pharmactrl.model.Categorie;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.repository.CategorieRepository;
import com.pharmactrl.repository.MedicamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MedicamentServiceTest {

    private MedicamentRepository medicamentRepository;
    private CategorieRepository categorieRepository;
    private AlerteService alerteService;
    private MedicamentService medicamentService;

    @BeforeEach
void setup() throws Exception {
    medicamentRepository = mock(MedicamentRepository.class);
    categorieRepository = mock(CategorieRepository.class);
    alerteService = mock(AlerteService.class);

    medicamentService = new MedicamentService();

    // Classe utilisée
    Class<?> m = MedicamentService.class;

    var repoField = m.getDeclaredField("medicamentRepository");
    repoField.setAccessible(true);
    repoField.set(medicamentService, medicamentRepository);

    var catField = m.getDeclaredField("categorieRepository");
    catField.setAccessible(true);
    catField.set(medicamentService, categorieRepository);

    var alertField = m.getDeclaredField("alerteService");
    alertField.setAccessible(true);
    alertField.set(medicamentService, alerteService);
}


    @Test
    void testAjouterMedicamentAvecDTO() {
        MedicamentCreateDTO dto = new MedicamentCreateDTO();
        dto.setNom("Doliprane");
        dto.setCode("D123");
        dto.setQuantite(100);
        dto.setPrix(9.99);
        dto.setSeuilAlerte(10);
        dto.setDateExpiration(LocalDate.now().plusMonths(6));
        dto.setCategorieId(1L);

        Categorie categorie = new Categorie();
        categorie.setId(1L);
        categorie.setNom("Antalgique");

        when(medicamentRepository.existsByCode("D123")).thenReturn(false);
        when(categorieRepository.findById(1L)).thenReturn(Optional.of(categorie));
        when(medicamentRepository.save(any(Medicament.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Medicament result = medicamentService.ajouterMedicament(dto);

        assertEquals("Doliprane", result.getNom());
        assertEquals("D123", result.getCode());
        assertEquals(100, result.getQuantite());
        verify(alerteService).verifierMedicament(result);
    }

    @Test
    void testGetMedicamentById() {
        Medicament m = new Medicament();
        m.setId(10L);
        m.setNom("Amoxicilline");

        when(medicamentRepository.findById(10L)).thenReturn(Optional.of(m));

        Optional<Medicament> result = medicamentService.getMedicamentById(10L);

        assertTrue(result.isPresent());
        assertEquals("Amoxicilline", result.get().getNom());
    }

    @Test
    void testSupprimerMedicament() {
        medicamentService.supprimerMedicament(5L);
        verify(medicamentRepository).deleteById(5L);
    }

    @Test
    void testGetAllMedicaments() {
        Medicament m1 = new Medicament(); m1.setNom("Doliprane");
        Medicament m2 = new Medicament(); m2.setNom("Spasfon");
        when(medicamentRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Medicament> result = medicamentService.getAllMedicaments();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateMedicament() {
        Medicament m = new Medicament();
        m.setId(1L); m.setNom("Ancien");
        m.setCategorie(new Categorie());

        MedicamentUpdateDTO dto = new MedicamentUpdateDTO();
        dto.setNom("Nouveau");
        dto.setCode("C222");
        dto.setDateExpiration(LocalDate.now().plusDays(100));
        dto.setPrix(19.99);
        dto.setQuantite(50);
        dto.setSeuilAlerte(5);
        dto.setCategorieId(2L);

        Categorie cat = new Categorie(); cat.setId(2L);

        when(medicamentRepository.findById(1L)).thenReturn(Optional.of(m));
        when(categorieRepository.findById(2L)).thenReturn(Optional.of(cat));
        when(medicamentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Medicament result = medicamentService.update(1L, dto);

        assertEquals("Nouveau", result.getNom());
        assertEquals(50, result.getQuantite());
        verify(alerteService).verifierMedicament(result);
    }

    @Test
    void testConvertirEnDTO() {
        Medicament m = new Medicament();
        m.setId(1L);
        m.setNom("Doliprane");
        m.setCode("D999");
        m.setPrix(12.5);

        Categorie cat = new Categorie();
        cat.setNom("Antalgique");
        m.setCategorie(cat);

        MedicamentDTO dto = medicamentService.convertirEnDTO(m);

        assertEquals("Doliprane", dto.getNom());
        assertEquals("Antalgique", dto.getCategorieNom());
    }
}
