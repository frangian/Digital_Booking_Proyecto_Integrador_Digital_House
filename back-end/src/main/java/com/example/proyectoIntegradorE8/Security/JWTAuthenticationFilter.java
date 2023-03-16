package com.example.proyectoIntegradorE8.Security;
import com.example.proyectoIntegradorE8.Security.AuthCredentials;
import com.example.proyectoIntegradorE8.Security.TokenUtils;
import com.example.proyectoIntegradorE8.Security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

// Filto para el proceso de autenticación
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // Método de intento de autenticación
    // Se llama a este método cuando el filtro intercepta una request de login.
    // El método lee los campos de email y contraseña del body de la request y crea una instancia de UsernamePasswordAuthenticationToken con esas credenciales.
    // Luego, este token se pasa al authentication manager para autenticar al usuario.
    // Si la autenticación es exitosa, se llama al método successfulAuthentication. Este método recupera los detalles del usuario del objeto Authentication autenticado,
    // crea un token JWT mediante el método TokenUtils.createToken() y agrega el token a los header de la respuesta.
    // Finalmente, se llama a super.successfulAuthentication() para completar la cadena de filtros.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials = new AuthCredentials();
        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (IOException e) {
        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.crearToken(userDetails.getNombre(), userDetails.getUsername());

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}

// creamos un token
//modificar la respuesta para adjuntar el nuevo token
// mandamos como argumento el nombre del encabezado y el valor del encabezado
// para confimar cambios: