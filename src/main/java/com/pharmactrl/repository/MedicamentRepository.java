package com.pharmactrl.repository;

import com.pharmactrl.model.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {}
