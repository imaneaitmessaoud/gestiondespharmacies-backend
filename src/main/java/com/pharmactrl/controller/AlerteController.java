package com.pharmactrl.controller;

import java.util.List;
import com.pharmactrl.model.Alerte;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmactrl.service.AlerteService;

@RestController
@RequestMapping("/api/alertes")
public class AlerteController {

    @Autowired
    private AlerteService alerteService;

    @GetMapping
    public List<Alerte> getAlertes() {
        return alerteService.getAlertes();
    }

    @PutMapping("/{id}/lue")
    public Alerte marquerCommeLue(@PathVariable Long id) {
        return alerteService.marquerCommeLue(id);
    }
@PostMapping("/verifier")
public void verifierManuellement() {
    alerteService.verifierTousLesMedicaments();
}

    
    

}
