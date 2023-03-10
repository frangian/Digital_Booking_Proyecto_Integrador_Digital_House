package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.repository.ImagenRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenService {
    private static final Logger logger = Logger.getLogger(ImagenService.class);

    ImagenRepository imagenRepository;

    @Autowired
    public ImagenService(ImagenRepository imagenRepository){
        this.imagenRepository = imagenRepository;
    }

    public Imagen guardarImagen(Imagen imagen) throws Exception {
        try {
            logger.info("Se inició una operación de guardado de la imagen con titulo: "+
                    imagen.getTitulo());
            return imagenRepository.save(imagen);
        } catch (Exception e){
            logger.info("No se pudo guardar la imagen en la BBDD");
            throw new Exception(e.getMessage());
        }
    }
    public Optional<Imagen> buscarImagen (Long id){
        logger.info("Se inició la búsqueda de la imagen con id="+id);
        return imagenRepository.findById(id);
    }
    public void actualizarImagen (Imagen imagen) throws Exception {
        try {
            logger.info("Se inició una operación de actualizacion de la imagen con id: "+
                    imagen.getId());
            imagenRepository.save(imagen);
        } catch (Exception e){
            logger.info("No se pudo actualizar la imagen");
            throw new Exception(e.getMessage());
        }
    }
    public List<Imagen> listarImagenes (){
        logger.info("Se inició una operación de listado de imagenes");
        return imagenRepository.findAll();
    }

    public List<Imagen> imagenesPorProducto (Long id){
        logger.info("Busqueda de imagenes por producto id");
        return imagenRepository.findByProductoId(id);
    }

    public List<Imagen> imagenesPorCategoria (Long id){
        logger.info("Busqueda de imagenes por producto id");
        return imagenRepository.findByCategoriaId(id);
    }


}
