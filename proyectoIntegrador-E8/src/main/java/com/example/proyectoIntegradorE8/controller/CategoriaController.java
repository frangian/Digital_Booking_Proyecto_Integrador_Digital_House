package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
    public class CategoriaController {

        private CategoriaService categoriaService;

        @Autowired
        public CategoriaController(CategoriaService categoriaService) {
            this.categoriaService = categoriaService;
        }

        @PostMapping
        public ResponseEntity<Categoria> guardarCategoriaNueva (@RequestBody Categoria categoria) {
            return ResponseEntity.ok(categoriaService.guardarCategoria(categoria));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminarCategoria(@PathVariable Long id) throws ResourceNotFoundException {
            Optional<Categoria> categoriaEncontrada = categoriaService.buscarCategoria(id);
            if (categoriaEncontrada.isPresent()) {
                categoriaService.eliminarCategoria(id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La categoría fue eliminada con éxito");
            } else {
                return ResponseEntity.badRequest().body("No se pudo encontrar la categoría");
            }
        }

       @PutMapping
       public ResponseEntity<String> actualizarCategoria(@RequestBody Categoria categoria){
                Optional<Categoria> categoriaEncontrado = categoriaService.buscarCategoria(categoria.getId());
                if (categoriaEncontrado.isPresent()) {
                    categoriaService.actualizarCategoria(categoria);
                    return ResponseEntity.ok("Se actualizaron correctamente los datos de" + categoria.getTitulo());
                } else {
                    return ResponseEntity.badRequest().body("No se pudieron actualizar los datos de" + categoria.getTitulo() + ".Intentar nuevamente");
                }
            }

       @GetMapping
       public ResponseEntity<List<Categoria>> buscarCategoria() {
            return ResponseEntity.ok(categoriaService.buscarTodas());
   }

       @GetMapping("/{id}")
       public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
                Optional<Categoria> categoriaEncontrado = categoriaService.buscarCategoria(id);
                if (categoriaEncontrado.isPresent()) {
                    return ResponseEntity.ok(categoriaEncontrado.get());
                } else {
                    return ResponseEntity.notFound().build();
                }
            }
        }

