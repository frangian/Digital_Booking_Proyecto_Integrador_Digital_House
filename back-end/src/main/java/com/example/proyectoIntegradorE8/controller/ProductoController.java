package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
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

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> productoPorCategoria(@PathVariable Long categoria) {
        return ResponseEntity.ok(productoService.productoPorCategoria(categoria));
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Producto>> productoPorCiudad(@PathVariable Long ciudad) {
        return ResponseEntity.ok(productoService.productoPorCiudad(ciudad));
    }

    @GetMapping("/random")
    public ResponseEntity<List<Producto>> productoRandom(){
        return ResponseEntity.ok(productoService.productoRandom());
    }

}
