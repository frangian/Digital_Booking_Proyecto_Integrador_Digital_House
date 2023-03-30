package com.example.proyectoIntegradorE8.service;
import com.example.proyectoIntegradorE8.entity.Favorito;
import com.example.proyectoIntegradorE8.entity.Producto;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.repository.FavoritoRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Log4j
@RequiredArgsConstructor
public class FavoritoService {
    private final FavoritoRepository favoritoRepository;

//    @Autowired
//    public FavoritoService (FavoritoRepository favoritoRepository){
//        this.favoritoRepository = favoritoRepository ;
//    }

    public Favorito guardarFavorito (Favorito favorito) throws ConstraintViolationException {
        try {
            log.info("Se accede a la BDD");
            return favoritoRepository.save(favorito);
        } catch (ConstraintViolationException e){
            throw e;
        }
    }
    public Favorito buscarFavorito (Long id) throws ResourceNotFoundException {
        log.info("buscando favorito...");
        Optional<Favorito> favoritoBuscado = favoritoRepository.findById(id);
        if (favoritoBuscado.isPresent()) {
            log.info("Se encontro el producto favorito con id: " + id + " en la BBDD exitosamente");
            return favoritoBuscado.get();
        } else {
            log.info("El producto favorito con id: "+id+" no existe en la BBDD");
            throw new ResourceNotFoundException("El prodcuto favorito con id: "+id+" no existe en la BBDD");
        }
    }

    public List<Favorito> listarFavoritos() throws Exception {
        try {
            log.info("Se inició una operación de listado de favoritos");
            return favoritoRepository.findAll();
        } catch (Exception e) {
            log.error("Error al listar los favoritos: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrió un error al listar todos los favoritos");
        }
    }
    public void actualizarFavoritos(Favorito favorito) throws SQLException, ResourceNotFoundException {
        log.info("entra al service");
        log.info("busca favorito en service");
        Favorito favoritoCompleto = buscarFavorito(favorito.getId());
        favoritoCompleto.setProducto(favorito.getProducto());
        favoritoCompleto.setUsuario(favorito.getUsuario());
        log.info("entra al repositorio");
        favoritoRepository.save(favoritoCompleto);
    }
    public void eliminarFavorito (Long id) throws Exception {
        try {
            favoritoRepository.deleteById(id);
            log.warn("Se eliminó el producto de su lista de favoritos");
        } catch (Exception e){
            log.error("Error al eliminar el producto de su lista de favoritos: Exception "+e.getMessage());
            throw new Exception("Ocurrió un error al eliminar el producto de favoritos");
        }
    }
    public List<Producto> productoPorUsuario (Long id) throws Exception {
        try {
            log.info("Buscando todos los favoritos por usuario con id: "+id);
            return favoritoRepository.findByUsuarioId(id);
        } catch (Exception e){
            log.error("Error al buscar los favoritos del usuario con id. Exception: "+e.getMessage());
            throw new Exception("Ocurrió un error al buscar los favoritos asignadas al id: "+id+" del usuario.");
        }
    }

}


