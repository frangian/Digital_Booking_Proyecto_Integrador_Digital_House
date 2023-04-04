package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.exception.GlobalException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producto")
@Log4j
@Tag(name = "Producto", description = "API metodos CRUD de los productos")
public class ProductoController {
    private final ProductoService productoService;

    @PostMapping
    @Operation(
            summary = "Agregar un producto",
            description = "Este endpoint permite agregar un producto con sus imagenes a a la BBDD"    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "" +
                            "{\"titulo\": \"String\", " +
                            "\"descripcion_producto\": \"String\", " +
                            "\"descripcion_ubicacion\": \"String\", " +
                            "\"url_ubicacion\": \"String\", " +
                            "\"normas\": \"String\", " +
                            "\"seguridad\": \"String\", " +
                            "\"cancelacion\": \"String\", " +
                            "\"puntuacion\": 0, " +
                            "\"categoria\": {\"id\": 0}, " +
                            "\"ciudad\": {\"id\": 0}, " +
                            "\"caracteristicas\": [ " +
                                "{\"id\": 0}]," +
                            "\"imagenes\": [ "+
                                "{\"titulo\": \"String\", \"url_imagen\": \"String\"}]" +
                            "}"                    )            )    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto) {
        try {
            log.info("guardarProducto: accediendo al servicio de producto...");
            Producto productoGuardado = productoService.guardarProducto(producto);
            log.info("El producto fue guardado en la BBDD exitosamente");
            return ResponseEntity.ok(productoGuardado);
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar un producto por ID", description = "Este endpoint permite buscar un producto por ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found: El producto no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    public ResponseEntity<?> buscarProducto(@PathVariable Long id) throws Exception {
        try {
            log.info("buscarProducto: accediendo al servicio de producto...");
            Producto productoBuscado = productoService.buscarProducto(id);
            log.info("buscarProducto: retornando el producto encontrado");
            return ResponseEntity.ok(productoBuscado);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MethodArgumentTypeMismatchException matme){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(matme.getMessage());
        }
    }
    @PutMapping
    @Operation(summary = "Actualizar un producto", description = "Este endpoint permite actualizar un producto ya existente en la BBDD")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "" +
                            "{\n" +
                            "    \"id\": 0, \"titulo\": \"String\",\"descripcion_producto\": \"String\",\"descripcion_ubicacion\": \"String\",\n" +
                            "    \"url_ubicacion\": \"String\", \"normas\": \"String\", \"seguridad\": \"String\", \"cancelacion\": \"String\",\n" +
                            "    \"puntuacion\": 0, \"categoria\": { \"id\": 0 }, \"ciudad\": { \"id\": 0 }, \"caracteristicas\": [ { \"id\": 0 } ],\n" +
                            "    \"imagenes\": [{\"id\": 0, \"titulo\": \"String\",\"url_imagen\": \"String\"}]}"
                    )            )    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> actualizarProducto(@RequestBody @NotNull Producto producto){
        try {
            log.info("actualizarProducto: accediendo al servicio de producto...");
            productoService.buscarProducto(producto.getId());
            productoService.guardarProducto(producto);
            log.info("actualizarProducto: producto con id: "+producto.getId()+" actualizado en la BBDD exitosamente");
            return ResponseEntity.ok(producto);
        } catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());
        } catch (ConstraintViolationException e) {
            return new GlobalException().handleConstraintViolationException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping
    @Operation(tags = "Producto", summary = "Listar todos los productos", description = "Este endpoint permite ver todos los productos registrados en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> listarProductos() {
        try {
            log.info("listarProductos: accediendo al servicio de producto...");
            List<Producto> productosGuardados = productoService.listarProductos();
            log.info("listarProductos: retornando la lista de productos");
            return ResponseEntity.ok(productosGuardados);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Este endpoint permite eliminar un producto de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "application/json", schema = @Schema(example = "Se elimino el producto con ID: \"+id+\" de la BBDD exitosamente"))),
            @ApiResponse(responseCode = "404", description = "El producto no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> eliminarProductos (@PathVariable Long id) {
        try {
            log.info("eliminarProductos: accediendo al servicio de producto...");
            productoService.buscarProducto(id);
            productoService.eliminarProducto(id);
            log.info("eliminarProductos: producto con id: "+id+" eliminado de la BBDD exitosamente");
            return ResponseEntity.ok("Se elimino el producto con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Buscar los productos dentro de la categoria ID", description = "Este endpoint permite buscar los productos dentro de la categoria ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> findByProductoPorCategoria(@PathVariable Long categoriaId) {
        try {
            log.info("findByProductoPorCategoria: accediendo al servicio de producto");
            List<Producto> productos = productoService.findByProductoPorCategoria(categoriaId);
            log.info("findByProductoPorCategoria: retornando los productos encontrados");
            return ResponseEntity.ok(productos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/ciudad/{ciudadId}")
    @Operation(summary = "Buscar los productos dentro de la ciudad ID", description = "Este endpoint permite buscar los productos dentro de la ciudad ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> findByProductoPorCiudad(@PathVariable Long ciudadId) {
        try {
            log.info("findByProductoPorCiudad: accediendo al servicio de producto");
            List<Producto> productos = productoService.findByProductoPorCiudad(ciudadId);
            log.info("findByProductoPorCiudad: retornando los productos encontrados");
            return ResponseEntity.ok(productos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/random")
    @Operation(summary = "Lista 8 productos de manera random", description = "Este endpoint permite listar 8 productos de la BBDD de manera random")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> getProductosRandom() {
        try {
            log.info("getProductosRandom: accediendo al servicio de producto");
            // Obtener todos los productos desde la base de datos
            List<Producto> productos = productoService.listarProductos();
            // Seleccionar aleatoriamente 8 productos
            Collections.shuffle(productos, new Random());
            List<Producto> productosRandom = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                productosRandom.add(productos.get(i));
            }
            // Devolver los 8 productos seleccionados
            log.info("getProductosRandom: retornando los productos encontrados");
            return ResponseEntity.ok(productosRandom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/disponibles/fecha")
    @Operation(summary = "Buscar los productos disponibles por fechas", description = "Este endpoint permite buscar los productos disponibles dentro de un rango de fechcas en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> findByProductoPorFechas (@RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) throws Exception {
        try {
            log.info("findByProductoPorFechas: accediendo al servicio de producto");
            List<Producto> productos = productoService.findByProductoPorFechas(fechaInicial,fechaFinal);
            log.info("findByProductoPorFechas: retornando los productos encontrados");
            return ResponseEntity.ok(productos);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/disponibles/fechaciudad")
    @Operation(summary = "Buscar los productos disponibles por fechas en ciudad ID", description = "Este endpoint permite buscar los productos disponibles dentro de un rango de fechcas en ciudad ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> findByProductoPorCiudadIdAndFechas (@RequestParam Long ciudadId, @RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) {
        try {
            log.info("findByProductoPorCiudadIdAndFechas: accediendo al servicio de producto");
            List<Producto> productos = productoService.findByProductoPorCiudadIdAndFechas(ciudadId,fechaInicial,fechaFinal);
            log.info("findByProductoPorCiudadIdAndFechas: retornando los productos encontrados");
            return ResponseEntity.ok(productos);
        } catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/disponibles/fechacategoria")
    @Operation(summary = "Buscar los productos disponibles por fechas en ciudad ID", description = "Este endpoint permite buscar los productos disponibles dentro de un rango de fechcas en ciudad ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> findByCategoriaIdAndProductoFechas (@RequestParam Long categoriaId, @RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) {
        try {
            log.info("findByCategoriaIdAndProductoFechas: accediendo al servicio de producto");
            List<Producto> productos = productoService.findByCategoriaIdAndProductoFechas(categoriaId,fechaInicial,fechaFinal);
            log.info("findByCategoriaIdAndProductoFechas: retornando los productos encontrados");
            return ResponseEntity.ok(productos);
        } catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/disponibles/categoriaciudad")
    @Operation(summary = "Buscar los productos disponibles por Id de ciudad y categoria", description = "Este endpoint permite buscar los productos disponibles con ID especifico de ciduad y categoria en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> findByCategoriaIdAndCiudadId (@RequestParam Long ciudadId, @RequestParam Long categoriaId) {
        try {
            log.info("findByCategoriaIdAndCiudadId: accediendo al servicio de producto");
            List<Producto> productos = productoService.findByCategoriaIdAndCiudadId(ciudadId,categoriaId);
            log.info("findByCategoriaIdAndCiudadId: retornando los productos encontrados");
            return ResponseEntity.ok(productos);
        } catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/disponibles/categoriaciudadfecha")
    @Operation(summary = "Buscar los productos disponibles por Id de ciudad y categoria y fechas", description = "Este endpoint permite buscar los productos disponibles dentro de un rango de fechcas en ciudad ID y categoria ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> findByCiudadIdAndCategoriaIdAndFechas (@RequestParam Long ciudadId, @RequestParam Long categoriaId, @RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) {
        try {
            log.info("findByCategoriaIdAndCiudadId: accediendo al servicio de producto");
            List<Producto> productos = productoService.findByCiudadIdAndCategoriaIdAndFechas(ciudadId,categoriaId, fechaInicial, fechaFinal);
            log.info("findByCategoriaIdAndCiudadId: retornando los productos encontrados");
            return ResponseEntity.ok(productos);
        } catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/titulo/{productoTitulo}")
    @Operation(summary = "Buscar los productos disponibles por titulo", description = "Este endpoint permite buscar los productos disponibles por el titulo en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> findByProductoXTitulo (@PathVariable String productoTitulo) {
        try {
            log.info("findByProductoXTitulo: accediendo al servicio de producto");
            List<Producto> productos = productoService.findByProductoXTitulo(productoTitulo);
            log.info("findByProductoXTitulo: retornando los productos encontrados");
            return ResponseEntity.ok(productos);
        } catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
