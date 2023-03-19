package com.example.proyectoIntegradorE8.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {
    private long id;
    private String nombre;
    private String apellido;
    private String ciudad;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
