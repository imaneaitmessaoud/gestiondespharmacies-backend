package com.pharmactrl.service;

import com.pharmactrl.model.Alerte;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.model.TypeAlerte;
import com.pharmactrl.repository.AlerteRepository;
import com.pharmactrl.repository.MedicamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlerteServiceTest {

    private AlerteRepository alerteRepository;
    private MedicamentRepository medicamentRepository;
    private AlerteService alerteService;

    @BeforeEach
    void setup() throws Exception {
        alerteRepository = mock(AlerteRepository.class);
        medicamentRepository = mock(MedicamentRepository.class);
        alerteService = new AlerteService();

        var cls = AlerteService.class;

        var aField = cls.getDeclaredField("alerteRepository");
        aField.setAccessible(true);
        aField.set(alerteService, alerteRepository);

        var mField = cls.getDeclaredField("medicamentRepository");
        mField.setAccessible(true);
        mField.set(alerteService, medicamentRepository);
    }

    @Test
    void testVerifierMedicament_CreeAlerteStockFaible() {
        Medicament medicament = new Medicament();
        medicament.setNom("Doliprane");
        medicament.setQuantite(5);
        medicament.setSeuilAlerte(10);
        medicament.setDateExpiration(LocalDate.now().plusDays(60));

        when(alerteRepository.findByMedicamentAndTypeAndEstLueFalseAndDateCreationBetween(
                any(), eq(TypeAlerte.STOCK_FAIBLE), any(), any()))
                .thenReturn(List.of()); // pas d’alerte existante

        alerteService.verifierMedicament(medicament);

        verify(alerteRepository).save(any(Alerte.class));
    }

    @Test
    void testGetAlertes() {
        Alerte a1 = new Alerte(); a1.setMessage("Alerte 1");
        Alerte a2 = new Alerte(); a2.setMessage("Alerte 2");

        when(alerteRepository.findAll()).thenReturn(List.of(a1, a2));

        List<Alerte> result = alerteService.getAlertes();

        assertEquals(2, result.size());
        assertEquals("Alerte 1", result.get(0).getMessage());
    }

    @Test
    void testMarquerCommeLue() {
        Alerte alerte = new Alerte(); alerte.setId(1L); alerte.setEstLue(false);
        when(alerteRepository.findById(1L)).thenReturn(Optional.of(alerte));
        when(alerteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Alerte result = alerteService.marquerCommeLue(1L);

        assertTrue(result.isEstLue());
    }

    @Test
    void testMarquerToutesCommeLues() {
        Alerte a1 = new Alerte(); a1.setEstLue(false);
        Alerte a2 = new Alerte(); a2.setEstLue(false);

        when(alerteRepository.findByEstLueFalse()).thenReturn(List.of(a1, a2));

        alerteService.marquerToutesCommeLues();

        assertTrue(a1.isEstLue());
        assertTrue(a2.isEstLue());
        verify(alerteRepository).saveAll(List.of(a1, a2));
    }

    @Test
    void testSupprimerToutesLesAlertesLues() {
        alerteService.supprimerToutesLesAlertesLues();
        verify(alerteRepository).deleteByEstLueTrue();
    }
}
