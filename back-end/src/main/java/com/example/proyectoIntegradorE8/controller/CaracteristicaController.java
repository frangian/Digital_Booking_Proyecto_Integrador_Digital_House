package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Caracteristica;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.CaractersiticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/caracteristica")
@Log4j
@Tag(name = "Caracteristica", description = "API metodos CRUD de las caracteristicas")
public class CaracteristicaController {
    private CaractersiticaService caractersiticaService;

    @Autowired
    public CaracteristicaController (CaractersiticaService caractersiticaService){
        this.caractersiticaService = caractersiticaService;
    }

    @PostMapping
    @Operation(summary = "Agregar una caracteristica", description = "Este endpoint permite agregar una caracteristica a a la BBDD")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"titulo\": \"String\" }")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Caracteristica.class))),
            @ApiResponse(responseCode = "400", description = "Peticion incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<?> guardarCaracteristica (@RequestBody Caracteristica caracteristica){
        try {
            log.info("Se inicia el proceso para guardar una caracteristica en la BBDD");
            Caracteristica caracteristicaGuardada = caractersiticaService.guardarCaracteristica(caracteristica);
            log.info("La caracteristica fue guardada "+caracteristica.getTitulo()+" en la BBDD exitosamente");
            return ResponseEntity.ok(caracteristicaGuardada);
        } catch (SQLException bre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar una caracteristica por ID", description = "Este endpoint permite buscar una caracteristica por ID de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Caracteristica.class))),
            @ApiResponse(responseCode = "404", description = "La caracteristica no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> buscarCaracteristica (@PathVariable Long id) {
        try {
            Caracteristica caracteristicaBuscada = caractersiticaService.buscarCaracteristica(id);
            return ResponseEntity.ok(caracteristicaBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
            caractersiticaService.buscarCaracteristica(caracteristica.getId());
            caractersiticaService.guardarCaracteristica(caracteristica);
            return ResponseEntity.ok(caracteristica);
        } catch (ResourceNotFoundException rnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (SQLException sqle){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sqle.getMessage());
        }
    }
    @GetMapping
    @Operation(summary = "Listar todas las caracteristicas", description = "Este endpoint permite ver todas las caracteristicas registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Caracteristica.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> listarCaracteristicas() {
        try {
            List<Caracteristica> caractersiticasGuardadas = caractersiticaService.listarCaracteristicas();
            log.info("Mostrando todas las caractersiticas registradas en la BBDD");
            return ResponseEntity.ok(caractersiticasGuardadas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una caracteristica", description = "Este endpoint permite eliminar una caracteristica de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(example = "Se elimino la caracteristica con ID: \"+id+\" de la BBDD exitosamente"))),
            @ApiResponse(responseCode = "404", description = "La caracteristica no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> eliminarCaracteristica (@PathVariable Long id) {
        try {
            caractersiticaService.buscarCaracteristica(id);
            caractersiticaService.eliminarCaracteristica(id);
            return ResponseEntity.ok("Se elimino la caracteristica con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/producto/{producto}")
    @Operation(summary = "Buscar las caracteristicas por producto ID", description = "Este endpoint permite buscar las caracteristicas por producto ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Caracteristica.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<List<Caracteristica>> caracteristicaXProducto(@PathVariable Long producto){
        return ResponseEntity.ok(caractersiticaService.caracteristicasXProducto(producto));
    }

}
