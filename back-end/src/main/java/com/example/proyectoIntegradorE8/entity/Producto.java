package com.example.proyectoIntegradorE8.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String titulo;
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ciudad_id", referencedColumnName = "id")
    private Ciudad ciudad;
    @OneToMany(mappedBy = "producto")
    private Set<Imagen> imagenes;


    public Producto() {
    }

    public Producto(Long id, String titulo, String descripcion_producto, String descripcion_ubicacion, String url_ubicacion, String normas, String seguridad, String cancelacion, Integer puntuacion, Categoria categoria, Ciudad ciudad) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion_producto = descripcion_producto;
        this.descripcion_ubicacion = descripcion_ubicacion;
        this.url_ubicacion = url_ubicacion;
        this.normas = normas;
        this.seguridad = seguridad;
        this.cancelacion = cancelacion;
        this.puntuacion = puntuacion;
        this.categoria = categoria;
        this.ciudad = ciudad;
    }

    public Producto(String titulo, String descripcion_producto, String descripcion_ubicacion, String url_ubicacion, String normas, String seguridad, String cancelacion, Integer puntuacion, Categoria categoria, Ciudad ciudad) {
        this.titulo = titulo;
        this.descripcion_producto = descripcion_producto;
        this.descripcion_ubicacion = descripcion_ubicacion;
        this.url_ubicacion = url_ubicacion;
        this.normas = normas;
        this.seguridad = seguridad;
        this.cancelacion = cancelacion;
        this.puntuacion = puntuacion;
        this.categoria = categoria;
        this.ciudad = ciudad;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion_producto() {
        return descripcion_producto;
    }

    public void setDescripcion_producto(String descripcion_producto) {
        this.descripcion_producto = descripcion_producto;
    }

    public String getDescripcion_ubicacion() {
        return descripcion_ubicacion;
    }

    public void setDescripcion_ubicacion(String descripcion_ubicacion) {
        this.descripcion_ubicacion = descripcion_ubicacion;
    }

    public String getUrl_ubicacion() {
        return url_ubicacion;
    }

    public void setUrl_ubicacion(String url_ubicacion) {
        this.url_ubicacion = url_ubicacion;
    }

    public String getNormas() {
        return normas;
    }

    public void setNormas(String normas) {
        this.normas = normas;
    }

    public String getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(String seguridad) {
        this.seguridad = seguridad;
    }

    public String getCancelacion() {
        return cancelacion;
    }

    public void setCancelacion(String cancelacion) {
        this.cancelacion = cancelacion;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Set<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(Set<Imagen> imagenes) {
        this.imagenes = imagenes;
    }
}
