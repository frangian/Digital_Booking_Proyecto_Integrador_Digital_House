package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.repository.CaractersiticaRepository;
import com.example.proyectoIntegradorE8.repository.ImagenRepository;
import com.example.proyectoIntegradorE8.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Log4j
public class ProductoService {
    private static final Logger logger = Logger.getLogger(ProductoService.class);
    ProductoRepository productoRepository;
    ImagenRepository imagenRepository;
    CaractersiticaRepository caracteristicaRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, ImagenRepository imagenRepository, CaractersiticaRepository caracteristicaRepository) {
        this.productoRepository = productoRepository;
        this.imagenRepository = imagenRepository;
        this.caracteristicaRepository = caracteristicaRepository;
    }

    public Producto guardarProducto(Producto producto) throws Exception {
        try {
            // Guardar primero el producto
            logger.info("Ingresando al service de Producto");
            Producto productoGuardado = productoRepository.save(producto);
            // Guardar las imagenes luego
            List<Imagen> imagenes = productoGuardado.getImagenes();
            logger.info("guardando imagenes: " + productoGuardado.getImagenes());
            for (Imagen imagen : imagenes) {
                imagen.setProducto(productoGuardado);
                imagenRepository.save(imagen);
            }
            // retornamos el producto con las imagenes guardadas
            return productoGuardado;
        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos", e);
            throw new Exception("Error al acceder a la base de datos. Mensaje:"+ e.getMessage());
        } catch (Exception e){
            logger.error("Error al actualizar el producto.", e);
            throw new Exception("Error al actualizar el producto. Mensaje:"+ e.getMessage());
        }
    }
    @Transactional
    public Producto buscarProducto (Long id) throws Exception {
        try {
            logger.info("buscando producto...");
            Producto productoBuscado = productoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("El producto con id: " + id + " no existe en la BBDD"));
            logger.info("Se encontró el producto con id: " + id + " en la BBDD exitosamente");
            return productoBuscado;
        } catch (EntityNotFoundException enfe) {
            logger.error("No se pudo encontrar el producto con id: " + id, enfe);
            throw new EntityNotFoundException("El producto con id: "+id+" no existe en la BBDD");
        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos", e);
            throw new Exception("Error al acceder a la base de datos. Mensaje:"+ e.getMessage());
        } catch (Exception e){
            logger.error("Error al actualizar el producto.", e);
            throw new Exception("Error al actualizar el producto. Mensaje:"+ e.getMessage());
        }
    }
    @Transactional
    public void actualizarProducto (Producto producto) throws Exception {
        try {
            logger.info("Actualizando el producto con id: " + producto.getId());
            productoRepository.save(producto);
        } catch (NullPointerException npe){
            log.error("El objeto producto es null o la lista de caracteristicas o imagenes es null.", npe);
            throw new NullPointerException("El objeto producto es null o la lista de caracteristicas o imagenes es null. Mensaje:"+npe.getMessage());
        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos", e);
            throw new Exception("Error al acceder a la base de datos. Mensaje:"+ e.getMessage());
        } catch (Exception e){
            logger.error("Error al actualizar el producto.", e);
            throw new Exception("Error al actualizar el producto. Mensaje:"+ e.getMessage());
        }
    }
    public List<Producto> listarProductos () throws Exception {
        try {
            logger.info("Se inició una operación de listado de productos");
            return productoRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al listar los productos: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todos los productos");
        }
    }
    public void eliminarProducto (Long id) throws Exception {
        try {
            productoRepository.deleteById(id);
            logger.warn("Se eliminó el producto con ID: "+id+" de la BBDD");
        } catch (Exception e){
            logger.error("Error al eliminar el producto.", e);
            throw new Exception("Error al eliminar el producto. Mensaje:"+ e.getMessage());
        }
    }
    public List<Producto> productoPorCategoria (Long id) throws Exception {
        try {
            logger.info("Buscando todos los productos de la categoria con id: "+id);
            return productoRepository.findByCategoriaId(id);
        } catch (Exception e){
            logger.error("Error al buscar productos con categoria id.", e);
            throw new Exception("Error al buscar productos con categoria id. Mensaje:"+ e.getMessage());
        }
    }
    public List<Producto> productoPorCiudad (Long id) throws Exception {
        try {
            logger.info("Buscando todos los productos en la ciudad con id: "+id);
            return productoRepository.findByCiudadId(id);
        } catch (Exception e){
            logger.error("Error al buscar el producto por ciudad id. Exception: "+e.getMessage());
            throw new Exception("Ocurrio un error al buscar el producto por ciudad id");
        }
    }
    public List<Producto> findByProductoFechas ( LocalDate fechaInicial, LocalDate fechaFinal) throws Exception {
        try {
            logger.info("Buscando todos los productos por fechas");
            return productoRepository.findByProductoFechas(fechaInicial,fechaFinal);
        } catch (Exception e) {
            logger.error("Error al buscar productos disponibles. Exception: "+e.getMessage());
            throw new Exception("Error al buscar productos disponibles. Exception: "+e.getMessage());
        }
    }
    public List<Producto> findByCiudadIdAndProductoFechas (Long ciudadId, LocalDate fechaInicial, LocalDate fechaFinal) throws Exception {
        try {
            logger.info("Service: buscando productos por ciudad id y fechas");
            return productoRepository.findByCiudadIdAndProductoFechas(ciudadId,fechaInicial,fechaFinal);
        } catch (Exception e) {
            logger.error("Error al buscar productos disponibles. Exception: "+e.getMessage());
            throw new Exception("Error al buscar productos disponibles. Exception: "+e.getMessage());
        }
    }

}
