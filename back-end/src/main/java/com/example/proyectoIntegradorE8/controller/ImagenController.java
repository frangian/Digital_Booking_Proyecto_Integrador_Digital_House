package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.exception.GlobalException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ImagenService;
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
@RequestMapping("/imagen")
@Log4j
@Tag(name = "Imagen", description = "API metodos CRUD de las imagenes")
public class ImagenController {
    private final ImagenService imagenService;

    @PostMapping
    @Operation(
            summary = "Agregar una imagen",
            description = "Este endpoint permite agregar una imagen a a la BBDD"    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"titulo\": \"String\",\"url_imagen\": \"String\",\"producto_id\": 0}")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = @Content(schema = @Schema(implementation = Imagen.class))),
            @ApiResponse(responseCode = "400", description = "Peticion incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<?> guardarImagen (@RequestBody Imagen imagen) {
        try {
            log.info("guardarImagen: accediendo al servicio de producto...");
            Imagen imagenGuardada = imagenService.guardarImagen(imagen);
            log.info("La imagen fue guardada en la BBDD exitosamente");
            return ResponseEntity.ok(imagenGuardada);
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar una imagen por ID", description = "Este endpoint permite buscar una imagen por ID de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Imagen.class))),
            @ApiResponse(responseCode = "404", description = "La imagen no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> buscarImagen (@PathVariable Long id) {
        try {
            log.info("buscarImagen: accediendo al servicio de imagen...");
            Imagen imagenBuscada = imagenService.buscarImagen(id);
            log.info("buscarImagen: retornando la imagen encontrado");
            return ResponseEntity.ok(imagenBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MethodArgumentTypeMismatchException matme){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(matme.getMessage());
        }
    }
    @PutMapping
    @Operation(summary = "Actualizar una imagen", description = "Este endpoint permite actualizar una imagen ya existente en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Imagen.class))),
            @ApiResponse(responseCode = "404", description = "La imagen no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> actualizarImagen(@RequestBody Imagen imagen){
        try {
            log.info("actualizarImagen: accediendo al servicio de imagen...");
            imagenService.buscarImagen(imagen.getId());
            imagenService.guardarImagen(imagen);
            log.info("actualizarImagen: imagen con id: "+imagen.getId()+" actualizada en la BBDD exitosamente");
            return ResponseEntity.ok(imagen);
        } catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping
    @Operation(summary = "Listar todas las imagenes", description = "Este endpoint permite ver todas las imagenes registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Imagen.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> listarImagenes (){
        try {
            log.info("listarImagenes: accediendo al servicio de imagen...");
            List<Imagen> imagenesGuardadas = imagenService.listarImagenes();
            log.info("listarImagenes: retornando la lista de imagenes");
            return ResponseEntity.ok(imagenesGuardadas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una imagen", description = "Este endpoint permite eliminar una imagen de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK", content = @Content(schema = @Schema(example = "Se elimino la imagen con ID: \"+id+\" de la BBDD exitosamente"))),
            @ApiResponse(responseCode = "404", description = "La imagen no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> eliminarImagen (@PathVariable Long id) {
        try {
            log.info("eliminarImagen: accediendo al servicio de imagen...");
            imagenService.buscarImagen(id);
            imagenService.eliminarImagen(id);
            log.info("eliminarImagen: imagen con id: "+id+" eliminada de la BBDD exitosamente");
            return ResponseEntity.ok("Se elimino la imagen con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/producto/{producto}")
    @Operation(summary = "Buscar una imagen por producto ID", description = "Este endpoint permite buscar una imagen por producto ID de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Imagen.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<List<Imagen>> imagenesPorProducto(@PathVariable Long producto) {
        log.info("imagenesPorProducto: accediendo al servicio de imagen");
        List<Imagen> imagenes = imagenService.imagenesPorProducto(producto);
        log.info("imagenesPorProducto: retornando las imagenes encontradas");
        return ResponseEntity.ok(imagenes);
    }
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar una imagen por categoria ID", description = "Este endpoint permite buscar una imagen por categoria ID de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Imagen.class))),
            @ApiResponse(responseCode = "404", description = "La categoria no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<List<Imagen>> imagenesPorCategoria(@PathVariable Long categoria) {
        log.info("imagenesPorCategoria: accediendo al servicio de imagen");
        List<Imagen> imagenes = imagenService.imagenesPorCategoria(categoria);
        log.info("imagenesPorCategoria: retornando las imagenes encontradas");
        return ResponseEntity.ok(imagenes);
    }
}
