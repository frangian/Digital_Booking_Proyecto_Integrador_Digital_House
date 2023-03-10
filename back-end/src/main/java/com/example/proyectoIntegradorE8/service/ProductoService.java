package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.entity.Imagen;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.repository.ImagenRepository;
import com.example.proyectoIntegradorE8.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ProductoService {
    private static final Logger logger = Logger.getLogger(ProductoService.class);

    public List<Categoria> findByCategoriaid;
    ProductoRepository productoRepository;
    ImagenRepository imagenRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, ImagenRepository imagenRepository) {
        this.productoRepository = productoRepository;
        this.imagenRepository = imagenRepository;
    }

    public Producto guardarProducto(Producto producto) throws Exception {
        try {

            // Guardar primero el producto
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
            logger.info("No se pudo guardar el producto en la BBDD");
            throw new Exception(e.getMessage());
        }
    }
    public Optional<Producto> buscarProductoXid (Long id){
        logger.info("Se inició la búsqueda del producto con id = "+id);
        return productoRepository.findById(id);
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
    public List<Producto> listarProductos (){
        logger.info("Se inició una operación de listado de productos");
        return productoRepository.findAll();
    }

    public List<Producto> productoPorCategoria (Long id){
        logger.info("Se inició la búsqueda del producto");
        return productoRepository.findByCategoriaId(id);
    }

    public List<Producto> productoPorCiudad (Long id){
        logger.info("Busqueda de producto por ciudad id");
        return productoRepository.findByCiudadId(id);
    }

}
