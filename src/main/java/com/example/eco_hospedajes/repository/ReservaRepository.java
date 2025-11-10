package com.example.eco_hospedajes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eco_hospedajes.model.Hospedaje;
import com.example.eco_hospedajes.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Listar reservas por usuario
    List<Reserva> findByUsuarioId(Long usuarioId);

    /**
     * --- MÉTODO AÑADIDO ---
     * Busca todas las reservas para una lista de hospedajes.
     * Esto se usará para el dashboard del propietario.
     */
    List<Reserva> findByHospedajeIn(List<Hospedaje> hospedajes);

}
