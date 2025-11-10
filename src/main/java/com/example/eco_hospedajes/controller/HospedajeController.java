package com.example.eco_hospedajes.controller;

import com.example.eco_hospedajes.model.Hospedaje;
import com.example.eco_hospedajes.repository.HospedajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedajes")
@CrossOrigin(origins = "*")
public class HospedajeController {

    @Autowired
    private HospedajeRepository hospedajeRepository;

    // Listar todos los hospedajes
    @GetMapping
    public List<Hospedaje> listarHospedajes() {
        return hospedajeRepository.findAll();
    }

    // Obtener hospedaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<Hospedaje> obtenerHospedajePorId(@PathVariable Long id) {
        return hospedajeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Agregar nuevo hospedaje
    @PostMapping
    public ResponseEntity<Hospedaje> agregarHospedaje(@RequestBody Hospedaje hospedaje) {
        Hospedaje creado = hospedajeRepository.save(hospedaje);
        return ResponseEntity.ok(creado);
    }

    // Actualizar hospedaje
    @PutMapping("/{id}")
    public ResponseEntity<Hospedaje> actualizarHospedaje(@PathVariable Long id,
                                                         @RequestBody Hospedaje datos) {
        return hospedajeRepository.findById(id).map(h -> {
            h.setNombre(datos.getNombre());
            h.setUbicacion(datos.getUbicacion());
            h.setPrecio(datos.getPrecio());
            h.setDescripcion(datos.getDescripcion());
            h.setImagenUrl(datos.getImagenUrl());
            h.setEstado(datos.isEstado());
            h.setPropietario(datos.getPropietario());

            hospedajeRepository.save(h);
            return ResponseEntity.ok(h);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar hospedaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHospedaje(@PathVariable Long id) {
        if (!hospedajeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        hospedajeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Listar hospedajes por propietario
    @GetMapping("/propietario/{propietarioId}")
    public List<Hospedaje> listarPorPropietario(@PathVariable Long propietarioId) {
        return hospedajeRepository.findByPropietarioId(propietarioId);
    }
}
