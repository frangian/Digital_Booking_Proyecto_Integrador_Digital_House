package com.example.proyectoIntegradorE8.repository;

import com.example.proyectoIntegradorE8.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservaRepository extends JpaRepository <Reserva, Long> {
    List<Reserva> findByProductoId (Long id);

}
