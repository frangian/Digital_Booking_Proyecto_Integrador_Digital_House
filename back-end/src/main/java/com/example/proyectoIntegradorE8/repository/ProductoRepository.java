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

  //No borrar por ahora:
//  @Query(value="SELECT * FROM producto p " +
//          "WHERE p.ciudad_id = :ciudadId", nativeQuery = true)
//  List<Producto> findByCiudadId(@Param("ciudadId") Long id);
//  //----------------------------------------------------------

  @Query("SELECT p FROM Producto p " +
            "WHERE NOT EXISTS " +
              "(SELECT r FROM Reserva r " +
                "WHERE r.producto = p " +
                "AND ((r.fechaInicial >= :fechaInicio AND r.fechaInicial <= :fechaFin) " +
                "OR (r.fechaFinal >= :fechaInicio AND r.fechaFinal <= :fechaFin) " +
                "OR (r.fechaInicial < :fechaInicio AND r.fechaFinal > :fechaFin)))")
  List<Producto> findByProductoFechas(@Param("fechaInicio") LocalDate fechaInicial, @Param("fechaFin") LocalDate fechaFinal);

  @Query("SELECT p FROM Producto p " +
          "WHERE p.ciudad.id = :ciudadId " +
          "AND NOT EXISTS " +
          "(SELECT r FROM Reserva r " +
          "WHERE r.producto = p " +
          "AND ((r.fechaInicial >= :fechaInicio AND r.fechaInicial <= :fechaFin) " +
          "OR (r.fechaFinal >= :fechaInicio AND r.fechaFinal <= :fechaFin) " +
          "OR (r.fechaInicial < :fechaInicio AND r.fechaFinal > :fechaFin)))")
  List<Producto> findByCiudadIdAndProductoFechas(@Param("ciudadId") Long ciudadId, @Param("fechaInicio") LocalDate fechaInicial, @Param("fechaFin") LocalDate fechaFinal);

  @Query("SELECT p FROM Producto p " +
          "WHERE p.categoria.id = :categoriaId " +
          "AND NOT EXISTS " +
          "(SELECT r FROM Reserva r " +
          "WHERE r.producto = p " +
          "AND ((r.fechaInicial >= :fechaInicio AND r.fechaInicial <= :fechaFin) " +
          "OR (r.fechaFinal >= :fechaInicio AND r.fechaFinal <= :fechaFin) " +
          "OR (r.fechaInicial < :fechaInicio AND r.fechaFinal > :fechaFin)))")
  List<Producto> findByCategoriaIdAndProductoFechas(@Param("categoriaId") Long categoriaId, @Param("fechaInicio") LocalDate fechaInicial, @Param("fechaFin") LocalDate fechaFinal);

  @Query("SELECT p FROM Producto p WHERE p.ciudad.id = :ciudadId AND p.categoria.id = :categoriaId")
  List<Producto> findByCategoriaIdAndCiudadId(@Param("ciudadId") Long ciudadId, @Param("categoriaId") Long categoriaId);

  @Query("SELECT p FROM Producto p WHERE p.titulo LIKE :productoTitulo%")
  List<Producto> findByProductoXTitulo(@Param("productoTitulo") String productoTitulo);

  @Query("SELECT p FROM Producto p " +
          "WHERE p.ciudad.id = :ciudadId AND p.categoria.id = :categoriaId " +
          "AND NOT EXISTS " +
          "(SELECT r FROM Reserva r " +
          "WHERE r.producto = p " +
          "AND ((r.fechaInicial >= :fechaInicio AND r.fechaInicial <= :fechaFin) " +
          "OR (r.fechaFinal >= :fechaInicio AND r.fechaFinal <= :fechaFin) " +
          "OR (r.fechaInicial < :fechaInicio AND r.fechaFinal > :fechaFin)))")
  List<Producto> findByCiudadIdAndCategoriaIdAndFechas(@Param("ciudadId") Long ciudadId,@Param("categoriaId") Long categoriaId ,@Param("fechaInicio") LocalDate fechaInicial, @Param("fechaFin") LocalDate fechaFinal);



}
