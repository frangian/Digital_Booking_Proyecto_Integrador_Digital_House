package com.example.proyectoIntegradorE8.repository;


import com.example.proyectoIntegradorE8.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository <Categoria,Long> {
}
