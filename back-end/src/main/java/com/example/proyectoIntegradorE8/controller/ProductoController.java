package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ProductoService;
import org.apache.log4j.Logger;
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
public class ProductoController {
    private static final Logger logger = Logger.getLogger(ProductoController.class);
    private ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
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
    public ResponseEntity<?> buscarProducto(@PathVariable Long id) {
        try {
            Producto productoBuscado = productoService.buscarProducto(id);
            return ResponseEntity.ok(productoBuscado);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> actualizarProducto(@RequestBody Producto producto){
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
    public ResponseEntity<?> productoPorCategoria(@PathVariable Long categoria) {
        try {
            return ResponseEntity.ok(productoService.productoPorCategoria(categoria));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<?> productoPorCiudad(@PathVariable Long ciudad) {
        try {
            return ResponseEntity.ok(productoService.productoPorCiudad(ciudad));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/random")
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
    public ResponseEntity<?> getProductosDisponiblesFecha (@RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) throws Exception {
        try {
            return ResponseEntity.ok(productoService.productosDisponiblesFecha(fechaInicial,fechaFinal));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
