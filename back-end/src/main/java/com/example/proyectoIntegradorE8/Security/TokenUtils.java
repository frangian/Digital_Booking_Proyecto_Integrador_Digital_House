package com.example.proyectoIntegradorE8.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "uqwbodqbw242272odwqd";
    private final static Long ACCESS_TOKEN_VALIDITY = 2_592_000L;


    public static String crearToken(String nombre, String email){
        //converitr el tiempo a milisegundos
        long expirationTime= ACCESS_TOKEN_VALIDITY * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        //esto va a viajar con el token
        Map<String, Object> adicional = new HashMap<>();
        adicional.put("nombre", nombre);

        //se crea el token
        return Jwts.builder()
                //para quien va dirigido
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(adicional)
                .signWith(SignatureAlgorithm.HS256, ACCESS_TOKEN_SECRET.getBytes())
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication (String token){

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

        } catch (JwtException e){
            return null;
        }
        }

}
