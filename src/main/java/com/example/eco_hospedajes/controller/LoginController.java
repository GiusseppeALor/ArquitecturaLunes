package com.example.eco_hospedajes.controller;

import com.example.eco_hospedajes.model.Usuario;
import com.example.eco_hospedajes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Clase interna para recibir el JSON del frontend
    public static class LoginRequest {
        public String email;
        public String contrasena;
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request) {
        // Buscar el usuario por correo y contraseña
        Usuario user = usuarioRepository.findByEmailAndContrasena(request.email, request.contrasena);

        if (user != null) {
            return user; // Devuelve el usuario en JSON
        } else {
            return new ErrorResponse("Credenciales incorrectas");
        }
    }

    // Respuesta de error
    public static class ErrorResponse {
        public String mensaje;
        public ErrorResponse(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}
