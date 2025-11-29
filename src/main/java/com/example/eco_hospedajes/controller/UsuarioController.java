package com.example.eco_hospedajes.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eco_hospedajes.model.Usuario;
import com.example.eco_hospedajes.repository.UsuarioRepository;

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

    
    @PostMapping("/dueno")
    public Usuario registrarDueno(@RequestBody Usuario usuario) {
        usuario.setRol("DUEÑO");
        return usuarioRepository.save(usuario);
    }
    @GetMapping
    public Iterable<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPerfilUsuario(@PathVariable Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElse(null); 

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarPerfilUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuarioActualizado) {

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElse(null);

        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setApellidos(usuarioActualizado.getApellidos());

        Usuario usuarioGuardado = usuarioRepository.save(usuarioExistente);

        return ResponseEntity.ok(usuarioGuardado);
    }

    @PatchMapping("/{id}/contrasena")
    public ResponseEntity<String> actualizarContrasena(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        String nuevaContrasena = body.get("contrasena");

        if (nuevaContrasena == null || nuevaContrasena.isEmpty()) {
            return ResponseEntity.badRequest().body("La contraseña no puede estar vacía.");
        }

        usuario.setContrasena(nuevaContrasena);

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}
