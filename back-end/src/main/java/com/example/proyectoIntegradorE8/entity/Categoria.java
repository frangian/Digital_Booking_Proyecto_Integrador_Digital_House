package com.example.proyectoIntegradorE8.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "titulo")
@Table (name="categoria")
public class Categoria  {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String titulo;
    @Column
    private String descripcion;
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos = new ArrayList<>();
    @OneToMany(mappedBy = "categoria")
    private List<Imagen> imagenes = new ArrayList<>();

    public Categoria(Long id, String titulo, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }
    public Categoria(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }
//    public Categoria() {
//    }
//
//    public Categoria(Long id, String titulo, String descripcion) {
//        this.id = id;
//        this.titulo = titulo;
//        this.descripcion = descripcion;
//    }
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitulo() {
//        return titulo;
//    }
//
//    public void setTitulo(String titulo) {
//        this.titulo = titulo;
//    }
//
//    public String getDescripcion() {
//        return descripcion;
//    }
//
//    public void setDescripcion(String descripcion) {
//        this.descripcion = descripcion;
//    }
//
//    public Set<Imagen> getImagenes() {
//        return imagenes;
//    }
//
//    public void setImagenes(Set<Imagen> imagenes) {
//        this.imagenes = imagenes;
//    }
//
//    public Set<Producto> getProductos() {
//        return productos;
//    }
//
//    public void setProductos(Set<Producto> productos) {
//        this.productos = productos;
//    }
}
