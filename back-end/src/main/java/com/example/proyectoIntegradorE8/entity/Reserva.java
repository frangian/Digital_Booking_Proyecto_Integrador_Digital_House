package com.example.proyectoIntegradorE8.entity;

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
    @JsonIdentityReference(alwaysAsId = false)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

}
