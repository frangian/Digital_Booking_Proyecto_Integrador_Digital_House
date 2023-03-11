package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Ciudad;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.CiudadService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/ciudad")
public class CiudadController {
    private static final Logger logger = Logger.getLogger(CiudadController.class);
    private CiudadService ciudadService;
    @Autowired
    public CiudadController (CiudadService ciudadService){
        this.ciudadService = ciudadService;
    }

    @PostMapping
    public ResponseEntity<?> guardarCiudad (@RequestBody Ciudad ciudad){
        try {
            logger.info("Se inicia el proceso para guardar una ciudad en la BBDD");
            Ciudad ciudadGuardada = ciudadService.guardarCiudad(ciudad);
            logger.info("La ciudad fue guardada "+ciudad.getNombre()+"   en la BBDD exitosamente");
            return ResponseEntity.ok(ciudadGuardada);
        } catch (SQLException bre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCiudad (@PathVariable Long id) {
        try {
            Ciudad ciudadBuscada = ciudadService.buscarCiudad(id);
            return ResponseEntity.ok(ciudadBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> actualizarCiudad(@RequestBody Ciudad ciudad){
        try {
            ciudadService.buscarCiudad(ciudad.getId());
            ciudadService.guardarCiudad(ciudad);
            return ResponseEntity.ok(ciudad);
        } catch (ResourceNotFoundException rnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (SQLException sqle){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sqle.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> listarTodas(){
        try {
            List<Ciudad> ciudadesGuardadas = ciudadService.listarTodas();
            logger.info("Mostrando todas las ciudades registradas en la BBDD");
            return ResponseEntity.ok(ciudadesGuardadas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCiudad (@PathVariable Long id) {
        try {
            ciudadService.buscarCiudad(id);
            ciudadService.eliminarCiudad(id);
            return ResponseEntity.ok("Se elimino la ciudad con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
