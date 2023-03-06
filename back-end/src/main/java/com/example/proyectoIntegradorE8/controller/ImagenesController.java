package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Caracteristica;
import com.example.proyectoIntegradorE8.entity.Imagenes;
import com.example.proyectoIntegradorE8.service.ImagenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imagen")
public class ImagenesController {

    private ImagenesService imagenesService;

    @Autowired
    public ImagenesController (ImagenesService imagenesService){
        this.imagenesService = imagenesService;
    }
    //guardar imagen
    @PostMapping
    public ResponseEntity<Imagenes> guardarImagen (@RequestBody Imagenes imagenes){
        return ResponseEntity.ok(imagenesService.gurdarImagen(imagenes));
    }
}
