package com.example.proyectoIntegradorE8.repository;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository <Producto, Long> {

  List<Producto> findByCategoriaId (Long id);

  List<Producto> findByCiudadId (Long id);

  @Query(value = "select producto from Producto p order by RAND() limit 8;")
  List<Producto> findProductosRandom();


}
