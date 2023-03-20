package com.example.proyectoIntegradorE8.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "codigo_reserva", unique = true)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String codigoReserva;
    @Column (name = "hora_comienzo", nullable = false)
    @Schema(type = "string", format = "time", pattern = "HH:mm:ss")
    private LocalTime horaComienzo;
    @Column (name = "fecha_inicial", nullable = false)
    private LocalDate fechaInicial;
    @Column (name = "fecha_final", nullable = false)
    private LocalDate fechaFinal;
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

//    public Reserva() {
//    }
//
//    public Reserva(Long id, String codigoReserva, LocalTime horaComienzo, LocalDate fechaInicial, LocalDate fechaFinal, Producto producto, Usuario usuario) {
//        this.id = id;
//        this.codigoReserva = codigoReserva;
//        this.horaComienzo = horaComienzo;
//        this.fechaInicial = fechaInicial;
//        this.fechaFinal = fechaFinal;
//        this.producto = producto;
//        this.usuario = usuario;
//    }
//
//    public Reserva(String codigoReserva, LocalTime horaComienzo, LocalDate fechaInicial, LocalDate fechaFinal, Producto producto, Usuario usuario) {
//        this.codigoReserva = codigoReserva;
//        this.horaComienzo = horaComienzo;
//        this.fechaInicial = fechaInicial;
//        this.fechaFinal = fechaFinal;
//        this.producto = producto;
//        this.usuario = usuario;
//    }
//
//    public String getCodigoReserva() {
//        return codigoReserva;
//    }
//
//    public void setCodigoReserva(String codigoReserva) {
//        this.codigoReserva = codigoReserva;
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
//    public LocalTime getHoraComienzo() {
//        return horaComienzo;
//    }
//
//    public void setHoraComienzo(LocalTime horaComienzo) {
//        this.horaComienzo = horaComienzo;
//    }
//
//    public LocalDate getFechaInicial() {
//        return fechaInicial;
//    }
//
//    public void setFechaInicial(LocalDate fechaInicial) {
//        this.fechaInicial = fechaInicial;
//    }
//
//    public LocalDate getFechaFinal() {
//        return fechaFinal;
//    }
//
//    public void setFechaFinal(LocalDate fechaFinal) {
//        this.fechaFinal = fechaFinal;
//    }
//
//    public Producto getProducto() {
//        return producto;
//    }
//
//    public void setProducto(Producto producto) {
//        this.producto = producto;
//    }
//
//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }
}
