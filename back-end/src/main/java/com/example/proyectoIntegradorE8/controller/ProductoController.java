package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ProductoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> guardarProducto (@RequestBody Producto producto) throws Exception {
        productoService.guardarProducto(producto);
        return ResponseEntity.ok("Se registró el producto: "+producto.getTitulo());
    }

    // listar productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Producto> resultado = productoService.buscarProductoXid(id);
        if (resultado.isPresent()) {
            logger.info("Se encontro el producto con id: "+id+" en la BBDD exitosamente");
            return ResponseEntity.ok(resultado.get());
        } else {
            throw new ResourceNotFoundException("El producto con id: "+id+" no existe en la BD");
        }
    }

    // buscar por  producto por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> productoPorCategoria(@PathVariable Long categoria) {
        return ResponseEntity.ok(productoService.productoPorCategoria(categoria));
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Producto>> productoPorCiudad(@PathVariable Long ciudad) {
        return ResponseEntity.ok(productoService.productoPorCiudad(ciudad));
    }

    @GetMapping("/random")
    public List<Producto> getProductosRandom() {
        // Obtener todos los productos desde la base de datos
        List<Producto> productos = productoService.listarProductos();

        // Seleccionar aleatoriamente 8 productos
        Collections.shuffle(productos, new Random());
        List<Producto> productosRandom = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            productosRandom.add(productos.get(i));
        }
        // Devolver los 8 productos seleccionados
        return productosRandom;
    }


}
