package com.pharmactrl.service;

import com.pharmactrl.model.Medicament;
import com.pharmactrl.model.Quantite;
import com.pharmactrl.model.Utilisateur;
import com.pharmactrl.model.Vente;
import com.pharmactrl.dto.QuantiteDTO;
import com.pharmactrl.repository.MedicamentRepository;
import com.pharmactrl.repository.QuantiteRepository;
import com.pharmactrl.repository.VenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class QuantiteServiceTest {

    private QuantiteRepository quantiteRepository;
    private MedicamentRepository medicamentRepository;
    private VenteRepository venteRepository;
    private QuantiteService quantiteService;

    @BeforeEach
    void setup() throws Exception {
        quantiteRepository = mock(QuantiteRepository.class);
        medicamentRepository = mock(MedicamentRepository.class);
        venteRepository = mock(VenteRepository.class);
        quantiteService = new QuantiteService();

        var cls = QuantiteService.class;

        var qf = cls.getDeclaredField("quantiteRepository");
        qf.setAccessible(true);
        qf.set(quantiteService, quantiteRepository);

        var mf = cls.getDeclaredField("medicamentRepository");
        mf.setAccessible(true);
        mf.set(quantiteService, medicamentRepository);

        var vf = cls.getDeclaredField("venteRepository");
        vf.setAccessible(true);
        vf.set(quantiteService, venteRepository);
    }

    @Test
    void testAjouterQuantiteSuccess() {
        Medicament medicament = new Medicament(); medicament.setId(1L);
        Vente vente = new Vente(); vente.setId(2L);
        Quantite quantite = new Quantite();
        quantite.setQuantite(10);
        quantite.setMedicament(medicament);
        quantite.setVente(vente);

        when(medicamentRepository.findById(1L)).thenReturn(Optional.of(medicament));
        when(venteRepository.findById(2L)).thenReturn(Optional.of(vente));
        when(quantiteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Quantite result = quantiteService.ajouterQuantite(quantite);

        assertEquals(10, result.getQuantite());
        assertEquals(medicament, result.getMedicament());
        assertEquals(vente, result.getVente());
    }

    @Test
    void testGetAllQuantites() {
        Quantite q1 = new Quantite(); q1.setQuantite(5);
        Quantite q2 = new Quantite(); q2.setQuantite(15);
        when(quantiteRepository.findAll()).thenReturn(List.of(q1, q2));

        List<Quantite> result = quantiteService.getAll();

        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getQuantite());
    }

    @Test
    void testGetById() {
        Quantite q = new Quantite(); q.setId(3L); q.setQuantite(20);
        when(quantiteRepository.findById(3L)).thenReturn(Optional.of(q));

        Quantite result = quantiteService.getById(3L);

        assertEquals(20, result.getQuantite());
    }

    @Test
    void testDeleteQuantite() {
        quantiteService.delete(5L);
        verify(quantiteRepository).deleteById(5L);
    }

    @Test
    void testToDTO() {
        Medicament m = new Medicament(); m.setId(1L); m.setNom("Doliprane");
        Utilisateur u = new Utilisateur(); u.setEmail("pharma@emsi.ma");
        Vente v = new Vente(); v.setId(2L); v.setUtilisateur(u); v.setDateVente(LocalDate.of(2025, 5, 16).atStartOfDay());

        Quantite q = new Quantite();
        q.setId(7L);
        q.setQuantite(8);
        q.setMedicament(m);
        q.setVente(v);

        QuantiteDTO dto = quantiteService.toDTO(q);

        assertEquals(7L, dto.getId());
        assertEquals("Doliprane", dto.getMedicamentNom());
        assertEquals("pharma@emsi.ma", dto.getUtilisateurEmail());
        assertEquals("2025-05-16T00:00", dto.getDateVente());

    }

    @Test
    void testGetAllDTOs() {
        Quantite q = new Quantite(); q.setId(9L); q.setQuantite(50);
        when(quantiteRepository.findAll()).thenReturn(List.of(q));

        List<QuantiteDTO> dtos = quantiteService.getAllDTOs();

        assertEquals(1, dtos.size());
        assertEquals(50, dtos.get(0).getQuantite());
    }
}
