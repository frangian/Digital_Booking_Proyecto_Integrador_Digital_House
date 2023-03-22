package com.example.proyectoIntegradorE8.Security;

import com.example.proyectoIntegradorE8.entity.Usuario;
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

    //TP, creando un objeto UsernamePasswordAuthenticationToken y llamando al administrador de autenticación para autenticar las credenciales del usuario.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException { // recibe una solicitud de autenticación HTTP y devuelve un objeto de autenticación.
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
// en el caso de que se complete bien la autenticación aneterior, hay que sobrescribir un metodo nuevo:
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        Usuario userDetails = (Usuario) authResult.getPrincipal();
        //creamos un token a partir de userDetails
        String token = TokenUtils.crearToken(userDetails.getNombre(), userDetails.getUsername());
        //modificamos la respuesta para adjuntar el nuevo token
        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter()
//                .printf(token) //esta linea serviria en caso de querer enviar el token en el body de la respuesta
                .flush(); //para confirmar esos cambios

        super.successfulAuthentication(request, response, chain, authResult);
    }
}

// creamos un token
//modificar la respuesta para adjuntar el nuevo token
// mandamos como argumento el nombre del encabezado y el valor del encabezado
// para confimar cambios: