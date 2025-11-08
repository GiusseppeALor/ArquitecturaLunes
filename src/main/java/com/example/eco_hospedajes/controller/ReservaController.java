package com.example.eco_hospedajes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // <-- IMPORTACIÓN AÑADIDA
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        // --- NOTA IMPORTANTE ---
        // Para que esto funcione, el JSON que envíes desde el frontend
        // debe incluir los IDs de usuario y hospedaje.
        // Ej: { "checkin": "...", "usuario": {"id": 5}, "hospedaje": {"id": 2} }
        return reservaRepository.save(reserva);
    }

    // --- V NUEVO MÉTODO PARA EL HISTORIAL V ---

    /**
     * Este método obtiene todas las reservas de un usuario específico.
     * Se accede con una URL como: GET /api/reservas/usuario/5
     */
    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        // Usamos el método que creamos en el Repositorio
        return reservaRepository.findByUsuarioId(usuarioId);
    }
}