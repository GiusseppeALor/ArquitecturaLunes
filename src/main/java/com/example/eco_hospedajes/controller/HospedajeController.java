package com.example.eco_hospedajes.controller;

import com.example.eco_hospedajes.model.Hospedaje;
import com.example.eco_hospedajes.repository.HospedajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedajes")
@CrossOrigin(origins = "*")
public class HospedajeController {

    @Autowired
    private HospedajeRepository hospedajeRepository;

    // ✅ Listar todos los hospedajes
    @GetMapping
    public List<Hospedaje> listarHospedajes() {
        return hospedajeRepository.findAll();
    }

    // ✅ Obtener hospedaje por ID
    @GetMapping("/{id}")
    public Hospedaje obtenerHospedajePorId(@PathVariable Long id) {
        return hospedajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospedaje no encontrado con ID: " + id));
    }

    // ✅ Agregar nuevo hospedaje
    @PostMapping
    public Hospedaje agregarHospedaje(@RequestBody Hospedaje hospedaje) {
        return hospedajeRepository.save(hospedaje);
    }

    // ✅ Actualizar hospedaje existente
    @PutMapping("/{id}")
    public Hospedaje actualizarHospedaje(@PathVariable Long id, @RequestBody Hospedaje hospedajeActualizado) {
        Hospedaje hospedaje = hospedajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospedaje no encontrado con ID: " + id));

        hospedaje.setNombre(hospedajeActualizado.getNombre());
        hospedaje.setUbicacion(hospedajeActualizado.getUbicacion());
        hospedaje.setPrecio(hospedajeActualizado.getPrecio());
        hospedaje.setImagenUrl(hospedajeActualizado.getImagenUrl());
        hospedaje.setDescripcion(hospedajeActualizado.getDescripcion());

        return hospedajeRepository.save(hospedaje);
    }

    // ✅ Eliminar hospedaje
    @DeleteMapping("/{id}")
    public void eliminarHospedaje(@PathVariable Long id) {
        if (!hospedajeRepository.existsById(id)) {
            throw new RuntimeException("Hospedaje no encontrado con ID: " + id);
        }
        hospedajeRepository.deleteById(id);
    }
}
