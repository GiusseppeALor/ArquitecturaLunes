package com.example.eco_hospedajes.controller;

import com.example.eco_hospedajes.model.Usuario;
import com.example.eco_hospedajes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public static class RegistroRequest {
        public String nombre;
        public String apellidos;
        public String email;
        public String contrasena;
    }

    @PostMapping("/registro")
    public Usuario registrarUsuario(@RequestBody RegistroRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre);
        usuario.setApellidos(request.apellidos);
        usuario.setEmail(request.email);
        usuario.setContrasena(request.contrasena);
        usuario.setRol("CLIENTE");

        return usuarioRepository.save(usuario);
    }
}
