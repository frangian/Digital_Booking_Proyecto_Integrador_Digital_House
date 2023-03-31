package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Ciudad;
import com.example.proyectoIntegradorE8.exception.GlobalException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.CiudadService;
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
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ciudad")
@Log4j
@Tag(name = "Ciudad", description = "API metodos CRUD de las ciudades")
public class CiudadController {
    private final CiudadService ciudadService;

    @PostMapping
    @Operation(summary = "Agregar una ciudad", description = "Este endpoint permite agregar una ciudad a a la BBDD")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"nombre\": \"String\", \"provincia\": \"String\",\"pais\": \"String\" }")
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = @Content(schema = @Schema(implementation = Ciudad.class))),
            @ApiResponse(responseCode = "400", description = "Peticion incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<?> guardarCiudad (@RequestBody Ciudad ciudad){
        System.out.println(ciudad.toString());
        try {
            log.info("guardarCiudad: accediendo al servicio de ciudad...");
            Ciudad ciudadGuardada = ciudadService.guardarCiudad(ciudad);
            log.info("La Ciudad fue guardada en la BBDD exitosamente");
            return ResponseEntity.ok(ciudadGuardada);
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar una ciudad por ID", description = "Este endpoint permite buscar una ciudad por ID de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Ciudad.class))),
            @ApiResponse(responseCode = "404", description = "La ciudad no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> buscarCiudad (@PathVariable Long id) {
        try {
            log.info("buscarCiudad: accediendo al servicio de ciudad...");
            Ciudad ciudadBuscada = ciudadService.buscarCiudad(id);
            log.info("buscarCiudad: retornando la ciudad encontrado");
            return ResponseEntity.ok(ciudadBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MethodArgumentTypeMismatchException matme){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(matme.getMessage());
        }
    }
    @PutMapping
    @Operation(summary = "Actualizar una ciudad", description = "Este endpoint permite actualizar una ciudad ya existente en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Ciudad.class))),
            @ApiResponse(responseCode = "404", description = "La ciudad no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> actualizarCiudad(@RequestBody Ciudad ciudad){
        try {
            log.info("actualizarCiudad: accediendo al servicio de ciudad...");
            ciudadService.buscarCiudad(ciudad.getId());
            ciudadService.guardarCiudad(ciudad);
            log.info("actualizarCiudad: ciudad con id: "+ciudad.getId()+" actualizado en la BBDD exitosamente");
            return ResponseEntity.ok(ciudad);
        } catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping
    @Operation(summary = "Listar todas las ciudades", description = "Este endpoint permite ver todas las ciudades registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Ciudad.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> listarCiudad(){
        try {
            log.info("listarCiudad: accediendo al servicio de ciudad...");
            List<Ciudad> ciudadesGuardadas = ciudadService.listarTodas();
            log.info("listarCiudad: retornando la lista de ciudades");
            return ResponseEntity.ok(ciudadesGuardadas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una ciudad", description = "Este endpoint permite eliminar una ciudad de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK", content = @Content(schema = @Schema(example = "Se elimino la ciudad con ID: \"+id+\" de la BBDD exitosamente"))),
            @ApiResponse(responseCode = "404", description = "La ciudad no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> eliminarCiudad (@PathVariable Long id) {
        try {
            log.info("eliminarCiudad: accediendo al servicio de ciudad...");
            ciudadService.buscarCiudad(id);
            ciudadService.eliminarCiudad(id);
            log.info("eliminarCiudad: ciudad con id: "+id+" eliminado de la BBDD exitosamente");
            return ResponseEntity.ok("Se elimino la ciudad con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
