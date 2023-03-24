package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Ciudad;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.CiudadService;
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
@RequestMapping("/ciudad")
@Tag(name = "Ciudad", description = "API metodos CRUD de las ciudades")
public class CiudadController {
    private static final Logger logger = Logger.getLogger(CiudadController.class);
    private CiudadService ciudadService;
    @Autowired
    public CiudadController (CiudadService ciudadService){
        this.ciudadService = ciudadService;
    }

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
            logger.info("Se inicia el proceso para guardar una ciudad en la BBDD");
            Ciudad ciudadGuardada = ciudadService.guardarCiudad(ciudad);
            logger.info("La ciudad fue guardada "+ciudad.getNombre()+"   en la BBDD exitosamente");
            return ResponseEntity.ok(ciudadGuardada);
        } catch (SQLException bre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
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
            Ciudad ciudadBuscada = ciudadService.buscarCiudad(id);
            return ResponseEntity.ok(ciudadBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
            ciudadService.buscarCiudad(ciudad.getId());
            ciudadService.guardarCiudad(ciudad);
            return ResponseEntity.ok(ciudad);
        } catch (ResourceNotFoundException rnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (SQLException sqle){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sqle.getMessage());
        }
    }
    @GetMapping
    @Operation(summary = "Listar todas las ciudades", description = "Este endpoint permite ver todas las ciudades registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Ciudad.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> listarTodas(){
        try {
            List<Ciudad> ciudadesGuardadas = ciudadService.listarTodas();
            logger.info("Mostrando todas las ciudades registradas en la BBDD");
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
            ciudadService.buscarCiudad(id);
            ciudadService.eliminarCiudad(id);
            return ResponseEntity.ok("Se elimino la ciudad con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
