package com.example.proyectoIntegradorE8.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "codigo_reserva", unique = true)
    private String codigoReserva;
    @Column (name = "hora_comienzo", nullable = false)
    private LocalTime horaComienzo;
    @Column (name = "fecha_inicial", nullable = false)
    private LocalDate fechaInicial;
    @Column (name = "fecha_final", nullable = false)
    private LocalDate fechaFinal;
    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;
    @Column (name = "usuario_id")
    private Long usuario;

    //constructores & getters & setters

    public Reserva() {
    }

    public Reserva(Long id, String codigoReserva, LocalTime horaComienzo, LocalDate fechaInicial, LocalDate fechaFinal, Producto producto, Long usuario) {
        this.id = id;
        this.codigoReserva = codigoReserva;
        this.horaComienzo = horaComienzo;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.producto = producto;
        this.usuario = usuario;
    }

    public Reserva(String codigoReserva, LocalTime horaComienzo, LocalDate fechaInicial, LocalDate fechaFinal, Producto producto, Long usuario) {
        this.codigoReserva = codigoReserva;
        this.horaComienzo = horaComienzo;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.producto = producto;
        this.usuario = usuario;
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getHoraComienzo() {
        return horaComienzo;
    }

    public void setHoraComienzo(LocalTime horaComienzo) {
        this.horaComienzo = horaComienzo;
    }

    public LocalDate getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }
}