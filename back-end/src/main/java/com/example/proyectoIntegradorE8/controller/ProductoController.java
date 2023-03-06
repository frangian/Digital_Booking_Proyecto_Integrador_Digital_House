package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    //crear un producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto (@RequestBody Producto producto){
        return ResponseEntity.ok(productoService.crearProducto(producto));
    }

    // listar productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    // buscar por  producto por categoria

    @GetMapping("/producto/{producto}")
    public ResponseEntity<List<Categoria>> productoPorCategoria(@PathVariable Long producto) {
        return ResponseEntity.ok(productoService.findByCategoria_id);
    }






}
