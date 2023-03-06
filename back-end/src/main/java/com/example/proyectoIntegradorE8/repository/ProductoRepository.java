package com.example.proyectoIntegradorE8.repository;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository <Producto, Long> {

  List<Producto> findByCategoriaId (Long id);

}
