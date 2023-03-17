package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/categoria")
@Tag(name = "Categoria", description = "API metodos CRUD de las categorias")
public class CategoriaController {
    private static final Logger logger = Logger.getLogger(CategoriaController.class);
    private CategoriaService categoriaService;
    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    @PostMapping
    @Operation(summary = "Agregar una categoria", description = "Este endpoint permite agregar una categoria a a la BBDD")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"titulo\": \"String\", \"descripcion\": \"String\" }")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "400", description = "Peticion incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<?> guardarCategoria (@RequestBody Categoria categoria){
        try {
            logger.info("Se inicia el proceso para guardar una categoria en la BBDD");
            Categoria categoriaGuardada = categoriaService.guardarCategoria(categoria);
            logger.info("La categoria fue guardada "+categoria.getTitulo()+"   en la BBDD exitosamente");
            return ResponseEntity.ok(categoriaGuardada);
        } catch (SQLException bre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar una categoria por ID", description = "Este endpoint permite buscar una categoria por ID de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "404", description = "La categoria no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> buscarCategoria(@PathVariable Long id) {
        try {
            Categoria categoriaBuscada = categoriaService.buscarCategoria(id);
            return ResponseEntity.ok(categoriaBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
           categoriaService.buscarCategoria(categoria.getId());
           categoriaService.guardarCategoria(categoria);
           return ResponseEntity.ok(categoria);
       } catch (ResourceNotFoundException rnfe){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
       } catch (SQLException sqle){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sqle.getMessage());
       }
   }
   @GetMapping
   @Operation(summary = "Listar todas las categorias", description = "Este endpoint permite ver todas las categorias registradas en la BBDD")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Categoria.class))),
           @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
   public ResponseEntity<?> listarCategorias() {
       try {
           List<Categoria> categoriaGuardadas = categoriaService.listarCategorias();
           logger.info("Mostrando todas las categorias registradas en la BBDD");
           return ResponseEntity.ok(categoriaGuardadas);
       } catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
   }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoria", description = "Este endpoint permite eliminar una categoria de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(example = "Se elimino la categoria con ID: \"+id+\" de la BBDD exitosamente"))),
            @ApiResponse(responseCode = "404", description = "La categoria no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.buscarCategoria(id);
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.ok("Se elimino la categoria con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}