package com.pharmactrl.service;

import com.pharmactrl.dto.UtilisateurDTO;
import com.pharmactrl.model.Role;
import com.pharmactrl.model.Utilisateur;
import com.pharmactrl.repository.UtilisateurRepositoray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtilisateurServiceTest {

    private UtilisateurRepositoray utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private UtilisateurService utilisateurService;

    @BeforeEach
    void setUp() throws Exception {
        utilisateurRepository = mock(UtilisateurRepositoray.class);
        passwordEncoder = new BCryptPasswordEncoder();
        utilisateurService = new UtilisateurService();

        // Injection via réflexion
        var repoField = UtilisateurService.class.getDeclaredField("utilisateurRepository");
        repoField.setAccessible(true);
        repoField.set(utilisateurService, utilisateurRepository);

        var encoderField = UtilisateurService.class.getDeclaredField("passwordEncoder");
        encoderField.setAccessible(true);
        encoderField.set(utilisateurService, passwordEncoder);
    }

    @Test
    void testGetAll() {
        Utilisateur u1 = new Utilisateur(); u1.setNom("Ali");
        Utilisateur u2 = new Utilisateur(); u2.setNom("Sara");
        when(utilisateurRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Utilisateur> result = utilisateurService.getAll();

        assertEquals(2, result.size());
        assertEquals("Ali", result.get(0).getNom());
    }

    @Test
    void testGetByIdSuccess() {
        Utilisateur u = new Utilisateur(); u.setId(1L); u.setNom("Imane");
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(u));

        Optional<Utilisateur> result = utilisateurService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Imane", result.get().getNom());
    }

    @Test
    void testFindByEmail() {
        Utilisateur u = new Utilisateur(); u.setEmail("imane@test.com");
        when(utilisateurRepository.findByEmail("imane@test.com")).thenReturn(Optional.of(u));

        Optional<Utilisateur> result = utilisateurService.findByEmail("imane@test.com");

        assertTrue(result.isPresent());
        assertEquals("imane@test.com", result.get().getEmail());
    }

    @Test
    void testUpdateUtilisateur() {
        Utilisateur original = new Utilisateur();
        original.setId(1L); original.setNom("Ali"); original.setEmail("ali@test.com");

        Utilisateur updated = new Utilisateur();
        updated.setNom("Ali Updated");
        updated.setPrenom("Updated");
        updated.setEmail("updated@test.com");
        updated.setRole(Role.ADMIN);
        updated.setMotDePasse("newpass");

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(original));
        when(utilisateurRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Utilisateur result = utilisateurService.update(1L, updated);

        assertEquals("Ali Updated", result.getNom());
        assertTrue(passwordEncoder.matches("newpass", result.getMotDePasse()));
    }

    @Test
    void testDeleteUtilisateur() {
        utilisateurService.delete(99L);
        verify(utilisateurRepository, times(1)).deleteById(99L);
    }

    @Test
    void testChangePassword() {
        Utilisateur u = new Utilisateur();
        u.setId(1L);
        u.setMotDePasse("old");
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(u));

        utilisateurService.changePassword(1L, "newpass");

        assertTrue(passwordEncoder.matches("newpass", u.getMotDePasse()));
        verify(utilisateurRepository).save(u);
    }

    @Test
    void testConvertirEnDTO() {
        Utilisateur u = new Utilisateur();
        u.setNom("Imane");
        u.setPrenom("El Khadiri");
        u.setEmail("imane@pharma.com");
        u.setRole(Role.ADMIN);

        UtilisateurDTO dto = utilisateurService.convertirEnDTO(u);

        assertEquals("Imane", dto.getNom());
        assertEquals("El Khadiri", dto.getPrenom());
        assertEquals("imane@pharma.com", dto.getEmail());
        assertEquals("ADMIN", dto.getRole());
    }
}
