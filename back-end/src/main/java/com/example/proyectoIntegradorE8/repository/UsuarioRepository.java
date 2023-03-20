package com.example.proyectoIntegradorE8.repository;

import com.example.proyectoIntegradorE8.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findOneByEmail(String email);
}
