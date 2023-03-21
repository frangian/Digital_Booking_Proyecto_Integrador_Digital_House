package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Usuario;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/usuario")
@Log4j
@Tag(name = "Usuario", description = "API metodos CRUD de los usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    @Operation(
            summary = "Agregar usuario por ID",
            description = "Este endpoint permite agregar un usuario por ID en a la BBDD"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario se creo correctamente"),
            @ApiResponse(responseCode = "400", description = "Lamentablemente no ha podido registrarse. Inténtelo más tarde")})
    public ResponseEntity<?> guardarUsuario (@RequestBody Usuario usuario) {
            try {
                log.info("guardando usuario...");
                Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);
                log.info("El usuario fue guardada en la BBDD exitosamente");
                return ResponseEntity.ok(usuarioGuardado);
            } catch (SQLException bre){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
            }
        }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar un usuario por ID",
            description = "Este endpoint permite buscar un usuario por ID en a la BBDD"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "El usuario no existe en la BBDD"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})
    public ResponseEntity<?> buscarUsuario (@PathVariable Long id) {
        try {
            Usuario usuarioBuscado = usuarioService.buscarUsuario(id);
            return ResponseEntity.ok(usuarioBuscado);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios",
               description = "Este endpoint permite ver todas los usuarios registrados en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})
    public ResponseEntity<?> listarTodos(){
        try {
            List<Usuario> usuarioGuardados = usuarioService.listarTodos();
            log.info("Mostrando todos los usuarios registradas en la BBDD");
            return ResponseEntity.ok(usuarioGuardados);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Este endpoint permite eliminar un usuario de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "El usuario no existe en la BBDD"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})
    public ResponseEntity<?> eliminarUsuario (@PathVariable Long id) {
        try {
            usuarioService.buscarUsuario(id);
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Se elimino el usuario con ID: " + id + " de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    @Operation(
            summary = "Buscar un usuario por ID",
            description = "Este endpoint permite buscar un usuario por ID en a la BBDD"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "El usuario no existe en la BBDD"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})
    public ResponseEntity<?> buscarUsuarioEmail (@PathVariable String email) {
        try {
            Usuario usuarioBuscado = usuarioService.buscarUsuarioEmail(email);
            return ResponseEntity.ok(usuarioBuscado);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}



