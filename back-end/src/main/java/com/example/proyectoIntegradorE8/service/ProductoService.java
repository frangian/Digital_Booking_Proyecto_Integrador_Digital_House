package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.repository.ImagenRepository;
import com.example.proyectoIntegradorE8.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductoService {
    private static final Logger logger = Logger.getLogger(ProductoService.class);
    ProductoRepository productoRepository;
    ImagenRepository imagenRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, ImagenRepository imagenRepository) {
        this.productoRepository = productoRepository;
        this.imagenRepository = imagenRepository;
    }

    public Producto guardarProducto(Producto producto) throws SQLException {
        try {
            // Guardar primero el producto
            logger.info("La informacion provista fue correcta, accediendo a ProductoRepository: "+producto.getId()+", "+producto.getTitulo());
            Producto productoGuardado = productoRepository.save(producto);
            // Guardar las imagenes luego
            Set<Imagen> imagenes = productoGuardado.getImagenes();
            logger.info("guardando imagenes: " + productoGuardado.getImagenes());
            for (Imagen imagen : imagenes) {
                imagen.setProducto(productoGuardado);
                imagenRepository.save(imagen);
            }
            // retornamos el producto con las imagenes guardadas
            return productoGuardado;
        } catch (Exception e) {
            logger.error("No se pudo guardar/actualizar el producto "+producto.getTitulo()+" en la BBDD. Exception: "+e.getMessage()+".");
            throw new SQLException("No se pudo guardar/actualizar el producto en la BBDD. No pueden quedar campos solicitados vacios.");
        }
    }
    public Producto buscarProducto (Long id) throws ResourceNotFoundException {
        logger.info("buscando producto...");
        Optional<Producto> productoBuscado = productoRepository.findById(id);
        if (productoBuscado.isPresent()) {
            logger.info("Se encontro el producto con id: " + id + " en la BBDD exitosamente");
            return productoBuscado.get();
        } else {
            logger.info("El producto con id: "+id+" no existe en la BBDD");
            throw new ResourceNotFoundException("El producto con id: "+id+" no existe en la BBDD");
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
    public void actualizarProducto (Producto producto) throws Exception {
        try {
            logger.info("Se inició una operación de actualizacion del producto con id: "+
                    producto.getId());
            productoRepository.save(producto);
        } catch (Exception e){
            logger.info("No se pudo actualizar el producto");
            throw new Exception(e.getMessage());
        }
    }
    public void eliminarProducto (Long id) throws Exception {
        try {
            productoRepository.deleteById(id);
            logger.warn("Se eliminó el producto con ID: "+id+" de la BBDD");
        } catch (Exception e){
            logger.error("Error al eliminar el producto: Exception "+e.getMessage());
            throw new Exception("Ocurrio un error al eliminar el producto");
        }
    }
    public List<Producto> productoPorCategoria (Long id) throws Exception {
        try {
            logger.info("Buscando todos los productos de la categoria con id: "+id);
            return productoRepository.findByCategoriaId(id);
        } catch (Exception e){
            logger.error("Error al buscar el producto por categoria id. Exception: "+e.getMessage());
            throw new Exception("Ocurrio un error al buscar el producto por categoria id");
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
    public List<Producto> productosDisponiblesFecha ( LocalDate fechaInicial, LocalDate fechaFinal) throws Exception {
        try {
            logger.info("Buscando todos los productos por fechas");
            return productoRepository.findByProductoFechas(fechaInicial,fechaFinal);
        } catch (Exception e) {
            logger.error("Error al buscar productos disponibles. Exception: "+e.getMessage());
            throw new Exception("Error al buscar productos disponibles. Exception: "+e.getMessage());
        }
    }

}
