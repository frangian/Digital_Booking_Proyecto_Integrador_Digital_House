package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.service.CategoriaService;
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
public class CategoriaController {
    private static final Logger logger = Logger.getLogger(CategoriaController.class);
    private CategoriaService categoriaService;
    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    @PostMapping
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
    public ResponseEntity<?> buscarCategoria(@PathVariable Long id) {
        try {
            Categoria categoriaBuscada = categoriaService.buscarCategoria(id);
            return ResponseEntity.ok(categoriaBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
   @PutMapping
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