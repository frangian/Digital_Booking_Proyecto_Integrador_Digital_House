package com.example.proyectoIntegradorE8.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "caracteristica")
public class Caracteristica {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String titulo;

    public Caracteristica(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Caracteristica(String titulo) {
        this.titulo = titulo;
    }

    public Caracteristica() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
