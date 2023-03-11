package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Caracteristica;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.CaractersiticaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/caracteristica")
public class CaracteristicaController {
    private static final Logger logger = Logger.getLogger(CaracteristicaController.class);
    private CaractersiticaService caractersiticaService;

    @Autowired
    public CaracteristicaController (CaractersiticaService caractersiticaService){
        this.caractersiticaService = caractersiticaService;
    }

    @PostMapping
    public ResponseEntity<?> guardarCaracteristica (@RequestBody Caracteristica caracteristica){
        try {
            logger.info("Se inicia el proceso para guardar una caracteristica en la BBDD");
            Caracteristica caracteristicaGuardada = caractersiticaService.guardarCaracteristica(caracteristica);
            logger.info("La caracteristica fue guardada "+caracteristica.getTitulo()+" en la BBDD exitosamente");
            return ResponseEntity.ok(caracteristicaGuardada);
        } catch (SQLException bre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCaracteristica (@PathVariable Long id) {
        try {
            Caracteristica caracteristicaBuscada = caractersiticaService.buscarCaracteristica(id);
            return ResponseEntity.ok(caracteristicaBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> actualizarCaracteristica(@RequestBody Caracteristica caracteristica){
        try {
            caractersiticaService.buscarCaracteristica(caracteristica.getId());
            caractersiticaService.guardarCaracteristica(caracteristica);
            return ResponseEntity.ok(caracteristica);
        } catch (ResourceNotFoundException rnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (SQLException sqle){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sqle.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> listarCaracteristicas() {
        try {
            List<Caracteristica> caractersiticasGuardadas = caractersiticaService.listarCaracteristicas();
            logger.info("Mostrando todas las caractersiticas registradas en la BBDD");
            return ResponseEntity.ok(caractersiticasGuardadas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCaracteristica (@PathVariable Long id) {
        try {
            caractersiticaService.buscarCaracteristica(id);
            caractersiticaService.eliminarCaracteristica(id);
            return ResponseEntity.ok("Se elimino la caracteristica con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/producto/{producto}")
    public ResponseEntity<List<Caracteristica>> caracteristicaXProducto(@PathVariable Long producto){
        return ResponseEntity.ok(caractersiticaService.caracteristicasXProducto(producto));
    }

}
