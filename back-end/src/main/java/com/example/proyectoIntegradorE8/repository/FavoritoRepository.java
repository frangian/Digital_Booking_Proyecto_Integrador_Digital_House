package com.example.proyectoIntegradorE8.repository;

import com.example.proyectoIntegradorE8.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoritoRepository extends JpaRepository <Favorito, Long> {
    List<Favorito> findByUsuarioId (Long id);
}
