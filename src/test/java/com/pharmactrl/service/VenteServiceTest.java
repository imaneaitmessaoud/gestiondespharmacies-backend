package com.pharmactrl.service;

import com.pharmactrl.dto.VenteCreateDTO;
import com.pharmactrl.dto.VenteDTO;
import com.pharmactrl.model.Medicament;
import com.pharmactrl.model.Quantite;
import com.pharmactrl.model.Utilisateur;
import com.pharmactrl.model.Vente;
import com.pharmactrl.repository.MedicamentRepository;
import com.pharmactrl.repository.QuantiteRepository;
import com.pharmactrl.repository.UtilisateurRepositoray;
import com.pharmactrl.repository.VenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VenteServiceTest {

    @InjectMocks
    private VenteService venteService;

    @Mock
    private VenteRepository venteRepository;

    @Mock
    private MedicamentRepository medicamentRepository;

    @Mock
    private UtilisateurRepositoray utilisateurRepository;

    @Mock
    private QuantiteRepository quantiteRepository;

    @Mock
    private AlerteService alerteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAjouterVente_success() {
        VenteCreateDTO dto = new VenteCreateDTO();
        dto.setDateVente(LocalDateTime.now());
        dto.setUtilisateurId(1L);

        VenteCreateDTO.LigneVenteDTO ligne = new VenteCreateDTO.LigneVenteDTO();
        ligne.setMedicamentId(1L);
        ligne.setQuantite(2);
        dto.setLignes(List.of(ligne));

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@email.com");

        Medicament medicament = new Medicament();
        medicament.setId(1L);
        medicament.setNom("Doliprane");
        medicament.setQuantite(10);

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(medicamentRepository.findById(1L)).thenReturn(Optional.of(medicament));
        when(venteRepository.save(any(Vente.class))).thenAnswer(i -> i.getArguments()[0]);

        Vente result = venteService.ajouterVente(dto);

        assertNotNull(result);
        assertEquals("test@email.com", result.getUtilisateur().getEmail());
        verify(medicamentRepository).save(any(Medicament.class));
        verify(quantiteRepository).save(any(Quantite.class));
    }

    @Test
    public void testSupprimerVente() {
        venteService.supprimerVente(1L);
        verify(venteRepository).deleteById(1L);
    }

    @Test
    public void testModifierVente_success() {
        Long venteId = 1L;
        VenteCreateDTO dto = new VenteCreateDTO();
        dto.setDateVente(LocalDateTime.now());
        dto.setUtilisateurId(2L);

        VenteCreateDTO.LigneVenteDTO ligne = new VenteCreateDTO.LigneVenteDTO();
        ligne.setMedicamentId(1L);
        ligne.setQuantite(3);
        dto.setLignes(List.of(ligne));

        Vente venteExistante = new Vente();
        venteExistante.setId(venteId);

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("new@email.com");

        Medicament medicament = new Medicament();
        medicament.setId(1L);
        medicament.setNom("Doliprane");
        medicament.setQuantite(10);

        Quantite ancienneQuantite = new Quantite();
        ancienneQuantite.setMedicament(medicament);
        ancienneQuantite.setQuantite(2);

        List<Quantite> anciennes = List.of(ancienneQuantite);

        when(venteRepository.findById(venteId)).thenReturn(Optional.of(venteExistante));
        when(utilisateurRepository.findById(2L)).thenReturn(Optional.of(utilisateur));
        when(medicamentRepository.findById(anyLong())).thenReturn(Optional.of(medicament));
        when(quantiteRepository.findByVenteId(venteId)).thenReturn(anciennes);
        when(venteRepository.save(any(Vente.class))).thenAnswer(i -> i.getArguments()[0]);

        Vente result = venteService.modifierVente(venteId, dto);

        assertNotNull(result);
        assertEquals("new@email.com", result.getUtilisateur().getEmail());
        verify(quantiteRepository).deleteAll(anciennes);
        verify(quantiteRepository).save(any(Quantite.class));
        verify(medicamentRepository, atLeastOnce()).save(any(Medicament.class));
    }

    @Test
    public void testGetAllVentes() {
        Vente vente = new Vente();
        when(venteRepository.findAllWithLignes()).thenReturn(List.of(vente));

        List<Vente> result = venteService.getAllVentes();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetVenteById_found() {
        Vente vente = new Vente();
        vente.setId(1L);
        when(venteRepository.findByIdWithLignes(1L)).thenReturn(Optional.of(vente));

        Optional<Vente> result = venteService.getVenteById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testConvertirEnDTO() {
        Vente vente = new Vente();
        vente.setId(1L);
        vente.setDateVente(LocalDateTime.now());

        Utilisateur user = new Utilisateur();
        user.setEmail("dto@test.com");
        vente.setUtilisateur(user);

        Medicament medicament = new Medicament();
        medicament.setNom("Doliprane");

        Quantite q = new Quantite();
        q.setQuantite(2);
        q.setMedicament(medicament);

        vente.setLignesDeVente(List.of(q));

        VenteDTO dto = venteService.convertirEnDTO(vente);

        assertEquals("dto@test.com", dto.getUtilisateurEmail());
        assertEquals(2, dto.getQuantiteVendue());
        assertEquals("Doliprane", dto.getMedicamentNom());
    }
}