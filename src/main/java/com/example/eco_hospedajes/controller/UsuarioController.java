package com.example.eco_hospedajes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping; // <-- IMPORTANTE: Añade esta importación
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

    @GetMapping
    public Iterable<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Este método se usa para obtener los datos de un solo usuario por su ID.
     * Se accede con una URL como: GET /api/usuarios/5
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPerfilUsuario(@PathVariable Long id) {

        // Busca al usuario en la base de datos por su ID
        Usuario usuario = usuarioRepository.findById(id)
                .orElse(null); // Retorna null si no lo encuentra

        if (usuario != null) {
            // Si se encuentra, retorna los datos del usuario (código 200 OK)
            return ResponseEntity.ok(usuario);
        } else {
            // Si no se encuentra, retorna un error 404 (No Encontrado)
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarPerfilUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuarioActualizado) {

        // 1. Buscar al usuario existente por su ID
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElse(null);

        // 2. Comprobar si existe
        if (usuarioExistente == null) {
            // Si no existe, devolvemos 404 Not Found
            return ResponseEntity.notFound().build();
        }

        // 3. Actualizar los campos del usuario existente con los datos nuevos
        // (No actualizamos el ID, el email, la contraseña o el rol en este método
        // simple)
        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setApellidos(usuarioActualizado.getApellidos());
        // ... (puedes añadir más campos aquí, como 'telefono', etc.)

        // 4. Guardar los cambios en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuarioExistente);

        // 5. Devolver el usuario actualizado
        return ResponseEntity.ok(usuarioGuardado);
    }
}
