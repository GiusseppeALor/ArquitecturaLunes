package com.example.eco_hospedajes.controller;

import java.util.*;
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

    // ✅ Obtener todas las reservas (vista del dueño con nombre de cliente y fechas correctas)
    @GetMapping
public List<Map<String, Object>> obtenerTodasLasReservas() {
    List<Reserva> reservas = reservaRepository.findAll();
    List<Map<String, Object>> respuesta = new ArrayList<>();

    for (Reserva r : reservas) {
        Map<String, Object> item = new HashMap<>();
        item.put("id", r.getId());
        item.put("estado", r.getEstado());

        // ✅ Convertir LocalDate a String legible (yyyy-MM-dd)
        item.put("fechaInicio", (r.getCheckin() != null) ? r.getCheckin().toString() : "—");
        item.put("fechaFin", (r.getCheckout() != null) ? r.getCheckout().toString() : "—");

        // Datos del hospedaje
        if (r.getHospedaje() != null) {
            Map<String, Object> hospedajeData = new HashMap<>();
            hospedajeData.put("id", r.getHospedaje().getId());
            hospedajeData.put("nombre", r.getHospedaje().getNombre());
            item.put("hospedaje", hospedajeData);
        } else {
            item.put("hospedaje", null);
        }

        // Datos del cliente (usuario)
        if (r.getUsuario() != null) {
            item.put("clienteNombre", r.getUsuario().getNombre());
        } else {
            item.put("clienteNombre", "Sin cliente");
        }

        respuesta.add(item);
    }

    return respuesta;
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
