package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Ciudad;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.service.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {

    private CiudadService ciudadService;

    @Autowired
    public CiudadController (CiudadService ciudadService){
        this.ciudadService = ciudadService;
    }


    @PostMapping
    public ResponseEntity<Ciudad> crearCiudad (@RequestBody Ciudad ciudad){
        return ResponseEntity.ok(ciudadService.crearCiudad(ciudad));
    }
    @GetMapping
    public ResponseEntity<List<Ciudad>> listarTodas(){
        return ResponseEntity.ok(ciudadService.listarTodas());
    }

    @GetMapping("/{Ciudad}")
    public ResponseEntity <List<Ciudad>> listarProdcutoXCiudad(@PathVariable Long ciudad) {
        return ResponseEntity.ok(ciudadService.productoXCiudad); //chquear
    }
}
