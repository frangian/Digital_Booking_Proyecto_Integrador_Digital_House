package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Caracteristica;
import com.example.proyectoIntegradorE8.repository.CaractersiticaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaractersiticaService {

    private static final Logger logger = Logger.getLogger(CaractersiticaService.class);
    CaractersiticaRepository caractersiticaRepository;

     @Autowired
    public CaractersiticaService (CaractersiticaRepository caractersiticaRepository){
         this.caractersiticaRepository = caractersiticaRepository;
     }

     public Caracteristica crearCaracteristica (Caracteristica caracteristica){
         logger.info("La caractersitica fue creada");
         return caractersiticaRepository.save(caracteristica);
     }


}
