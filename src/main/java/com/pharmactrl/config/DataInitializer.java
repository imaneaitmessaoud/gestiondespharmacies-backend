package com.pharmactrl.config;

import com.pharmactrl.model.Role;
import com.pharmactrl.model.Utilisateur;
import com.pharmactrl.repository.UtilisateurRepositoray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UtilisateurRepositoray utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Admin
        if (utilisateurRepository.findByEmail("admin@pharma.com").isEmpty()) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setPrenom("Principal");
            admin.setEmail("admin@pharma.com");
            admin.setMotDePasse(passwordEncoder.encode("123456"));
            admin.setRole(Role.ADMIN);
            utilisateurRepository.save(admin);
            System.out.println(" Admin créé : admin@pharma.com / 123456");
        } else {
            System.out.println("ℹ Admin déjà existant.");
        }
    
        // Utilisateur test
        if (utilisateurRepository.findByEmail("test@pharma.com").isEmpty()) {
            Utilisateur user = new Utilisateur();
            user.setNom("Test");
            user.setPrenom("Utilisateur");
            user.setEmail("test@pharma.com");
            user.setMotDePasse(passwordEncoder.encode("azerty123"));
            user.setRole(Role.PHARMACIEN); // ou Role.ADMIN si tu veux tester pareil
            utilisateurRepository.save(user);
            System.out.println(" Utilisateur test créé : test@pharma.com / azerty123");
        } else {
            System.out.println("ℹ Utilisateur test déjà existant.");
        }
        
    
    }
}