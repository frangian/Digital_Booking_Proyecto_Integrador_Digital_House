package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.exception.GlobalException;
import com.example.proyectoIntegradorE8.service.CategoriaService;
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
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categoria")
@Log4j
@Tag(name = "Categoria", description = "API metodos CRUD de las categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    @PostMapping
    @Operation(summary = "Agregar una categoria", description = "Este endpoint permite agregar una categoria a a la BBDD")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"titulo\": \"String\", \"descripcion\": \"String\" }")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = @Content(schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "400", description = "Peticion incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<?> guardarCategoria (@RequestBody Categoria categoria){
        try {
            log.info("guardarCategoria: accediendo al servicio de categoria...");
            Categoria categoriaGuardada = categoriaService.guardarCategoria(categoria);
            log.info("La categoria fue guardada en la BBDD exitosamente");
            return ResponseEntity.ok(categoriaGuardada);
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar una categoria por ID", description = "Este endpoint permite buscar una categoria por ID de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "404", description = "La categoria no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> buscarCategoria(@PathVariable Long id) throws Exception{
        try {
            log.info("buscarCategoria: accediendo al servicio de categoria...");
            Categoria categoriaBuscada = categoriaService.buscarCategoria(id);
            log.info("buscarCategoria: retornando la categoria encontrada...");
            return ResponseEntity.ok(categoriaBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MethodArgumentTypeMismatchException matme){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(matme.getMessage());
        }
    }
   @PutMapping
   @Operation(summary = "Actualizar una categoria", description = "Este endpoint permite actualizar una categoria ya existente en la BBDD")
   @io.swagger.v3.oas.annotations.parameters.RequestBody(
           content = @Content(
                   mediaType = "application/json",
                   examples = @ExampleObject(value = "{ \"id\": \"String\", \"titulo\": \"String\", \"descripcion\": \"String\" }")))
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Categoria.class))),
           @ApiResponse(responseCode = "404", description = "La categoria no existe en la BBDD", content = @Content),
           @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
   public ResponseEntity<?> actualizarCategoria(@RequestBody Categoria categoria){
       try {
           log.info("actualizarCategoria: accediendo al servicio de categoria...");
           categoriaService.buscarCategoria(categoria.getId());
           categoriaService.actualizarCategoria(categoria);
           log.info("actualizarProducto: categoria con id: "+categoria.getId()+" actualizada en la BBDD exitosamente");
           return ResponseEntity.ok(categoria);
       } catch (EntityNotFoundException enfe){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());
       } catch (ConstraintViolationException e) {
           return new GlobalException().handleConstraintViolationException(e);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }
   @GetMapping
   @Operation(summary = "Listar todas las categorias", description = "Este endpoint permite ver todas las categorias registradas en la BBDD")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Categoria.class))),
           @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
   public ResponseEntity<?> listarCategorias() {
       try {
           log.info("listarCategorias: accediendo al servicio de categoria...");
           List<Categoria> categoriaGuardadas = categoriaService.listarCategorias();
           log.info("listarCategorias: retornando la lista de categorias");
           return ResponseEntity.ok(categoriaGuardadas);
       } catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
   }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoria", description = "Este endpoint permite eliminar una categoria de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK", content = @Content(schema = @Schema(example = "Se elimino la categoria con ID: \"+id+\" de la BBDD exitosamente"))),
            @ApiResponse(responseCode = "404", description = "La categoria no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        try {
            log.info("eliminarCategoria: accediendo al servicio de categoria...");
            categoriaService.buscarCategoria(id);
            categoriaService.eliminarCategoria(id);
            log.info("eliminarCategoria: categoria con id: "+id+" eliminada de la BBDD exitosamente");
            return ResponseEntity.ok("Se elimino la categoria con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}