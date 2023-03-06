package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.List;


@Service
public class ProductoService {
    private static final Logger logger = Logger.getLogger(ProductoService.class);

    public List<Categoria> findByCategoriaid;
    ProductoRepository productoRepository;

    @Autowired
    public ProductoService (ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public Producto crearProducto (Producto producto){
        logger.info("El prodcuto fue creado con éxito.");
        return productoRepository.save(producto);
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

    public List<Producto> productoRandom (){
        logger.info("Me trae 8 productos random");
        return productoRepository.findProductosRandom();
    }


}
