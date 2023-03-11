package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ImagenService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/imagen")
public class ImagenController {
    private static final Logger logger = Logger.getLogger(ImagenController.class);
    private ImagenService imagenService;
    @Autowired
    public ImagenController(ImagenService imagenService){
        this.imagenService = imagenService;
    }

    @PostMapping
    public ResponseEntity<?> guardarImagen (@RequestBody Imagen imagen) {
        try {
            logger.info("Se inicia el proceso para guardar una imagen en la BBDD");
            Imagen imagenGuardada = imagenService.guardarImagen(imagen);
            logger.info("La imagen "+imagen.getTitulo()+" fue guardada en la BBDD exitosamente");
            return ResponseEntity.ok(imagenGuardada);
        } catch (SQLException bre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarImagen (@PathVariable Long id) {
        try {
            Imagen imagenBuscada = imagenService.buscarImagen(id);
            return ResponseEntity.ok(imagenBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> actualizarImagen(@RequestBody Imagen imagen){
        try {
            imagenService.buscarImagen(imagen.getId());
            imagenService.guardarImagen(imagen);
            return ResponseEntity.ok(imagen);
        } catch (ResourceNotFoundException rnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (SQLException sqle){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sqle.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> listarImagenes (){
        try {
            List<Imagen> imagenesGuardadas = imagenService.listarImagenes();
            logger.info("Mostrando todas las imagenes registradas en la BBDD");
            return ResponseEntity.ok(imagenesGuardadas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarImagen (@PathVariable Long id) {
        try {
            imagenService.buscarImagen(id);
            imagenService.eliminarImagen(id);
            return ResponseEntity.ok("Se elimino la imagen con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/producto/{producto}")
    public ResponseEntity<List<Imagen>> imagenesPorProducto(@PathVariable Long producto) {
        return ResponseEntity.ok(imagenService.imagenesPorProducto(producto));
    }
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Imagen>> imagenesPorCategoria(@PathVariable Long categoria) {
        return ResponseEntity.ok(imagenService.imagenesPorCategoria(categoria));
    }
}
