package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Imagenes;
import com.example.proyectoIntegradorE8.repository.ImagenesRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagenesService {

    private static final Logger logger = Logger.getLogger(ImagenesService.class);

    ImagenesRepository imagenesRepository;

    @Autowired
    public ImagenesService (ImagenesRepository imagenesRepository){
        this.imagenesRepository = imagenesRepository;
    }

    // guardar imagen
    public Imagenes gurdarImagen (Imagenes imagenes){
        logger.info("la imagen se guardó con éxito");
        return imagenesRepository.save(imagenes);
    }


}
