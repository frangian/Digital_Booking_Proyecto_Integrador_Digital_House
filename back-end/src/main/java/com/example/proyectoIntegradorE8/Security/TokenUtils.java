package com.example.proyectoIntegradorE8.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";
    private final static Long ACCESS_TOKEN_VALIDITY = 2_592_000L;

//En resumen, el método "crearToken" de la clase "TokenUtils" genera un token JWT con la información del nombre y el correo electrónico,
// lo firma y lo devuelve como una cadena.
    public static String crearToken(String nombre, String email){
        //convertir el tiempo a milisegundos
        long expirationTime= ACCESS_TOKEN_VALIDITY * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        //esto va a viajar con el token, son datos adicionales como cadena de objetos.
        Map<String, Object> adicional = new HashMap<>();
        adicional.put("nombre", nombre); //el nombre del usuario

        //se crea el token que será enviado al cliente
        return Jwts.builder()
                //para quien va dirigido
                .setSubject(email)//establece el "sujeto" del token a la dirección de correo electrónico proporcionada que generalmente se usa para identificar al destinatario del token.
                .setExpiration(expirationDate) //establece la fecha y hora de expiración del token. Después de esta fecha, el token ya no será válido y no se podrá usar.
                .addClaims(adicional) // Se agrega una reclamación llamada "nombre" con el valor proporcionado en la variable "nombre". Las reclamaciones son campos personalizados que se pueden
                // agregar a los tokens para proporcionar información adicional.
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes())) //firma el token utilizando el algoritmo HMAC-SHA y la clave secreta proporcionada en la variable "ACCESS_TOKEN_SECRET".
                // La firma asegura que el token no haya sido modificado durante su tránsito.
                .compact(); //convierte el token JWT en una cadena compacta que se puede enviar a través de una solicitud HTTP.
    }

 //     es necesario para que sprint reconozca y pueda pasar el proceso de autorización para un usuario que quiere accedes a un endpoint con un token, el token esta en formato plano.
    public static UsernamePasswordAuthenticationToken getAuthentication (String token){

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

// para extraer el correo que esta identificando a un usuario, se usa esto:
            String email = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
// si el usuario manda un token indorrecto/invalido/expirado,existe esta excepción y retorna nulo, y quiere decir que no se ha podido crear
        } catch (JwtException e){
            return null;
        }
        }

}
// El método toma una cadena de token JWT como argumento y devuelve un objeto de tipo "UsernamePasswordAuthenticationToken".
// Si el token es válido, el método extrae el "sujeto" del token, que debe ser una dirección de correo electrónico,
// y lo usa como identificador de usuario para la autenticación. A continuación, crea y devuelve un objeto "UsernamePasswordAuthenticationToken"
// que representa la autenticación del usuario. Si el token no es válido o hay un error al analizarlo, el método devuelve null.
