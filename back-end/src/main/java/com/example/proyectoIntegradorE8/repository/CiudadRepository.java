package com.example.proyectoIntegradorE8.repository;

import com.example.proyectoIntegradorE8.entity.Ciudad;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CiudadRepository extends JpaRepository <Ciudad, Long> {

//  List<Ciudad> findByCiudadId (Long id);
}
