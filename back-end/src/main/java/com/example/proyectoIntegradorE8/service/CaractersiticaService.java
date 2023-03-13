package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Caracteristica;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.repository.CaractersiticaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CaractersiticaService {
    private static final Logger logger = Logger.getLogger(CaractersiticaService.class);
    CaractersiticaRepository caractersiticaRepository;
     @Autowired
    public CaractersiticaService (CaractersiticaRepository caractersiticaRepository){
         this.caractersiticaRepository = caractersiticaRepository;
     }

     public Caracteristica guardarCaracteristica (Caracteristica caracteristica) throws SQLException {
         try {
             logger.info("La informacion provista fue correcta, accediendo a CaracteristicaRepository: "+caracteristica.getTitulo()+".");
             return caractersiticaRepository.save(caracteristica);
         } catch (Exception e){
             logger.error("No se pudo guardar/actualizar la caracteristica "+caracteristica.getTitulo()+" en la BBDD. Exception: "+e.getMessage()+".");
             throw new SQLException("No se pudo guardar/actualizar la caracteristica en la BBDD. No pueden quedar campos solicitados vacios.");
         }
     }
    public Caracteristica buscarCaracteristica (Long id) throws ResourceNotFoundException {
        logger.info("buscando caracteristica...");
        Optional<Caracteristica> caracteristicaBuscada = caractersiticaRepository.findById(id);
        if (caracteristicaBuscada.isPresent()) {
            logger.info("Se encontro la caracteristica con id: " + id + " en la BBDD exitosamente");
            return caracteristicaBuscada.get();
        } else {
            logger.info("La caracteristica con id: "+id+" no existe en la BBDD");
            throw new ResourceNotFoundException("La caracteristica con id: "+id+" no existe en la BBDD");
        }
    }    public List<Caracteristica> listarCaracteristicas() throws BadRequestException {
        try {
            logger.info("Se inició una operación de listado de caracteristicas");
            return caractersiticaRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al listar las caracteristicas: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todas las caracteristicas");
        }
    }
    public void eliminarCaracteristica (Long id) throws Exception {
        try {
            caractersiticaRepository.deleteById(id);
            logger.warn("Se eliminó la caracteristica con ID: "+id+" de la BBDD");
        } catch (Exception e){
            logger.error("Error al eliminar la caracteristica: Exception "+e.getMessage());
            throw new Exception("Ocurrio un error al eliminar la caracteristica");
        }
    }
    public List<Caracteristica> caracteristicasXProducto (Long id){
         return caractersiticaRepository.findAllByProductoId(id);
    }

}
