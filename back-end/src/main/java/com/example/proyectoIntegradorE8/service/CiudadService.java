package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Ciudad;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.repository.CiudadRepository;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class CiudadService {
    private CiudadRepository ciudadRepository;
    @Autowired
    public CiudadService (CiudadRepository ciudadRepository){
        this.ciudadRepository = ciudadRepository;
    }

    public Ciudad guardarCiudad (Ciudad ciudad) throws ConstraintViolationException {
        try {
            log.info("La informacion provista fue correcta, accediendo a CiudadRepository: "+ciudad.getNombre()+", "+ciudad.getPais());
            return ciudadRepository.save(ciudad);
        } catch (ConstraintViolationException e) {
            throw e;
        }
    }
    public Ciudad buscarCiudad (Long id) throws ResourceNotFoundException {
        log.info("buscando ciudad...");
        Optional<Ciudad> ciudadBuscada = ciudadRepository.findById(id);
        if (ciudadBuscada.isPresent()) {
            log.info("Se encontro la ciudad con id: " + id + " en la BBDD exitosamente");
            return ciudadBuscada.get();
        } else {
            log.info("La ciudad con id: "+id+" no existe en la BBDD");
            throw new ResourceNotFoundException("La ciudad con id: "+id+" no existe en la BBDD");
        }
    }
    public List<Ciudad> listarTodas() throws Exception {
        try {
            log.info("Se inició una operación de listado de ciudades");
            return ciudadRepository.findAll();
        } catch (Exception e) {
            log.error("Error al listar las ciudades: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todas las ciudades");
        }
    }
    public void eliminarCiudad (Long id) throws Exception {
        try {
            ciudadRepository.deleteById(id);
            log.warn("Se eliminó la ciudad con ID: "+id+" de la BBDD");
        } catch (Exception e){
            log.error("Error al eliminar la ciudad: Exception "+e.getMessage());
            throw new Exception("Ocurrio un error al eliminar la ciudad");
        }
    }

}
