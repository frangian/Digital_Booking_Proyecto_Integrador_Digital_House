package com.example.proyectoIntegradorE8.security.Authentication;

import com.example.proyectoIntegradorE8.entity.Role;
import com.example.proyectoIntegradorE8.entity.Usuario;
import com.example.proyectoIntegradorE8.security.JWT.JwtService;
import lombok.extern.log4j.Log4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.example.proyectoIntegradorE8.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Log4j
public class AuthenticationService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        log.info("registrando...");
        var user = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .ciudad(request.getCiudad())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        usuarioRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("autenticando...");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow();
        log.info("se encontro el usuario en la base");
        var jwtToken = jwtService.generateToken(user);
        log.info(("se genero el token"));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
