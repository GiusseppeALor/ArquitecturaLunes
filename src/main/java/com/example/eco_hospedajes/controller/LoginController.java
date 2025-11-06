package com.example.eco_hospedajes.controller;

import com.example.eco_hospedajes.model.Usuario;
import com.example.eco_hospedajes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite peticiones desde tu HTML
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 🔹 LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String usuario, @RequestParam String contrasena) {
        Usuario user = usuarioRepository.findByUsuarioAndContrasena(usuario, contrasena);

        if (user != null) {
            return "OK"; // Login correcto
        } else {
            return "ERROR"; // Credenciales inválidas
        }
    }
}
