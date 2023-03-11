package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.repository.CategoriaRepository;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
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

    public Categoria guardarCategoria (Categoria categoria) throws SQLException {
        try {
            logger.info("La informacion provista fue correcta, accediendo a CategoriaRepository: "+categoria.getTitulo()+".");
            return categoriaRepository.save(categoria);
        } catch (Exception e){
            logger.error("No se pudo guardar/actualizar la categoria "+categoria.getTitulo()+" en la BBDD. Exception: "+e.getMessage()+".");
            throw new SQLException("No se pudo guardar/actualizar la categoria en la BBDD. No pueden quedar campos solicitados vacios.");
        }
    }
    public Categoria buscarCategoria (Long id) throws ResourceNotFoundException {
        logger.info("buscando categoria...");
        Optional<Categoria> categoriaBuscada = categoriaRepository.findById(id);
        if (categoriaBuscada.isPresent()) {
            logger.info("Se encontro la categoria con id: " + id + " en la BBDD exitosamente");
            return categoriaBuscada.get();
        } else {
            logger.info("La categoria con id: "+id+" no existe en la BBDD");
            throw new ResourceNotFoundException("La categoria con id: "+id+" no existe en la BBDD");
        }
    }
    public List<Categoria> listarCategorias () throws Exception {
        try {
            logger.info("Se inició una operación de listado de categorias");
            return categoriaRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al listar las categorias: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todas las categorias");
        }
    }
    public void eliminarCategoria(Long id) throws Exception {
        try {
            categoriaRepository.deleteById(id);
            logger.warn("Se eliminó la categoria con ID: "+id+" de la BBDD");
        } catch (Exception e){
            logger.error("Error al eliminar la categoria: Exception "+e.getMessage());
            throw new Exception("Ocurrio un error al eliminar la categoria");
        }
    }

}