package com.example.proyectoIntegradorE8.repository;

import com.example.proyectoIntegradorE8.entity.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaractersiticaRepository extends JpaRepository <Caracteristica,Long> {

    @Query(value = "SELECT c.* FROM caracteristica c INNER JOIN producto_x_caracteristica pc ON c.id = pc.caracteristica_id WHERE pc.producto_id = :productoId", nativeQuery = true)
    List<Caracteristica> findAllByProductoId(@Param("productoId") Long productoId);
}
