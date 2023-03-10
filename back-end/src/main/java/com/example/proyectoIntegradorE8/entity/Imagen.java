package com.example.proyectoIntegradorE8.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table (name = "imagen")
public class Imagen {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String titulo;
    @Column
    private String url_imagen;
    @ManyToOne
    @JsonIgnore
    @JoinColumn (name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;
    @ManyToOne
    @JsonIgnore
    @JoinColumn (name = "producto_id", referencedColumnName = "id")
    private Producto producto;

    //constructores & getters & setters

    public Imagen() {
    }

    public Imagen(Long id, String titulo, String url_imagen) {
        this.id = id;
        this.titulo = titulo;
        this.url_imagen = url_imagen;
    }

    public Imagen(String titulo, String url_imagen) {
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
