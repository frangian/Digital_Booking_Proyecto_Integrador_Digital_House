package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.repository.ImagenRepository;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class ImagenService {
    private ImagenRepository imagenRepository;
    @Autowired
    public ImagenService(ImagenRepository imagenRepository){
        this.imagenRepository = imagenRepository;
    }

    public Imagen guardarImagen(Imagen imagen) throws ConstraintViolationException {
        try {
            log.info("La informacion provista fue correcta, accediendo a ImagenRepository: "+imagen.getId()+", "+imagen.getTitulo());
            return imagenRepository.save(imagen);
        } catch (ConstraintViolationException e) {
            throw e;
        }
    }
    public Imagen buscarImagen (Long id) throws ResourceNotFoundException {
        log.info("buscando imagen...");
        Optional<Imagen> imagenBuscada = imagenRepository.findById(id);
        if (imagenBuscada.isPresent()) {
            log.info("Se encontro la imagen con id: " + id + " en la BBDD exitosamente");
            return imagenBuscada.get();
        } else {
            log.info("La imagen con id: "+id+" no existe en la BBDD");
            throw new ResourceNotFoundException("La imagen con id: "+id+" no existe en la BBDD");
        }
    }
    public List<Imagen> listarImagenes () throws Exception {
        try {
            log.info("Se inició una operación de listado de imagenes");
            return imagenRepository.findAll();
        } catch (Exception e) {
            log.error("Error al listar las imagenes: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todas las imagenes");
        }
    }
    public void eliminarImagen (Long id) throws Exception {
        try {
            imagenRepository.deleteById(id);
            log.warn("Se eliminó la imagen con ID: "+id+" de la BBDD");
        } catch (Exception e){
            log.error("Error al eliminar la imagen: Exception "+e.getMessage());
            throw new Exception("Ocurrio un error al eliminar la imagen");
        }
    }
    public List<Imagen> imagenesPorProducto (Long id){
        log.info("Busqueda de imagenes por producto id");
        return imagenRepository.findByProductoId(id);
    }

    public List<Imagen> imagenesPorCategoria (Long id){
        log.info("Busqueda de imagenes por producto id");
        return imagenRepository.findByCategoriaId(id);
    }


}
