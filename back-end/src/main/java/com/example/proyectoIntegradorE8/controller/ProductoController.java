package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ProductoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/producto")
public class ProductoController {

    private static final Logger logger = Logger.getLogger(ProductoService.class);

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

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Producto> resultado = productoService.buscarProductoXid(id);
        if (resultado.isPresent()) {
            logger.info("El producto con id: "+id+", fue encontrado en la BD");
            return ResponseEntity.ok(resultado.get());
        } else {
            throw new ResourceNotFoundException("El producto con id: "+id+" no existe en la BD");
        }
    };

    // buscar por  producto por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> productoPorCategoria(@PathVariable Long categoria) {
        return ResponseEntity.ok(productoService.productoPorCategoria(categoria));
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Producto>> productoPorCiudad(@PathVariable Long ciudad) {
        return ResponseEntity.ok(productoService.productoPorCiudad(ciudad));
    }

//    @GetMapping("/random")
//    public ResponseEntity<List<Producto>> productoRandom(){
//        return ResponseEntity.ok(productoService.productoRandom());
//    }

    @GetMapping("/random")
    public ResponseEntity<?> productosRandom(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.productosRandom());
        } catch (Exception e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
