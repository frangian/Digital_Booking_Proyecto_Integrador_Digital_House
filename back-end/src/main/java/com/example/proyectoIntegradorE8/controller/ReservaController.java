package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Reserva;
import com.example.proyectoIntegradorE8.exception.GlobalException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ProductoService;
import com.example.proyectoIntegradorE8.service.ReservaService;
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
@RequestMapping("/reserva")
@Log4j
@Tag(name = "Reserva", description = "API metodos CRUD de las reservas")
public class ReservaController {
    private final ReservaService reservaService;
    private final ProductoService productoService;

    @PostMapping
    @Operation(
            summary = "Agregar una reserva",
            description = "Este endpoint permite agregar una reserva a a la BBDD"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"horaComienzo\": \"HH:mm:ss\", \"fechaInicial\": \"AAAA-MM-DD\", \"fechaFinal\": \"AAAA-MM-DD\", \"producto\": {\"id\": 0}, \"usuario\": {\"id\": 0}}"))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = @Content(schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "400", description = "Peticion incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<?> guardarReserva (@RequestBody Reserva reserva){
        try {
            log.info("guardarReserva: accediendo al servicio de reserva...");
            Reserva reservaGuardada = reservaService.guardarReserva(reserva);
            log.info("La reserva fue guardada en la BBDD exitosamente");
            return ResponseEntity.ok(reservaGuardada);
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        }
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar una reserva por ID",
            description = "Este endpoint permite buscar una reserva por ID en a la BBDD"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "404", description = "La reserva no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> buscarReserva (@PathVariable Long id) {
        try {
            log.info("buscarReserva: accediendo al servicio de reserva...");
            Reserva reservaBuscada = reservaService.buscarReserva(id);
            log.info("buscarReserva: retornando la reserva encontrada");
            return ResponseEntity.ok(reservaBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MethodArgumentTypeMismatchException matme){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(matme.getMessage());
        }
    }
    @PutMapping
    @Operation(summary = "Actualizar una reserva", description = "Este endpoint permite actualizar una reserva ya existente en la BBDD")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"id\": 0, \"horaComienzo\": \"HH:mm:ss\", \"fechaInicial\": \"AAAA-MM-DD\", \"fechaFinal\": \"AAAA-MM-DD\", \"producto\": {\"id\": 0}, \"usuario\": {\"id\": 0}}"))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "404", description = "La reserva no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> actualizarReserva(@RequestBody Reserva reserva){
        try {
            log.info("actualizarReserva: accediendo al servicio de reserva...");
            productoService.buscarProducto(reserva.getProducto().getId());
            reservaService.actualizarReserva(reserva);
            log.info("actualizarReserva: reserva con id: "+reserva.getId()+" actualizada en la BBDD exitosamente");
            return ResponseEntity.ok(reserva);
        } catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping
    @Operation(summary = "Listar todas las reservas", description = "Este endpoint permite ver todas las reservas registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> listarReserva(){
        try {
            log.info("listarReserva: accediendo al servicio de reserva...");
            List<Reserva> reservasGuardadas = reservaService.listarReserva();
            log.info("listarReserva: retornando la lista de reservas");
            return ResponseEntity.ok(reservasGuardadas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una ciudad", description = "Este endpoint permite eliminar una ciudad de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK", content = @Content(schema = @Schema(example = "Se elimino la reserva con ID: \"+id+\" de la BBDD exitosamente"))),
            @ApiResponse(responseCode = "404", description = "La reserva no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> eliminarReserva (@PathVariable Long id) {
        try {
            log.info("eliminarReserva: accediendo al servicio de reserva...");
            reservaService.buscarReserva(id);
            reservaService.eliminarReserva(id);
            log.info("eliminarReserva: reserva con id: "+id+" eliminada de la BBDD exitosamente");
            return ResponseEntity.ok("Se elimino la reserva con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Listar todas las reservas asignadas al producto buscado", description = "Este endpoint permite ver todas las reservas asignadas al producto buscado registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> reservaPorProducto(@PathVariable Long productoId) {
        try {
            log.info("reservaPorProducto: accediendo al servicio de reserva");
            List<Reserva> reservas = reservaService.reservaPorProducto(productoId);
            log.info("reservaPorProducto: retornando las reservas encontradas");
            return ResponseEntity.ok(reservas);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
