package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Favorito;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.exception.GlobalException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.FavoritoService;
import com.example.proyectoIntegradorE8.service.ProductoService;
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
@RequestMapping("/favorito")
@Log4j
@Tag(name = "Favorito", description = "API metodos CRUD de los favoritos")
public class FavoritoController {
    private final FavoritoService favoritoService;
    private final ProductoService productoService;

    @PostMapping
    @Operation(
            summary = "Agregar favorito",
            description = "Este endpoint permite un producto a favorito"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value =
                            "{\"producto\": {\"id\": 0}, " +
                             "\"usuario\": {\"id\": 0}}"))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = @Content(schema = @Schema(implementation = Favorito.class))),
            @ApiResponse(responseCode = "400", description = "Petición incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<?> guardarFavorito (@RequestBody Favorito favorito){
        try {
            log.info("guardarFavorito: accediendo al servicio de favoritos...");
            Favorito favoritoGuardado = favoritoService.guardarFavorito(favorito);
            log.info("el prodcuto fue guardado en favorito exitosamente");
            return ResponseEntity.ok(favoritoGuardado);
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar favorito por ID",
            description = "Este endpoint permite buscar favoritos por ID en a la BBDD"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Favorito.class))),
            @ApiResponse(responseCode = "404", description = "El producto guardado en favoritos no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> buscarFavorito (@PathVariable Long id) {
        try {
            log.info("buscarFavorito: accediendo al servicio de favoritos...");
            Favorito favoritoBuscado = favoritoService.buscarFavorito(id);
            log.info("buscarFavorito: retornando el producto encontrado");
            return ResponseEntity.ok(favoritoBuscado);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MethodArgumentTypeMismatchException matme){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(matme.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos los favoritos", description = "Este endpoint permite ver todos los productos favoritos registradas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Favorito.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> listarFavoritos(){
        try {
            log.info("listarFavoritos: accediendo al servicio de favorito...");
            List<Favorito> favoritoGuardado = favoritoService.listarFavoritos();
            log.info("listarFavoritos: retornando la lista de favoritos");
            return ResponseEntity.ok(favoritoGuardado);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    @Operation(summary = "Actualizar favoritos", description = "Este endpoint permite actualizar favoritos ya existente en la BBDD")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"producto\": {\"id\": 0}, \"usuario\": {\"id\": 0}}"))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Favorito.class))),
            @ApiResponse(responseCode = "404", description = "El producto guardado en favoritos no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Petición Incorrecta", content = @Content)})
    public ResponseEntity<?> actualizarFavoritos(@RequestBody Favorito favorito){
        try {
            log.info("actualizarFavorito: accediendo al servicio de favoritos...");
            productoService.buscarProducto(favorito.getProducto().getId());
            favoritoService.actualizarFavoritos(favorito);
            log.info("actualizarFavorito: favorito con id: " + favorito.getId() + " actualizada en la BBDD exitosamente");
            return ResponseEntity.ok(favorito);
        } catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar favorito", description = "Este endpoint permite eliminar favoritos de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK", content = @Content(schema = @Schema(example = "Se elimino favorito con ID: \"+id+\" de la BBDD exitosamente"))),
            @ApiResponse(responseCode = "404", description = "El producto favorito no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Petición Incorrecta", content = @Content)})
    public ResponseEntity<?> eliminarFavorito (@PathVariable Long id) {
        try {
            log.info("eliminarFavorito: accediendo al servicio de favoritos...");
            favoritoService.buscarFavorito(id);
            favoritoService.eliminarFavorito(id);
            log.info("eliminarFavorito: favorito con id: "+id+" eliminado de la BBDD exitosamente");
            return ResponseEntity.ok("Se elimino favorito con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar todos los favoritos asignadas al usuario buscado", description = "Este endpoint permite ver todos los usuarios asignadas al usuario buscado registrados en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Petición Incorrecta", content = @Content)})
    public ResponseEntity<?> favoritoPorUsuario(@PathVariable Long usuarioId) {
        try {
            log.info("favoritoPorUsuario: accediendo al servicio de favorito");
            List<Producto> productos = favoritoService.productoPorUsuario(usuarioId);
            log.info("favoritoPorUsuario: retornando los productos encontrados");
            return ResponseEntity.ok(productos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//trae el id del prodcuto

//    @GetMapping("/usuario/{usuarioId}")
//    @Operation(summary = "Listar todos los favoritos asignadas al usuario buscado", description = "Este endpoint permite ver todos los usuarios asignadas al usuario buscado registrados en la BBDD")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Favorito.class))),
//            @ApiResponse(responseCode = "400", description = "Petición Incorrecta", content = @Content)})
//    public ResponseEntity<?> favoritoPorUsuario(@PathVariable Long usuarioId) {
//        try {
//            log.info("favoritoPorUsuario: accediendo al servicio de favorito");
//            List<Favorito> favoritos = favoritoService.favoritoPorUsuario(usuarioId);
//            log.info("favoritoPorUsuario: retornando los favoritos encontrados");
//            return ResponseEntity.ok(favoritos);
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

}
