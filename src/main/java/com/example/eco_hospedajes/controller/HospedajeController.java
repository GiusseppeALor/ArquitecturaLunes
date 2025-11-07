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

    @GetMapping
    public List<Hospedaje> listarHospedajes() {
        return hospedajeRepository.findAll();
    }

    @PostMapping
    public Hospedaje agregarHospedaje(@RequestBody Hospedaje hospedaje) {
        return hospedajeRepository.save(hospedaje);
    }
}
