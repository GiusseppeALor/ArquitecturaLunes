package com.example.eco_hospedajes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eco_hospedajes.model.Reserva;
import com.example.eco_hospedajes.repository.ReservaRepository;
import com.example.eco_hospedajes.repository.UsuarioRepository;
import com.example.eco_hospedajes.repository.HospedajeRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HospedajeRepository hospedajeRepository;

    // Listar todas las reservas
    @GetMapping
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    // Listar reservas por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> listarPorUsuario(@PathVariable Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    // Crear reserva (validando usuario y hospedaje)
    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva) {
        if (reserva.getUsuario() == null || reserva.getUsuario().getId() == null ||
            reserva.getHospedaje() == null || reserva.getHospedaje().getId() == null) {
            return ResponseEntity.badRequest().body("Falta usuario o hospedaje");
        }

        usuarioRepository.findById(reserva.getUsuario().getId())
                .ifPresent(reserva::setUsuario);

        hospedajeRepository.findById(reserva.getHospedaje().getId())
                .ifPresent(reserva::setHospedaje);

        // Por defecto, si quieres puedes asignar estado "Pendiente"
        if (reserva.getEstado() == null) {
            reserva.setEstado("Pendiente");
        }

        Reserva creado = reservaRepository.save(reserva);
        return ResponseEntity.ok(creado);
    }

    // Actualizar estado de reserva
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Reserva datos) {
        Optional<Reserva> r = reservaRepository.findById(id);
        if (r.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reserva reserva = r.get();
        reserva.setEstado(datos.getEstado());
        reservaRepository.save(reserva);
        return ResponseEntity.ok(reserva);
    }

    // Eliminar reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
        if (!reservaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reservaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
