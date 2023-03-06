package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.repository.CategoriaRepository;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private static final Logger logger = Logger.getLogger(CategoriaService.class);
    CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService (CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria guardarCategoria (Categoria categoria){
        logger.info("Se inició una operación de guardado de las categorías con título: "+
                categoria.getTitulo());
        return categoriaRepository.save(categoria);
    }
    public void actualizarCategoria (Categoria categoria){
        logger.info("Se actualizó la categoría con id="+
                categoria.getId());
        categoriaRepository.save(categoria);
    }

    public Optional<Categoria> buscarCategoria (Long id){
        logger.info("Se inició la búsqueda de la categoría con id="+id);
        return categoriaRepository.findById(id);
    }

    public void eliminarCategoria(Long id) throws ResourceNotFoundException {
        Optional<Categoria> categoriaAEliminar = buscarCategoria(id);
        if (categoriaAEliminar.isPresent()) {
            categoriaRepository.deleteById(id);
            logger.warn("Se realizo una operación de eliminar categoría con");
        }
        else{
            throw new ResourceNotFoundException("La categoría a eliminar no existe en la base de datos");
        }
    }
    public List<Categoria> buscarTodas (){
        logger.info("Se inició una operación de listado de categorías");
        return categoriaRepository.findAll();
    }

//    public List<Categoria> randomHome (){
//        logger.info("Se inició una operación de listado de 8 categorias random");
//        return categoriaRepository.findAll();
//    }
}
