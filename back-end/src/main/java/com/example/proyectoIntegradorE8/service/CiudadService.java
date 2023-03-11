package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Ciudad;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.repository.CiudadRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Ciudad guardarCiudad (Ciudad ciudad) throws Exception {
        try {
            logger.info("La informacion provista en el POST fue correcta, accediendo a CiudadRepository: "+ciudad.getNombre()+", "+ciudad.getPais());
            return ciudadRepository.save(ciudad);
        } catch (Exception e){
            logger.error("No se pudo guardar la ciudad en la BBDD");
            throw new Exception(e.getMessage());
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

    public void actualizarCiudad (Ciudad ciudad) throws Exception {
        try {
            logger.info("Se inició una operación de actualizacion de la ciudad con id: "+ciudad.getId());
            ciudadRepository.save(ciudad);
        } catch (Exception e){
            logger.info("No se pudo actualizar la ciudad");
            throw new Exception(e.getMessage());
        }
    }
    public List<Ciudad> listarTodas() {
        try {
            logger.info("Se inició una operación de listado de ciudades");
            return ciudadRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al listar las ciudades", e);
            throw e;
        }
    }

    public void eliminarCiudad (Long id) {
        ciudadRepository.deleteById(id);
        logger.warn("Se eliminó la ciudad con ID: "+id+" de la BBDD");
    }

}
