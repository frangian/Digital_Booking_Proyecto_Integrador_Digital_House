package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imagen")
public class ImagenController {

    private ImagenService imagenService;

    @Autowired
    public ImagenController(ImagenService imagenService){
        this.imagenService = imagenService;
    }
    //guardar imagen
    @PostMapping
    public ResponseEntity<Imagen> guardarImagen (@RequestBody Imagen imagen){
        return ResponseEntity.ok(imagenService.guardarImagen(imagen));
    }

    @GetMapping
    public ResponseEntity<List<Imagen>> listarImagenes (){
        return ResponseEntity.ok(imagenService.listarImagenes());
    }

    @GetMapping("/producto/{producto}")
    public ResponseEntity<List<Imagen>> productoPorCiudad(@PathVariable Long producto) {
        return ResponseEntity.ok(imagenService.imagenesPorProducto(producto));
    }
}
