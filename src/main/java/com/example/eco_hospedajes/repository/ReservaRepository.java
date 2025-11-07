package com.example.eco_hospedajes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.eco_hospedajes.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
