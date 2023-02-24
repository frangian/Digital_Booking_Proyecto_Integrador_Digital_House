package com.example.proyectoIntegradorE8.Service;

import com.example.proyectoIntegradorE8.entity.Categoria;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.service.CategoriaService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @Test
    @Order(1)
    public void guardarCategoriaTest() {
        Categoria categoriaParaGuardar = new Categoria("Hoteles", "Proveen a los huéspedes de servicios adicionales como restaurantes, piscinas y guarderías", "https://www.google.com/search?q=imagen+de+hoteles&oq=imagen+de+hoteles+&aqs=chrome.0.0i512l3j0i22i30i625l7.147420j0j7&sourceid=chrome&ie=UTF-8#imgrc=LCWhLCoS9ENSWM");
        Categoria categoriaGuardada = categoriaService.guardarCategoria(categoriaParaGuardar);
        //assertEquals(1L, categoriaGuardada.getTitulo());
        assertEquals("Hoteles", categoriaGuardada.getTitulo());
    }

    @Test
    @Order(2)
    public void actualizarCategoriaTest() {
        List<Categoria> categoria= categoriaService.buscarTodas();
        Categoria ultimaCategoria = categoria.get(categoria.size()-1);
        Categoria categoriaParaActualizar = new Categoria(ultimaCategoria.getId(), "Deptos", "Proveen a los huéspedes de servicios adicionales como restaurantes", "https://www.google.com/search?q=imagen+de+hoteles&oq=imagen+de+hoteles+&aqs=chrome.0.0i512l3j0i22i30i625l7.147420j0j7&sourceid=chrome&ie=UTF-8#imgrc=LCWhLCoS9ENSWM");
        categoriaService.actualizarCategoria(categoriaParaActualizar);
        Optional<Categoria> categoriaActualizada = categoriaService.buscarCategoria(categoriaParaActualizar.getId());
        assertEquals("Deptos", categoriaActualizada.get().getTitulo());

    }

    @Test
    @Order(3)
    public void buscarCategoriaByIdTest(){
        List<Categoria> categoria= categoriaService.buscarTodas();
        Categoria ultimaCategoria = categoria.get(categoria.size()-1);
        Optional<Categoria> categoriaBuscada = categoriaService.buscarCategoria(ultimaCategoria.getId());
        assertEquals("Deptos", categoriaBuscada.get().getTitulo());
    }

    @Test
    @Order(4)
    public void buscarTodasCategoriasTest() throws BadRequestException, ResourceNotFoundException {
        List<Categoria> categoria= categoriaService.buscarTodas();
        Categoria categoriaParaGuardar = new Categoria("Hoteles", "Proveen a los huéspedes de servicios adicionales como restaurantes, piscinas y guarderías", "https://www.google.com/search?q=imagen+de+hoteles&oq=imagen+de+hoteles+&aqs=chrome.0.0i512l3j0i22i30i625l7.147420j0j7&sourceid=chrome&ie=UTF-8#imgrc=LCWhLCoS9ENSWM");
        Categoria categoriaGuardada = categoriaService.guardarCategoria(categoriaParaGuardar);
        List<Categoria> categoria2 = categoriaService.buscarTodas();
        Integer cantidadEsperada = categoria.size()+1;
        assertEquals(1,categoria2.size()-categoria.size());
        categoriaService.eliminarCategoria(categoria2.get(categoria2.size()-1).getId());
    }

    @Test
    @Order(5)
    public void eliminarCategoriaTest() throws BadRequestException, ResourceNotFoundException {
        List<Categoria> categoria= categoriaService.buscarTodas();
        Categoria ultimaCategoria = categoria.get(categoria.size()-1);
        categoriaService.eliminarCategoria(ultimaCategoria.getId());
        Optional<Categoria> categoriaElimnada = categoriaService.buscarCategoria(ultimaCategoria.getId());
        assertFalse(categoriaElimnada.isPresent());
    }

}
