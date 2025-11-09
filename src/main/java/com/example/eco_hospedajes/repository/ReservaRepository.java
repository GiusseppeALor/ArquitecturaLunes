package com.example.eco_hospedajes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eco_hospedajes.model.Hospedaje; // <-- AÑADE ESTA IMPORTACIÓN
import com.example.eco_hospedajes.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioId(Long usuarioId);

    /**
     * --- MÉTODO AÑADIDO ---
     * Busca todas las reservas para una lista de hospedajes.
     * Esto es lo que usaremos para el dashboard del dueño.
     */
    List<Reserva> findByHospedajeIn(List<Hospedaje> hospedajes);

}