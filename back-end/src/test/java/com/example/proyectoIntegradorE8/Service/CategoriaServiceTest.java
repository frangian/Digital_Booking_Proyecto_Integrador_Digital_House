package com.example.proyectoIntegradorE8.Service;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.service.CategoriaService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @Test
    @Order(1)
    public void guardarCategoriaTest() throws Exception {
        Categoria categoriaParaGuardar = new Categoria("Hoteles", "Tests");
        Categoria categoriaGuardada = categoriaService.guardarCategoria(categoriaParaGuardar);
        assertEquals("Hoteles", categoriaGuardada.getTitulo());
    }

    @Test
    @Order(2)
    public void actualizarCategoriaTest() throws Exception {
        List<Categoria> categorias= categoriaService.listarCategorias();
        Categoria ultimaCategoria = categorias.get(categorias.size()-1);
        Categoria categoriaParaActualizar = new Categoria(ultimaCategoria.getId(), "Deptos", "Tests");
        categoriaService.guardarCategoria(categoriaParaActualizar);
        Categoria categoriaActualizada = categoriaService.buscarCategoria(categoriaParaActualizar.getId());
        assertEquals("Deptos", categoriaActualizada.getTitulo());

    }

    @Test
    @Order(3)
    public void buscarCategoriaByIdTest() throws Exception {
        List<Categoria> categoria= categoriaService.listarCategorias();
        Categoria ultimaCategoria = categoria.get(categoria.size()-1);
        Categoria categoriaBuscada = categoriaService.buscarCategoria(ultimaCategoria.getId());
        assertEquals("Deptos", categoriaBuscada.getTitulo());
    }

    @Test
    @Order(4)
    public void buscarTodasCategoriasTest() throws Exception {
        List<Categoria> listadoCategorias1= categoriaService.listarCategorias();
        Integer cantidadCategorias1 = listadoCategorias1.size();
        Categoria categoriaGuardada = new Categoria("Hoteles 2", "Test 2");
        categoriaService.guardarCategoria(categoriaGuardada);
        List<Categoria> listadoCategorias2 = categoriaService.listarCategorias();
        Integer cantidadCategorias2 = listadoCategorias2.size();
        assertEquals(1,cantidadCategorias2-cantidadCategorias1);
        categoriaService.eliminarCategoria(categoriaGuardada.getId());
    }

    @Test
    @Order(5)
    public void eliminarCategoriaTest() throws Exception {
        List<Categoria> listadoCategorias= categoriaService.listarCategorias();
        int cantidadCategoriasInicio = listadoCategorias.size();
        Categoria ultimaCategoria = listadoCategorias.get(listadoCategorias.size()-1);
        categoriaService.eliminarCategoria(ultimaCategoria.getId());
        List<Categoria> listadoCategorias2= categoriaService.listarCategorias();
        int cantidadCategoriasFinal = listadoCategorias2.size();
        assertEquals(1,cantidadCategoriasInicio-cantidadCategoriasFinal);
    }

}
