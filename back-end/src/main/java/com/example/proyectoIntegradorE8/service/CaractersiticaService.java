package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Caracteristica;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.repository.CaractersiticaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log4j
@RequiredArgsConstructor
public class CaractersiticaService {
    private final CaractersiticaRepository caractersiticaRepository;

     public Caracteristica guardarCaracteristica (Caracteristica caracteristica) throws ConstraintViolationException {
         try {
             log.info("guardarCaracteristica: accediendo al repositorio de producto...");
             return caractersiticaRepository.save(caracteristica);
         } catch (ConstraintViolationException e) {
             throw e;
         }
     }
    public Caracteristica buscarCaracteristica (Long id) throws Exception {
        try {
            log.info("buscarCaracteristica: accediendo al repositorio de caracteristica...");
            Caracteristica caracteristicaBuscado = caractersiticaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("La caracteristica con id: " + id + " no existe en la BBDD"));
            log.info("Se encontró la caracteristica con id: " + id + " en la BBDD exitosamente");
            return caracteristicaBuscado;
        } catch (EntityNotFoundException enfe){
            log.info(enfe.getMessage());
            throw new EntityNotFoundException(enfe.getMessage());
        } catch (DataAccessException dae) {
            throw new Exception("Error al acceder a la base de datos. Mensaje:"+ dae.getMessage());
        }
    }
    @Transactional
    public void actualizarCaracteristica (Caracteristica caracteristica) throws Exception {
        try {
            log.info("actualizarCaracteristica: accediendo al repositorio de caracteristica...");
            caractersiticaRepository.save(caracteristica);
        } catch (ConstraintViolationException e) {
            throw e;
        }
    }
    public List<Caracteristica> listarCaracteristicas() throws BadRequestException {
        try {
            log.info("listarCaracteristicas: accediendo al repositorio de caracteristica...");
            return caractersiticaRepository.findAll();
        } catch (Exception e) {
            log.error("Error al listar las caracteristicas: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todas las caracteristicas");
        }
    }
    public void eliminarCaracteristica (Long id) throws Exception {
        try {
            log.info("eliminarCaracteristica: accediendo al repositorio de caracteristica...");
            caractersiticaRepository.deleteById(id);
        } catch (Exception e){
            log.error("Error al eliminar la caracteristica: Exception "+e.getMessage());
            throw new Exception("Ocurrio un error al eliminar la caracteristica. Mensaje: "+ e.getMessage());
        }
    }
    public List<Caracteristica> caracteristicasXProducto (Long id) throws Exception {
        try {
            log.info("caracteristicasXProducto: accediendo al repositorio de caracteristica");
            return caractersiticaRepository.findAllByProductoId(id);
        } catch (Exception e) {
            log.error("Error al buscar caracteristicas disponibles. Exception: "+e.getMessage());
            throw new Exception("Error al buscar caracteristica disponibles. Exception: "+e.getMessage());
        }
    }

}
