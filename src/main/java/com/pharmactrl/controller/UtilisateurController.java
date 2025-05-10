package com.pharmactrl.controller;

import com.pharmactrl.dto.UtilisateurDTO;
import com.pharmactrl.model.Utilisateur;
import com.pharmactrl.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public List<Utilisateur> getAll() {
        return utilisateurService.getAll();
    }
   
    @GetMapping("/{id}")
    public Optional<Utilisateur> getById(@PathVariable Long id) {
        return utilisateurService.getById(id);
    }

    @PostMapping
    public Utilisateur create(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.create(utilisateur);
    }

    @PutMapping("/{id}")
    public Utilisateur update(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        return utilisateurService.update(id, utilisateur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        utilisateurService.delete(id);
    }
   
@GetMapping("/dto")
public List<UtilisateurDTO> getAllDTOs() {
    return utilisateurService.getAllUtilisateurs().stream()
            .map(utilisateurService::convertirEnDTO)
            .collect(Collectors.toList());
}
@PutMapping("/password/{id}")
public void changePassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
    utilisateurService.changePassword(id, body.get("newPassword"));
}

}
