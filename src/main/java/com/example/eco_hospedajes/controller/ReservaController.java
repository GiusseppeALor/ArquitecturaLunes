package com.example.eco_hospedajes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.eco_hospedajes.model.Reserva;
import com.example.eco_hospedajes.repository.ReservaRepository;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaRepository.save(reserva);
    }
}
