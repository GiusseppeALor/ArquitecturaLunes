package com.example.eco_hospedajes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eco_hospedajes.model.Hospedaje;

@Repository
public interface HospedajeRepository extends JpaRepository<Hospedaje, Long> {

    /**
     * Busca todos los hospedajes asociados a un ID de propietario específico.
     * Spring Data JPA entiende "findByPropietarioId" y automáticamente
     * busca por el campo "propietario.id" dentro de tu entidad Hospedaje.
     */
    List<Hospedaje> findByPropietarioId(Long propietarioId);
}
