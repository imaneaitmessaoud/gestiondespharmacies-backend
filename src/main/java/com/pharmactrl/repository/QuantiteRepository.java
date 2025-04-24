package com.pharmactrl.repository;

import com.pharmactrl.model.Quantite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantiteRepository extends JpaRepository<Quantite, Long> {
}
