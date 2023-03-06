package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Ciudad;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.repository.CiudadRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadService {
    private static final Logger logger = Logger.getLogger(CiudadService.class); //chquear
    public List<Ciudad> productoXCiudad; //chequear

    CiudadRepository ciudadRepository;

    @Autowired
    public CiudadService (CiudadRepository ciudadRepository){
        this.ciudadRepository = ciudadRepository;
    }

    public Ciudad crearCiudad (Ciudad ciudad){
        logger.info("La ciudad fue creada con éxito.");
        return ciudadRepository.save(ciudad);
    }
    public List<Ciudad> listarTodas(){
        logger.info("Se inició una operación de listado de ciudades");
        return ciudadRepository.findAll();
    }

//    public List<Ciudad> productoXCiudad (Long id){
//        logger.info("Se inició la busqueda de producto por ciudad");
//        return ciudadRepository.findByCiudadId(id);
//    }

}


// listar todas las ciudades
// crear metodo buscandolo ppr ciudad

