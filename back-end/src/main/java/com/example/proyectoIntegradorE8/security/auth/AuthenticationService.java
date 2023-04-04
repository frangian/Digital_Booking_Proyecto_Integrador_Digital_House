package com.example.proyectoIntegradorE8.security.auth;

import com.example.proyectoIntegradorE8.entity.Usuario;
import com.example.proyectoIntegradorE8.entity.UsuarioRole;
import com.example.proyectoIntegradorE8.repository.UsuarioRepository;
import com.example.proyectoIntegradorE8.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .usuarioRole(UsuarioRole.USER)
                .build();
        usuarioRepository.save(usuario);
        var jwtToken = jwtService.generateToken(usuario);
        return AuthenticationResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var usuario = usuarioRepository.findOneByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(usuario);
        return AuthenticationResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .token(jwtToken)
                .build();
    }
}