package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.repository.ImagenRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenService {

    private static final Logger logger = Logger.getLogger(ImagenService.class);

    ImagenRepository imagenRepository;

    @Autowired
    public ImagenService(ImagenRepository imagenRepository){
        this.imagenRepository = imagenRepository;
    }

    // guardar imagen
    public Imagen guardarImagen(Imagen imagen){
        logger.info("la imagen se guardó con éxito");
        return imagenRepository.save(imagen);
    }

    public List<Imagen> listarImagenes (){
        logger.info("listando imagenes");
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
