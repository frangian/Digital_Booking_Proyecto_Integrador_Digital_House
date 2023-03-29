package com.example.proyectoIntegradorE8.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
