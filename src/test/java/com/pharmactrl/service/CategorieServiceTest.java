package com.pharmactrl.service;

import com.pharmactrl.model.Categorie;
import com.pharmactrl.repository.CategorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategorieServiceTest {

    private CategorieRepository categorieRepository;
    private CategorieService categorieService;

    @BeforeEach
    void setup() throws Exception {
        categorieRepository = mock(CategorieRepository.class);
        categorieService = new CategorieService();

        var field = CategorieService.class.getDeclaredField("categorieRepository");
        field.setAccessible(true);
        field.set(categorieService, categorieRepository);
    }

    @Test
    void testGetAll() {
        Categorie c1 = new Categorie(); c1.setNom("Antibiotiques");
        Categorie c2 = new Categorie(); c2.setNom("Antalgiques");
        when(categorieRepository.findAll()).thenReturn(List.of(c1, c2));

        List<Categorie> result = categorieService.getAll();

        assertEquals(2, result.size());
        assertEquals("Antibiotiques", result.get(0).getNom());
    }

    @Test
    void testAddCategorie() {
        Categorie c = new Categorie(); c.setNom("Anti-inflammatoires");
        when(categorieRepository.save(c)).thenReturn(c);

        Categorie result = categorieService.add(c);

        assertEquals("Anti-inflammatoires", result.getNom());
    }

    @Test
    void testGetByIdFound() {
        Categorie c = new Categorie(); c.setId(1L); c.setNom("Antibiotiques");
        when(categorieRepository.findById(1L)).thenReturn(Optional.of(c));

        Optional<Categorie> result = categorieService.getCategorieById(1L);

        assertTrue(result.isPresent());
        assertEquals("Antibiotiques", result.get().getNom());
    }

    @Test
    void testUpdateCategorie() {
        Categorie existante = new Categorie(); existante.setId(1L); existante.setNom("Ancienne");
        Categorie nouvelle = new Categorie(); nouvelle.setNom("Nouvelle"); nouvelle.setDescription("Desc");

        when(categorieRepository.findById(1L)).thenReturn(Optional.of(existante));
        when(categorieRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Categorie result = categorieService.update(1L, nouvelle);

        assertEquals("Nouvelle", result.getNom());
        assertEquals("Desc", result.getDescription());
    }

    @Test
    void testDeleteCategorie() {
        categorieService.delete(5L);
        verify(categorieRepository).deleteById(5L);
    }

    @Test
    void testSearchByNom() {
        Categorie c1 = new Categorie(); c1.setNom("Antalgique");
        when(categorieRepository.findByNomContainingIgnoreCase("anta")).thenReturn(List.of(c1));

        List<Categorie> result = categorieService.searchByNom("anta");

        assertEquals(1, result.size());
        assertEquals("Antalgique", result.get(0).getNom());
    }
}
