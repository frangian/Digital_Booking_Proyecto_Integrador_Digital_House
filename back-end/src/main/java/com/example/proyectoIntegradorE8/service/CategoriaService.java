package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public Categoria guardarCategoria (Categoria categoria) throws ConstraintViolationException {
        try {
            log.info("guardarCategoria: accediendo al repositorio de categoria...");
            return categoriaRepository.save(categoria);
        } catch (ConstraintViolationException e) {
            throw e;
        }
    }
    public Categoria buscarCategoria (Long id) throws Exception {
        try {
            log.info("buscarCategoria: accediendo al repositorio de categoria...");
            Categoria categoriaBuscado = categoriaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("El categoria con id: " + id + " no existe en la BBDD"));
            log.info("Se encontró el categoria con id: " + id + " en la BBDD exitosamente");
            return categoriaBuscado;
        } catch (EntityNotFoundException enfe){
            log.info(enfe.getMessage());
            throw new EntityNotFoundException(enfe.getMessage());
        } catch (DataAccessException dae) {
            throw new Exception("Error al acceder a la base de datos. Mensaje:"+ dae.getMessage());
        }
    }
    @Transactional
    public void actualizarCategoria (Categoria categoria) throws Exception {
        try {
            log.info("actualizarCategoria: accediendo al repositorio de categoria...");
            categoriaRepository.save(categoria);
        } catch (ConstraintViolationException e) {
            throw e;
        }
    }
    public List<Categoria> listarCategorias () throws Exception {
        try {
            log.info("listarProductos: accediendo al repositorio de categoria...");
            return categoriaRepository.findAll();
        } catch (Exception e) {
            log.error("Error al listar las categorias: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todas las categorias");
        }
    }
    public void eliminarCategoria(Long id) throws Exception {
        try {
            log.info("eliminarCategoria: accediendo al repositorio de categoria...");
            categoriaRepository.deleteById(id);
        } catch (Exception e){
            log.error("Error al eliminar la categoria: Exception "+e.getMessage());
            throw new Exception("Ocurrio un error al eliminar la categoria");
        }
    }

}