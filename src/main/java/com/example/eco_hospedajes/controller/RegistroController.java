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

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String usuario,
                                   @RequestParam String contrasena,
                                   @RequestParam String nombre,
                                   @RequestParam String correo,
                                   @RequestParam(defaultValue = "cliente") String rol) {

        // Verificar si el usuario ya existe
        Usuario existente = usuarioRepository.findByUsuarioAndContrasena(usuario, contrasena);
        if (existente != null) {
            return "EXISTE";
        }

        // Crear nuevo usuario
        Usuario nuevo = new Usuario();
        nuevo.setUsuario(usuario);
        nuevo.setContrasena(contrasena);
        nuevo.setNombre(nombre);
        nuevo.setRol(rol);
        // Si agregaste el campo correo en tu modelo:
        // nuevo.setCorreo(correo);

        usuarioRepository.save(nuevo);
        return "OK";
    }
}
