package com.example.eco_hospedajes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eco_hospedajes.model.Reserva; // <-- AÑADE ESTA IMPORTACIÓN

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioId(Long usuarioId);

}