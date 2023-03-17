package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/producto")
@Tag(name = "Producto", description = "API metodos CRUD de los productos")
public class ProductoController {
    private static final Logger logger = Logger.getLogger(ProductoController.class);
    private ProductoService productoService;
    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

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
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto) {
        try {
            logger.info("Se inicia el proceso para guardar un producto en la BBDD");
            Producto productoGuardado = productoService.guardarProducto(producto);
            logger.info("El producto fue guardado "+producto.getTitulo()+"   en la BBDD exitosamente");
            return ResponseEntity.ok(productoGuardado);
        } catch (SQLException bre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar un producto por ID", description = "Este endpoint permite buscar un producto por ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> buscarProducto(@PathVariable Long id) {
        try {
            Producto productoBuscado = productoService.buscarProducto(id);
            return ResponseEntity.ok(productoBuscado);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
            productoService.buscarProducto(producto.getId());
            productoService.guardarProducto(producto);
            return ResponseEntity.ok(producto);
        } catch (ResourceNotFoundException rnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (SQLException sqle){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sqle.getMessage());
        }
    }
    @GetMapping
    @Operation(tags = "Producto", summary = "Listar todos los productos", description = "Este endpoint permite ver todos los productos registrados en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> listarProductos() {
        try {
            List<Producto> productosGuardados = productoService.listarProductos();
            logger.info("Mostrando todas los productos registrados en la BBDD");
            return ResponseEntity.ok(productosGuardados);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Este endpoint permite eliminar un producto de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = "Se elimino el producto con ID: \"+id+\" de la BBDD exitosamente"))),
            @ApiResponse(responseCode = "404", description = "El producto no existe en la BBDD", content = @Content),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> eliminarProductos (@PathVariable Long id) {
        try {
            productoService.buscarProducto(id);
            productoService.eliminarProducto(id);
            return ResponseEntity.ok("Se elimino el producto con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar los productos dentro de la categoria ID", description = "Este endpoint permite buscar los productos dentro de la categoria ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> productoPorCategoria(@PathVariable Long categoria) {
        try {
            return ResponseEntity.ok(productoService.productoPorCategoria(categoria));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/ciudad/{ciudad}")
    @Operation(summary = "Buscar los productos dentro de la ciudad ID", description = "Este endpoint permite buscar los productos dentro de la ciudad ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> productoPorCiudad(@PathVariable Long ciudad) {
        try {
            return ResponseEntity.ok(productoService.productoPorCiudad(ciudad));
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
            // Obtener todos los productos desde la base de datos
            List<Producto> productos = productoService.listarProductos();
            // Seleccionar aleatoriamente 8 productos
            Collections.shuffle(productos, new Random());
            List<Producto> productosRandom = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                productosRandom.add(productos.get(i));
            }
            // Devolver los 8 productos seleccionados
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
    public ResponseEntity<?> getProductosDisponiblesFecha (@RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) throws Exception {
        try {
            return ResponseEntity.ok(productoService.findByProductoFechas(fechaInicial,fechaFinal));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/disponibles/fechaciudad")
    @Operation(summary = "Buscar los productos disponibles por fechas en ciudad ID", description = "Este endpoint permite buscar los productos disponibles dentro de un rango de fechcas en ciudad ID en la BBDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Peticion Incorrecta", content = @Content)})
    public ResponseEntity<?> getProductosDisponiblesFechaCiudad (@RequestParam Long ciudadId, @RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) {
        try {
            logger.info("Controller: buscando productos por ciudad id y fechas");
            return ResponseEntity.ok(productoService.findByCiudadIdAndProductoFechas(ciudadId,fechaInicial,fechaFinal));
        } catch (Exception e){
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
