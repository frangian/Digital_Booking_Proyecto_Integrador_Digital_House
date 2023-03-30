package com.example.proyectoIntegradorE8.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String titulo;
    @Column
    private String direccion;
    @Column
    private String descripcion_producto;
    @Column
    private String descripcion_ubicacion;
    @Column
    private String url_ubicacion;
    @Column
    private String normas;
    @Column
    private String seguridad;
    @Column
    private String cancelacion;
    @Column
    private Integer puntuacion;
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id", nullable = false)
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "ciudad_id", referencedColumnName = "id", nullable = false)
    private Ciudad ciudad;
    @ManyToMany
    @JoinTable(name = "producto_x_caracteristica",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id"))
    private List<Caracteristica> caracteristicas = new ArrayList<>();
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes = new ArrayList<>();
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Reserva> reservas = new ArrayList<>();
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Favorito> favoritos = new ArrayList<>();
    public void agregarImagen(Imagen imagen) {
        imagenes.add(imagen);
        imagen.setProducto(this);
    }
    public void removerImagen(Imagen imagen) {
        imagenes.remove(imagen);
        imagen.setProducto(null);
    }
}
