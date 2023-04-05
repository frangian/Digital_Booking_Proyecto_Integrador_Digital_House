package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.repository.ImagenRepository;
import com.example.proyectoIntegradorE8.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final ImagenRepository imagenRepository;

    public Producto guardarProducto(Producto producto) throws ConstraintViolationException {
        try {
            // Guardar primero el producto
            log.info("guardarProducto: accediendo al repositorio de producto...");
            Producto productoGuardado = productoRepository.save(producto);
            // Guardar las imagenes luego
            List<Imagen> imagenes = productoGuardado.getImagenes();
            log.info("guardando imagenes: " + productoGuardado.getImagenes());
            for (Imagen imagen : imagenes) {
                imagen.setProducto(productoGuardado);
                imagenRepository.save(imagen);
            }
            // retornamos el producto con las imagenes guardadas
            return productoGuardado;
        } catch (ConstraintViolationException e) {
            throw e;
        }
    }
    @Transactional
    public Producto buscarProducto (Long id) throws Exception {
        try {
            log.info("buscarProducto: accediendo al repositorio de producto...");
            Producto productoBuscado = productoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("El producto con id: " + id + " no existe en la BBDD"));
            log.info("Se encontró el producto con id: " + id + " en la BBDD exitosamente");
            return productoBuscado;
        } catch (EntityNotFoundException enfe){
            log.info(enfe.getMessage());
            throw new EntityNotFoundException(enfe.getMessage());
        } catch (DataAccessException dae) {
            throw new Exception("Error al acceder a la base de datos. Mensaje:"+ dae.getMessage());
        }
    }
    @Transactional
    public void actualizarProducto (Producto producto) throws Exception {
        try {
            log.info("actualizarProducto: accediendo al repositorio de producto...");
            productoRepository.save(producto);
        } catch (ConstraintViolationException e) {
            throw e;
        }
    }
    public List<Producto> listarProductos () throws Exception {
        try {
            log.info("listarProductos: accediendo al repositorio de producto...");
            return productoRepository.findAll();
        } catch (Exception e) {
            log.error("Error al listar los productos: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todos los productos");
        }
    }
    public void eliminarProducto (Long id) throws Exception {
        try {
            log.info("eliminarProducto: accediendo al repositorio de producto...");
            productoRepository.deleteById(id);
        } catch (Exception e){
            log.error("Error al eliminar el producto.", e);
            throw new Exception("Error al eliminar el producto. Mensaje:"+ e.getMessage());
        }
    }
    public List<Producto> findByProductoPorCategoria (Long id) throws Exception {
        try {
            log.info("productoPorCategoria: accediendo al repositorio de productos");
            return productoRepository.findByCategoriaId(id);
        } catch (Exception e){
            log.error("Error al buscar productos con categoria id.", e);
            throw new Exception("Error al buscar productos con categoria id. Mensaje:"+ e.getMessage());
        }
    }
    public List<Producto> findByProductoPorCiudad (Long id) throws Exception {
        try {
            log.info("findByProductoPorCiudad: accediendo al repositorio de productos");
            return productoRepository.findByCiudadId(id);
        } catch (Exception e){
            log.error("Error al buscar el producto por ciudad id. Exception: "+e.getMessage());
            throw new Exception("Error al buscar productos disponibles. Exception: "+e.getMessage());
        }
    }
    public List<Producto> findByProductoPorFechas ( LocalDate fechaInicial, LocalDate fechaFinal) throws Exception {
        try {
            log.info("findByProductoPorFechas: accediendo al repositorio de productos");
            return productoRepository.findByProductoFechas(fechaInicial,fechaFinal);
        } catch (Exception e) {
            log.error("Error al buscar productos disponibles. Exception: "+e.getMessage());
            throw new Exception("Error al buscar productos disponibles. Exception: "+e.getMessage());
        }
    }
    public List<Producto> findByProductoPorCiudadIdAndFechas (Long ciudadId, LocalDate fechaInicial, LocalDate fechaFinal) throws Exception {
        try {
            log.info("findByProductoPorCiudadIdAndFechas: accediendo al repositorio de productos");
            return productoRepository.findByCiudadIdAndProductoFechas(ciudadId,fechaInicial,fechaFinal);
        } catch (Exception e) {
            log.error("Error al buscar productos disponibles. Exception: "+e.getMessage());
            throw new Exception("Error al buscar productos disponibles. Exception: "+e.getMessage());
        }
    }
    public List<Producto> findByCategoriaIdAndProductoFechas (Long categoriaId, LocalDate fechaInicial, LocalDate fechaFinal) throws Exception {
        try {
            log.info("findByCategoriaIdAndProductoFechas: accediendo al repositorio de productos");
            return productoRepository.findByCategoriaIdAndProductoFechas(categoriaId,fechaInicial,fechaFinal);
        } catch (Exception e) {
            log.error("Error al buscar productos disponibles. Exception: "+e.getMessage());
            throw new Exception("Error al buscar productos disponibles. Exception: "+e.getMessage());
        }
    }
    public List<Producto> findByCategoriaIdAndCiudadId ( Long ciudadId, Long categoriaId) throws Exception {
        try {
            log.info("findByCategoriaIdAndCiudadId: accediendo al repositorio de productos");
            return productoRepository.findByCategoriaIdAndCiudadId(ciudadId,categoriaId);
        } catch (Exception e) {
            log.error("Error al buscar productos disponibles. Exception: "+e.getMessage());
            throw new Exception("Error al buscar productos disponibles. Exception: "+e.getMessage());
        }
    }
    public List<Producto> findByCiudadIdAndCategoriaIdAndFechas ( Long ciudadId, Long categoriaId, LocalDate fechaInicial, LocalDate fechaFinal) throws Exception {
        try {
            log.info("findByCiudadIdAndCategoriaIdAndFechas: accediendo al repositorio de productos");
            return productoRepository.findByCiudadIdAndCategoriaIdAndFechas(ciudadId,categoriaId, fechaInicial, fechaFinal);
        } catch (Exception e) {
            log.error("Error al buscar productos disponibles. Exception: "+e.getMessage());
            throw new Exception("Error al buscar productos disponibles. Exception: "+e.getMessage());
        }
    }
    public List<Producto> findByProductoXTitulo (String titulo) throws Exception {
        try {
            log.info("findByProductoXTitulo: accediendo al repositorio de productos");
            return productoRepository.findByProductoXTitulo(titulo);
        } catch (Exception e){
            log.error("Error al buscar el producto por titulo. Exception: "+e.getMessage());
            throw new Exception("Error al buscar productos disponibles. Exception: "+e.getMessage());
        }
    }

}
