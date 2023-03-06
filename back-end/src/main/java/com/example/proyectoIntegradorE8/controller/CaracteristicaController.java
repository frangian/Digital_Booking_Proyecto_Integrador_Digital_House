package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Caracteristica;
import com.example.proyectoIntegradorE8.service.CaractersiticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/caracteristica")
public class CaracteristicaController {

    private CaractersiticaService caractersiticaService;

    @Autowired
    public CaracteristicaController (CaractersiticaService caractersiticaService){
        this.caractersiticaService = caractersiticaService;
    }

    @PostMapping
    public ResponseEntity <Caracteristica> crearCaractersitica (@RequestBody Caracteristica caracteristica){
        return ResponseEntity.ok(caractersiticaService.crearCaracteristica(caracteristica));
    }

    @GetMapping
    public ResponseEntity<List<Caracteristica>> listarTodas() {
        return ResponseEntity.ok(caractersiticaService.listarTodas());
    }
}
