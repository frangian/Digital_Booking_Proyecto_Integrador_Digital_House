package com.example.proyectoIntegradorE8.repository;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenRepository extends JpaRepository <Imagen, Long> {

    List<Imagen> findByProductoId (Long id);
    List<Imagen> findByCategoriaId (Long id);

}
