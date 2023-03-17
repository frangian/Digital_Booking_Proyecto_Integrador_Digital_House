package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Reserva;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/reserva")
@Tag(name = "Reserva", description = "API metodos CRUD de las reservas")
public class ReservaController {
    private static final Logger logger = Logger.getLogger(ReservaController.class);
    private ReservaService reservaService;
    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    @Operation(
            summary = "Agregar una reserva",
            description = "Este endpoint permite agregar una reserva a a la BBDD"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "" +
                    "{" +
                    "\"horaComienzo\": \"HH:mm:ss\", " +
                    "\"fechaInicial\": \"AAAA-MM-DD\", " +
                    "\"fechaFinal\": \"AAAA-MM-DD\", " +
                    "\"producto\": {" +
                    "   \"id\": 0" +
                    "}, " +
                    "\"usuario\": {" +
                    "   \"id\": 0}" +
                    "}"
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "400", description = "Peticion incorrecta"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public ResponseEntity<?> guardarReserva (@RequestBody Reserva reserva){
        try {
            logger.info("guardando reserva...");
            Reserva reservaGuardada = reservaService.guardarReserva(reserva);
            logger.info("La reserva fue guardada "+reserva.getCodigoReserva()+" en la BBDD exitosamente");
            return ResponseEntity.ok(reservaGuardada);
        } catch (SQLException bre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }
    @Operation(
            summary = "Agregar una reserva",
            description = "Este endpoint permite agregar una reserva a a la BBDD"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "La reserva no existe en la BBDD"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarReserva (@PathVariable Long id) {
        try {
            Reserva reservaBuscada = reservaService.buscarReserva(id);
            return ResponseEntity.ok(reservaBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping
    @Operation(summary = "Actualizar una reserva", description = "Este endpoint permite actualizar una reserva ya existente en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "La reserva no existe en la BBDD"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})

    public ResponseEntity<?> actualizarReserva(@RequestBody Reserva reserva){
        try {
            reservaService.buscarReserva(reserva.getId());
            reservaService.guardarReserva(reserva);
            return ResponseEntity.ok(reserva);
        } catch (ResourceNotFoundException rnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (SQLException sqle){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sqle.getMessage());
        }
    }
    @GetMapping
    @Operation(summary = "Listar todas las reservas", description = "Este endpoint permite ver todas las reservas registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})
    public ResponseEntity<?> listarTodas(){
        try {
            List<Reserva> reservasGuardadas = reservaService.listarReserva();
            logger.info("Mostrando todas las reservas registradas en la BBDD");
            return ResponseEntity.ok(reservasGuardadas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una ciudad", description = "Este endpoint permite eliminar una ciudad de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "La reserva no existe en la BBDD"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})
    public ResponseEntity<?> eliminarReserva (@PathVariable Long id) {
        try {
            reservaService.buscarReserva(id);
            reservaService.eliminarReserva(id);
            return ResponseEntity.ok("Se elimino la reserva con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/producto/{producto}")
    @Operation(summary = "Listar todas las reservas asignadas al producto buscado", description = "Este endpoint permite ver todas las reservas asignadas al producto buscado registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta")})

    public ResponseEntity<?> reservaPorProducto(@PathVariable Long producto) {
        try {
            return ResponseEntity.ok(reservaService.reservaPorProducto(producto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
