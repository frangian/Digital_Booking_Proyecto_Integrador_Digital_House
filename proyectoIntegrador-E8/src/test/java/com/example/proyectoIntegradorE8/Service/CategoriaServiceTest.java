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
        assertEquals(1L, categoriaGuardada.getTitulo());
    }

    @Test
    @Order(2)
    public void actualizarCategoriaTest() {
        Categoria categoriaParaActualizar = new Categoria(1L, "Hoteles", "Proveen a los huéspedes de servicios adicionales como restaurantes", "https://www.google.com/search?q=imagen+de+hoteles&oq=imagen+de+hoteles+&aqs=chrome.0.0i512l3j0i22i30i625l7.147420j0j7&sourceid=chrome&ie=UTF-8#imgrc=LCWhLCoS9ENSWM");
        categoriaService.actualizarCategoria(categoriaParaActualizar);
        Optional<Categoria> categoriaActualizada = categoriaService.buscarCategoria(categoriaParaActualizar.getId());
        assertEquals("Hoteles", categoriaActualizada.get().getId());

    }

    @Test
    @Order(3)
    public void buscarCategoriaByIdTest(){
        Long idABuscar=1L;
        Optional<Categoria> categoriaBuscada=categoriaService.buscarCategoria(idABuscar);
        assertNotNull(categoriaBuscada.get());
    }

    @Test
    @Order(4)
    public void eliminarCategoriaTest() throws BadRequestException, ResourceNotFoundException {
        Long idEliminar=1L;
        categoriaService.eliminarCategoria(idEliminar);
        Optional<Categoria> categoriaElimnada = categoriaService.buscarCategoria(idEliminar);
        assertFalse(categoriaElimnada.isPresent());
    }

    @Test
    @Order(5)
    public void buscarTodasCategoriasTest(){
        List<Categoria> categoria= categoriaService.buscarTodas();
        Integer cantidadEsperada=4;
        assertEquals(cantidadEsperada,categoria.size());
    }
}
