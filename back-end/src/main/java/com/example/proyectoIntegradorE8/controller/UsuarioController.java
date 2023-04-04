package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Usuario;
import com.example.proyectoIntegradorE8.exception.GlobalException;
import com.example.proyectoIntegradorE8.security.auth.AuthenticationRequest;
import com.example.proyectoIntegradorE8.security.auth.AuthenticationResponse;
import com.example.proyectoIntegradorE8.security.auth.AuthenticationService;
import com.example.proyectoIntegradorE8.security.auth.RegisterRequest;
import com.example.proyectoIntegradorE8.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
@Log4j
@Tag(name = "Usuario", description = "API metodos CRUD de los usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    private final AuthenticationService authenticationService;

    @PostMapping("/auth/registro")
    @Operation(
            summary = "Agregar usuario por ID",
            description = "Este endpoint permite agregar un usuario por ID en a la BBDD"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            "    \"nombre\": \"string\"," +
                            "    \"apellido\": \"string\"," +
                            "    \"email\": \"string\"," +
                            "    \"password\": \"string\"" +
                            "}"
                    )            )    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El usuario se creo correctamente"),
            @ApiResponse(responseCode = "400", description = "Lamentablemente no ha podido registrarse. Inténtelo más tarde")})
    public ResponseEntity<?> guardarUsuario (@RequestBody RegisterRequest request) {
        try {
            log.info("guardarUsuario: accediendo al servicio de usuario... ");
            AuthenticationResponse usuarioGuardado = authenticationService.register(request);
            log.info("El usuario fue guardado en la BBDD exitosamente");
            return ResponseEntity.ok(usuarioGuardado);
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        }
    }

    @PostMapping("/auth/login")
    @Operation(
            summary = "Realizar un login a la aplicacon",
            description = "Este endpoint permite realizar un login"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            "    \"email\": \"string\"," +
                            "    \"password\": \"string\"" +
                            "}"
                    )            )    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El usuario se creo correctamente"),
            @ApiResponse(responseCode = "400", description = "Lamentablemente no ha podido registrarse. Inténtelo más tarde")})
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        try {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
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
    public ResponseEntity<?> buscarUsuario (@PathVariable Long id) throws Exception {
        try {
            log.info("buscarUsuario: accediendo al servicio de usuario...");
            Usuario usuarioBuscado = usuarioService.buscarUsuario(id);
            log.info("buscarUsuario: mostrando el usuario encontrado");
            return ResponseEntity.ok(usuarioBuscado);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MethodArgumentTypeMismatchException matme){
            return new GlobalException().handleMethodArgumentTypeMismatch(matme);
        }
    }
    @PutMapping
    @Operation(summary = "Actualizar un usuario", description = "Este endpoint permite actualizar un usuario ya existente en la BBDD")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            "    \"id\":0," +
                            "    \"ciudad\": \"string\"" +
                            "}"
                    )            )    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> actualizarUsuario(@RequestBody @NotNull Usuario usuario) throws Exception {
        try {
            log.info("actualizarUsuario: accediendo al servicio de usuario...");
            usuarioService.actualizarUsuario(usuario);
            log.info("actualizarUsuario: usuario con id: "+usuario.getId()+" actualizado en la BBDD exitosamente");
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        } catch (MethodArgumentTypeMismatchException matme){
            return new GlobalException().handleMethodArgumentTypeMismatch(matme);
        }
    }
    @GetMapping
    @Operation(summary = "Listar todos los usuarios",
            description = "Este endpoint permite ver todas los usuarios registrados en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})
    public ResponseEntity<?> listarUsuario(){
        try {
            log.info("listarUsuario: accediendo al servicio de usuario...");
            List<Usuario> usuarioGuardados = usuarioService.listarUsuario();
            log.info("listarUsuario: retornando la lista de usuarios");
            return ResponseEntity.ok(usuarioGuardados);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Este endpoint permite eliminar un usuario de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "404", description = "El usuario no existe en la BBDD"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})
    public ResponseEntity<?> eliminarUsuario (@PathVariable Long id) {
        try {
            log.info("eliminarUsuario: accediendo al servicio de usuario...");
            usuarioService.buscarUsuario(id);
            usuarioService.eliminarUsuario(id);
            log.info("eliminarUsuario: usuario con id: "+id+" eliminado de la BBDD exitosamente");
            return ResponseEntity.ok("Se elimino el usuario con ID: " + id + " de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/email/{email}")
    @Operation(
            summary = "Buscar un usuario por email",
            description = "Este endpoint permite buscar un usuario por email en a la BBDD"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "El usuario no existe en la BBDD"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})
    public ResponseEntity<?> buscarUsuarioEmail (@PathVariable String email) {
        try {
            log.info("buscarUsuarioEmail: accediendo al servicio de usuario...");
            Usuario usuarioBuscado = usuarioService.findByUsuarioPorEmail(email);
            log.info("buscarUsuarioEmail: retornando los usuarios encontrados");
            return ResponseEntity.ok(usuarioBuscado);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MethodArgumentTypeMismatchException matme){
            return new GlobalException().handleMethodArgumentTypeMismatch(matme);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}