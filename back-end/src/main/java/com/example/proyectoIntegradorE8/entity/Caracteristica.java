package com.example.proyectoIntegradorE8.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table (name = "caracteristica")
public class Caracteristica {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String titulo;
    @ManyToMany(mappedBy = "caracteristicas")
    @JsonIgnore
    private List<Producto> productos = new ArrayList<>();

    public Caracteristica() {
    }

//    public Caracteristica(Long id, String titulo) {
//        this.id = id;
//        this.titulo = titulo;
//    }
//
//    public Caracteristica(String titulo) {
//        this.titulo = titulo;
//    }
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
//    public Set<Producto> getProductos() {
//        return productos;
//    }
//
//    public void setProductos(Set<Producto> productos) {
//        this.productos = productos;
//    }

}
