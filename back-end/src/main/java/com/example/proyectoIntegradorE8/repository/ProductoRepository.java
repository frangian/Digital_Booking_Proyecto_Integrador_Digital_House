package com.example.proyectoIntegradorE8.repository;

import com.example.proyectoIntegradorE8.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ProductoRepository extends JpaRepository <Producto, Long> {

  List<Producto> findByCategoriaId (Long id);

  List<Producto> findByCiudadId (Long id);

  @Query("SELECT p FROM Producto p WHERE NOT EXISTS (SELECT r FROM Reserva r WHERE r.producto = p AND ((r.fechaInicial >= :fechaInicio AND r.fechaInicial <= :fechaFin) OR (r.fechaFinal >= :fechaInicio AND r.fechaFinal <= :fechaFin) OR (r.fechaInicial < :fechaInicio AND r.fechaFinal > :fechaFin)))")
  List<Producto> findByProductoFechas(@Param("fechaInicio") LocalDate fechaInicial, @Param("fechaFin") LocalDate fechaFinal);

}
