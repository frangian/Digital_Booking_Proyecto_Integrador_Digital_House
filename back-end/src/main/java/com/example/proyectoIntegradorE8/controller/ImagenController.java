package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ImagenService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Imagen> guardarImagen (@RequestBody Imagen imagen) throws Exception {
        Imagen imagenGuardada = imagenService.guardarImagen(imagen);
        logger.info("Se registró la imagen: "+imagen.getTitulo()+", en la BBDD exitosamente");
        return ResponseEntity.ok(imagenGuardada);
    }
    @PutMapping
    public ResponseEntity<String> actualizarImagen(@RequestBody Imagen imagen) throws Exception {
        Optional<Imagen> imagenBuscado = imagenService.buscarImagen(imagen.getId());
        if(imagenBuscado.isPresent()){
            imagenService.actualizarImagen(imagen);
            return ResponseEntity.ok("Se actualizo la imagen: "+imagen.getTitulo()+", con ID: "+imagen.getId());
        }
        else {
            throw new ResourceNotFoundException("No se puede actualizar los datos de la imagen con id: "+imagen.getId()+" porque no existe en la BBDD");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Imagen> buscarImagen (@PathVariable Long id) throws Exception {
        Optional<Imagen> resultado = imagenService.buscarImagen(id);
        if (resultado.isPresent()) {
            logger.info("Se encontro la imagen con id "+id+" en la BBDD exitosamente");
            return ResponseEntity.ok(resultado.get());
        } else {
            throw new ResourceNotFoundException("La imagen con id: "+id+" no existe en la BBDD");
        }
    }
    @GetMapping
    public ResponseEntity<List<Imagen>> listarImagenes (){
        return ResponseEntity.ok(imagenService.listarImagenes());
    }

    @GetMapping("/producto/{producto}")
    public ResponseEntity<List<Imagen>> productoPorCiudad(@PathVariable Long producto) {
        return ResponseEntity.ok(imagenService.imagenesPorProducto(producto));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Imagen>> imagenPorCategoria(@PathVariable Long categoria) {
        return ResponseEntity.ok(imagenService.imagenesPorCategoria(categoria));
    }
}
