package com.example.eco_hospedajes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eco_hospedajes.model.Hospedaje; // <-- AÑADE ESTA IMPORTACIÓN

@Repository
public interface HospedajeRepository extends JpaRepository<Hospedaje, Long> {
    List<Hospedaje> findByPropietarioId(Long propietarioId);

}