package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Ciudad;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.repository.CiudadRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CiudadService {
    private static final Logger logger = Logger.getLogger(CiudadService.class);
    private CiudadRepository ciudadRepository;
    @Autowired
    public CiudadService (CiudadRepository ciudadRepository){
        this.ciudadRepository = ciudadRepository;
    }

    public Ciudad guardarCiudad (Ciudad ciudad) throws SQLException {
        try {
            logger.info("La informacion provista fue correcta, accediendo a CiudadRepository: "+ciudad.getNombre()+", "+ciudad.getPais());
            return ciudadRepository.save(ciudad);
        } catch (Exception e){
            logger.error("No se pudo guardar/actualizar la ciudad "+ciudad.getNombre()+" en la BBDD. Exception: "+e.getMessage()+".");
            throw new SQLException("No se pudo guardar/actualizar la ciudad en la BBDD. No pueden quedar campos solicitados vacios.");
        }
    }
    public Ciudad buscarCiudad (Long id) throws ResourceNotFoundException {
        logger.info("buscando ciudad...");
        Optional<Ciudad> ciudadBuscada = ciudadRepository.findById(id);
        if (ciudadBuscada.isPresent()) {
            logger.info("Se encontro la ciudad con id: " + id + " en la BBDD exitosamente");
            return ciudadBuscada.get();
        } else {
            logger.info("La ciudad con id: "+id+" no existe en la BBDD");
            throw new ResourceNotFoundException("La ciudad con id: "+id+" no existe en la BBDD");
        }
    }
    public List<Ciudad> listarTodas() throws Exception {
        try {
            logger.info("Se inició una operación de listado de ciudades");
            return ciudadRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al listar las ciudades: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todas las ciudades");
        }
    }
    public void eliminarCiudad (Long id) throws Exception {
        try {
            ciudadRepository.deleteById(id);
            logger.warn("Se eliminó la ciudad con ID: "+id+" de la BBDD");
        } catch (Exception e){
            logger.error("Error al eliminar la ciudad: Exception "+e.getMessage());
            throw new Exception("Ocurrio un error al eliminar la ciudad");
        }
    }

}
