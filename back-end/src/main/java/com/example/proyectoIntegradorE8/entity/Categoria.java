package com.example.proyectoIntegradorE8.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

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
}
