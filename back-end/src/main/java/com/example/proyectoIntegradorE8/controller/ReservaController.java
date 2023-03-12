package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.entity.Reserva;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.ReservaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/reserva")
public class ReservaController {
    private static final Logger logger = Logger.getLogger(ReservaController.class);
    private ReservaService reservaService;
    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<?> guardarReserva (@RequestBody Reserva reserva){
        try {
            logger.info("guardando reserva...");
            Reserva reservaGuardada = reservaService.guardarReserva(reserva);
            logger.info("La reserva fue guardada "+reserva.getCodigoReserva()+" en la BBDD exitosamente");
            return ResponseEntity.ok(reservaGuardada);
        } catch (SQLException bre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarReserva (@PathVariable Long id) {
        try {
            Reserva reservaBuscada = reservaService.buscarReserva(id);
            return ResponseEntity.ok(reservaBuscada);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> actualizarReserva(@RequestBody Reserva reserva){
        try {
            reservaService.buscarReserva(reserva.getId());
            reservaService.guardarReserva(reserva);
            return ResponseEntity.ok(reserva);
        } catch (ResourceNotFoundException rnfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (SQLException sqle){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sqle.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> listarTodas(){
        try {
            List<Reserva> reservasGuardadas = reservaService.listarReserva();
            logger.info("Mostrando todas las reservas registradas en la BBDD");
            return ResponseEntity.ok(reservasGuardadas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva (@PathVariable Long id) {
        try {
            reservaService.buscarReserva(id);
            reservaService.eliminarReserva(id);
            return ResponseEntity.ok("Se elimino la reserva con ID: "+id+" de la BBDD exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
