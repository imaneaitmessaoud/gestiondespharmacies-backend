package com.pharmactrl.repository;

import com.pharmactrl.model.Quantite;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantiteRepository extends JpaRepository<Quantite, Long> {
    List<Quantite> findByVenteId(Long venteId);

}
