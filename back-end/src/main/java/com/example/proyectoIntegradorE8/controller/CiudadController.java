package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Ciudad;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.CiudadService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> guardarCiudad (@RequestBody Ciudad ciudad) throws Exception {
        ciudadService.guardarCiudad(ciudad);
        return ResponseEntity.ok("Se registró la ciudad: "+ciudad.getNombre());
    }
    @PutMapping
    public ResponseEntity<String> actualizarCiudad(@RequestBody Ciudad ciudad) throws Exception {
        Optional<Ciudad> ciudadBuscado = ciudadService.buscarCiudad(ciudad.getId());
        if(ciudadBuscado.isPresent()){
            ciudadService.actualizarCiudad(ciudad);
            return ResponseEntity.ok("Se actualizo la ciudad: "+ciudad.getNombre()+", con ID: "+ciudad.getId());
        }
        else {
            throw new ResourceNotFoundException("No se puede actualizar los datos de la ciudad con id: "+ciudad.getId()+" porque no existe en la BBDD");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Ciudad> buscarCiudad (@PathVariable Long id) throws Exception {
        Optional<Ciudad> resultado = ciudadService.buscarCiudad(id);
        if (resultado.isPresent()) {
            logger.info("Se encontro la ciudad con id: "+id+" en la BBDD exitosamente");
            return ResponseEntity.ok(resultado.get());
        } else {
            throw new ResourceNotFoundException("La ciudad con id: "+id+" no existe en la BBDD");
        }
    }
    @GetMapping
    public ResponseEntity<List<Ciudad>> listarTodas(){
        return ResponseEntity.ok(ciudadService.listarTodas());
    }

//    @GetMapping("/ciudad/{ciudad}")
//    public ResponseEntity <List<Ciudad>> listarProdcutoXCiudad(@PathVariable Long ciudad) {
//        return ResponseEntity.ok(ciudadService.productoXCiudad (ciudad));
//    }
}
