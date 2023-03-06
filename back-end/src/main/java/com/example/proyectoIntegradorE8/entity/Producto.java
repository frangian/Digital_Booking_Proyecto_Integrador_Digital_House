package com.example.proyectoIntegradorE8.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Categoria_id", referencedColumnName = "id")
    private Categoria categoria;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Ciudad_id", referencedColumnName = "id")
    private Ciudad ciudad;

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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Imagenes_id", referencedColumnName = "id")
    private List<Imagenes> imagenes = new ArrayList<Imagenes>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Caracteristica_id", referencedColumnName = "id")
    private List<Caracteristica> caracterisitcas = new ArrayList<Caracteristica>();

    public Producto() {
    }

    public Producto(Categoria categoria, Ciudad ciudad, String descripcion_producto, String descripcion_ubicacion, String url_ubicacion, String normas, String seguridad, String cancelacion, Integer puntuacion, List<Imagenes> imagenes, List<Caracteristica> caracterisitcas) {
        this.categoria = categoria;
        this.ciudad = ciudad;
        this.descripcion_producto = descripcion_producto;
        this.descripcion_ubicacion = descripcion_ubicacion;
        this.url_ubicacion = url_ubicacion;
        this.normas = normas;
        this.seguridad = seguridad;
        this.cancelacion = cancelacion;
        this.puntuacion = puntuacion;
        this.imagenes = imagenes;
        this.caracterisitcas = caracterisitcas;
    }

    public Producto(Long id, Categoria categoria, Ciudad ciudad, String descripcion_producto, String descripcion_ubicacion, String url_ubicacion, String normas, String seguridad, String cancelacion, Integer puntuacion, List<Imagenes> imagenes, List<Caracteristica> caracterisitcas) {
        this.id = id;
        this.categoria = categoria;
        this.ciudad = ciudad;
        this.descripcion_producto = descripcion_producto;
        this.descripcion_ubicacion = descripcion_ubicacion;
        this.url_ubicacion = url_ubicacion;
        this.normas = normas;
        this.seguridad = seguridad;
        this.cancelacion = cancelacion;
        this.puntuacion = puntuacion;
        this.imagenes = imagenes;
        this.caracterisitcas = caracterisitcas;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Imagenes> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagenes> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Caracteristica> getCaracterisitcas() {
        return caracterisitcas;
    }

    public void setCaracterisitcas(List<Caracteristica> caracterisitcas) {
        this.caracterisitcas = caracterisitcas;
    }
}
