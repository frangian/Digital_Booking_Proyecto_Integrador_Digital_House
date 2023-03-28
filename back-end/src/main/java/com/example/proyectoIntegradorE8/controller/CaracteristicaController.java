package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Caracteristica;
import com.example.proyectoIntegradorE8.exception.GlobalException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.CaractersiticaService;
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
@RequestMapping("/caracteristica")
@Log4j
@Tag(name = "Caracteristica", description = "API metodos CRUD de las caracteristicas")
public class CaracteristicaController {
    private final CaractersiticaService caractersiticaService;

    @PostMapping
    @Operation(summary = "Agregar una caracteristica", description = "Este endpoint permite agregar una caracteristica a a la BBDD")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"titulo\": \"String\" }")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = @Content(schema = @Schema(implementation = Caracteristica.class))),
            @ApiResponse(responseCode = "400", description = "Peticion incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<?> guardarCaracteristica (@RequestBody Caracteristica caracteristica){
        try {
            log.info("guardarCaracteristica: accediendo al servicio de caracteristica...");
            Caracteristica caracteristicaGuardada = caractersiticaService.guardarCaracteristica(caracteristica);
            log.info("La caracteristica fue guardada en la BBDD exitosamente");
            return ResponseEntity.ok(caracteristicaGuardada);
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar una caracteristica por ID", description = "Este endpoint permite buscar una caracteristica por ID de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Caracteristica.class))),
            @ApiResponse(responseCode = "404", description = "La caracteristica no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> buscarCaracteristica (@PathVariable Long id) throws Exception {
        try {
            log.info("buscarCaracteristica: accediendo al servicio de caracteristica...");
            Caracteristica caracteristicaBuscada = caractersiticaService.buscarCaracteristica(id);
            log.info("buscarCaracteristica: retornando la caracteristica encontrada");
            return ResponseEntity.ok(caracteristicaBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MethodArgumentTypeMismatchException matme){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(matme.getMessage());
        }
    }
    @PutMapping
    @Operation(summary = "Actualizar una caracteristica", description = "Este endpoint permite actualizar una caracteristica ya existente en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Caracteristica.class))),
            @ApiResponse(responseCode = "404", description = "La caracteristica no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> actualizarCaracteristica(@RequestBody Caracteristica caracteristica){
        try {
            log.info("actualizarCaracteristica: accediendo al servicio de caracteristica...");
            caractersiticaService.buscarCaracteristica(caracteristica.getId());
            caractersiticaService.actualizarCaracteristica(caracteristica);
            log.info("actualizarCaracteristica: caracteristica con id: "+caracteristica.getId()+" actualizado en la BBDD exitosamente");
            return ResponseEntity.ok(caracteristica);
        } catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping
    @Operation(summary = "Listar todas las caracteristicas", description = "Este endpoint permite ver todas las caracteristicas registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Caracteristica.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> listarCaracteristicas() {
        try {
            log.info("listarCaracteristicas: accediendo al servicio de caracteristica...");
            List<Caracteristica> caractersiticasGuardadas = caractersiticaService.listarCaracteristicas();
            log.info("listarCaracteristicas: retornando la lista de caracteristicas");
            return ResponseEntity.ok(caractersiticasGuardadas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una caracteristica", description = "Este endpoint permite eliminar una caracteristica de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK", content = @Content(schema = @Schema(example = "Se elimino la caracteristica con ID: \"+id+\" de la BBDD exitosamente"))),
            @ApiResponse(responseCode = "404", description = "La caracteristica no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> eliminarCaracteristica (@PathVariable Long id) {
        try {
            log.info("eliminarCaracteristica: accediendo al servicio de caracteristica...");
            caractersiticaService.buscarCaracteristica(id);
            caractersiticaService.eliminarCaracteristica(id);
            log.info("eliminarCaracteristica: caracteristica con id: "+id+" eliminada de la BBDD exitosamente");
            return ResponseEntity.ok("Se elimino la caracteristica con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Buscar las caracteristicas por producto ID", description = "Este endpoint permite buscar las caracteristicas por producto ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Caracteristica.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<List<Caracteristica>> caracteristicaXProducto(@PathVariable Long productoId) throws Exception {
        log.info("caracteristicaXProducto: accediendo al servicio de caracteristica");
        List<Caracteristica> caracteristicas = caractersiticaService.caracteristicasXProducto(productoId);
        log.info("caracteristicaXProducto: retornando las caracteristicas encontradas");
        return ResponseEntity.ok(caracteristicas);
    }

}
