package com.example.eco_hospedajes.repository;

import com.example.eco_hospedajes.model.Hospedaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospedajeRepository extends JpaRepository<Hospedaje, Long> {
}

