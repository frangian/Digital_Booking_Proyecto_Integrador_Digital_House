package com.example.proyectoIntegradorE8.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class TokenUtils {
    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";
    private final static Long ACCESS_TOKEN_VALIDITY = 2_592_000L;

    public static String crearToken(String nombre, String email, Collection<? extends GrantedAuthority> authorities){
        long expirationTime= ACCESS_TOKEN_VALIDITY * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> adicional = new HashMap<>();
        adicional.put("nombre", nombre);
//lista de roles
        List<String> authorityList = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .claim("authorities", authorityList)
                .addClaims(adicional)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
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

            List<String> authorityNames = Optional.ofNullable(claims.get("authorities", List.class)).orElse(Collections.emptyList());
            Collection<GrantedAuthority> authorities = new HashSet<>();
            for (String authorityName : authorityNames) {
                authorities.add(new SimpleGrantedAuthority(authorityName));
            }

            return new UsernamePasswordAuthenticationToken(email, null, authorities);
        } catch (JwtException e){
            return null;
        }
    }
}

