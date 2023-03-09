package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Optional;


@Service
public class ProductoService {
    private static final Logger logger = Logger.getLogger(ProductoService.class);

    public List<Categoria> findByCategoriaid;
    ProductoRepository productoRepository;

    @Autowired
    public ProductoService (ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public Producto guardarProducto (Producto producto) throws Exception {
        try {
            logger.info("Se inició una operación de guardado del producto con titulo: "+
                    producto.getTitulo());
            return productoRepository.save(producto);
        } catch (Exception e){
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

//    public List<Producto> productosRandom () throws Exception{
//        try {
//            logger.info("Mostrar 8 productos random (ProductoService)");
//            List<Producto> productosRandom = productoRepository.findAllProductosRandom();
//            return productosRandom;
//        } catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//    }


}
