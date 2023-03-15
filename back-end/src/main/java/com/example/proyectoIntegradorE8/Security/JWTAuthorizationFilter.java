package com.example.proyectoIntegradorE8.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // utiliza el token par solicitar, no para autorizar
        String bearerToken = request.getHeader("Authorization");

        // hay que verificar el bearerToken sea diferente a null //que el token inicie con el prefijo bearer
        if (bearerToken != null && bearerToken.startsWith("Bearer")){

            //extraer el token y remplazar el prefijo Bearer por nada
        String token = bearerToken.replace("Bearer", "");

        //creamos una instancia pero lo extraemos usando TokenUtils y el metodo, le enviamos el token y usamos security con Context para establecer la autenticación
        UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(usernamePAT);

        //llamamos a filtrer
        filterChain.doFilter(request,response);

        }





    }
}
