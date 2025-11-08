package com.example.eco_hospedajes.repository;

import com.example.eco_hospedajes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmailAndContrasena(String email, String contrasena);
}
