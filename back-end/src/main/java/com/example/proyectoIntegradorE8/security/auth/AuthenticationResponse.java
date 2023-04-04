package com.example.proyectoIntegradorE8.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String token;

}