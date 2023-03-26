package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ImagenService;
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
@RequestMapping("/imagen")
@Tag(name = "Imagen", description = "API metodos CRUD de las imagenes")
public class ImagenController {
    private static final Logger logger = Logger.getLogger(ImagenController.class);
    private ImagenService imagenService;
    @Autowired
    public ImagenController(ImagenService imagenService){
        this.imagenService = imagenService;
    }

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
            logger.info("Se inicia el proceso para guardar una imagen en la BBDD");
            Imagen imagenGuardada = imagenService.guardarImagen(imagen);
            logger.info("La imagen "+imagen.getTitulo()+" fue guardada en la BBDD exitosamente");
            return ResponseEntity.ok(imagenGuardada);
        } catch (SQLException bre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
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
            Imagen imagenBuscada = imagenService.buscarImagen(id);
            return ResponseEntity.ok(imagenBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
            imagenService.buscarImagen(imagen.getId());
            imagenService.guardarImagen(imagen);
            return ResponseEntity.ok(imagen);
        } catch (ResourceNotFoundException rnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (SQLException sqle){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sqle.getMessage());
        }
    }
    @GetMapping
    @Operation(summary = "Listar todas las imagenes", description = "Este endpoint permite ver todas las imagenes registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Imagen.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> listarImagenes (){
        try {
            List<Imagen> imagenesGuardadas = imagenService.listarImagenes();
            logger.info("Mostrando todas las imagenes registradas en la BBDD");
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
            imagenService.buscarImagen(id);
            imagenService.eliminarImagen(id);
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
        return ResponseEntity.ok(imagenService.imagenesPorProducto(producto));
    }
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar una imagen por categoria ID", description = "Este endpoint permite buscar una imagen por categoria ID de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Imagen.class))),
            @ApiResponse(responseCode = "404", description = "La categoria no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<List<Imagen>> imagenesPorCategoria(@PathVariable Long categoria) {
        return ResponseEntity.ok(imagenService.imagenesPorCategoria(categoria));
    }
}
