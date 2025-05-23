package com.pharmactrl.security;

import com.pharmactrl.model.Utilisateur;
import com.pharmactrl.repository.UtilisateurRepositoray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepositoray utilisateurRepository;
//appelee automatiquement par spring security
    // On charge l'utilisateur par son email
    // On utilise l'interface UserDetailsService de Spring Security
    // pour charger l'utilisateur et ses rôles
    // On doit implémenter la méthode loadUserByUsername
    // qui est appelée par Spring Security lors de l'authentification
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // On retourne un User spring security
        return new User(
                utilisateur.getEmail(),
                utilisateur.getMotDePasse(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name()))
        );
    }
}
