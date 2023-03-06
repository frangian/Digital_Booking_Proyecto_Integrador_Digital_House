package com.example.proyectoIntegradorE8.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "imagen")
public class Imagenes {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String titulo;
    @Column
    private String url_imagen;

    public Imagenes() {
    }

    public Imagenes(Long id, String titulo, String url_imagen) {
        this.id = id;
        this.titulo = titulo;
        this.url_imagen = url_imagen;
    }

    public Imagenes(String titulo, String url_imagen) {
        this.titulo = titulo;
        this.url_imagen = url_imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
