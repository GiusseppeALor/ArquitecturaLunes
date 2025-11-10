package com.example.eco_hospedajes.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eco_hospedajes.model.Reserva;
import com.example.eco_hospedajes.repository.ReservaRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    // ✅ Crear nueva reserva
    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    // ✅ Obtener reservas por usuario (historial)
    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    // ✅ Obtener todas las reservas (vista del dueño)
    @GetMapping
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    // ✅ Actualizar estado de una reserva (Confirmada / Cancelada / Pendiente)
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");

        Optional<Reserva> optionalReserva = reservaRepository.findById(id);
        if (!optionalReserva.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Reserva reserva = optionalReserva.get();
        reserva.setEstado(nuevoEstado);
        reservaRepository.save(reserva);

        return ResponseEntity.ok(reserva);
    }

    // ✅ Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
        if (!reservaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reservaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
