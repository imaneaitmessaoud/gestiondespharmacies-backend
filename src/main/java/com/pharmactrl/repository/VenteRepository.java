package com.pharmactrl.repository;

import com.pharmactrl.model.Vente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VenteRepository extends JpaRepository<Vente, Long> {
    @Query("SELECT v FROM Vente v LEFT JOIN FETCH v.lignesDeVente")
List<Vente> findAllWithLignes();
@Query("SELECT v FROM Vente v LEFT JOIN FETCH v.lignesDeVente WHERE v.id = :id")
Optional<Vente> findByIdWithLignes(@Param("id") Long id);



}
