package com.example.eco_hospedajes.controller;

import com.example.eco_hospedajes.model.Usuario;
import com.example.eco_hospedajes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") 
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @GetMapping
    public Iterable<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}
