package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Reserva;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.repository.ReservaRepository;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class ReservaService {
    private static final Logger logger = Logger.getLogger(ReservaService.class);
    private ReservaRepository reservaRepository;
    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva guardarReserva (Reserva reserva) throws SQLException {
        try {
            logger.info("incia la capa de servicio, se accede a la BBDD");
            return reservaRepository.save(reserva);
        } catch (Exception e){
            logger.error("No se pudo guardar/actualizar la reserva "+reserva.getId()+" en la BBDD. Exception: "+e.getMessage()+".");
            throw new SQLException("No se pudo guardar/actualizar la reserva en la BBDD. No pueden quedar campos solicitados vacios.");
        }
    }
    public Reserva buscarReserva (Long id) throws ResourceNotFoundException {
        logger.info("buscando reserva...");
        Optional<Reserva> reservaBuscada = reservaRepository.findById(id);
        if (reservaBuscada.isPresent()) {
            logger.info("Se encontro la reserva con id: " + id + " en la BBDD exitosamente");
            return reservaBuscada.get();
        } else {
            logger.info("La reserva con id: "+id+" no existe en la BBDD");
            throw new ResourceNotFoundException("La reserva con id: "+id+" no existe en la BBDD");
        }
    }
    public void actualizarReserva(Reserva reserva) throws SQLException, ResourceNotFoundException {
        logger.info("entra al service");
        logger.info("busca la reserva en service");
        Reserva reservaCompleta = buscarReserva(reserva.getId());
        reservaCompleta.setHoraComienzo(reserva.getHoraComienzo());
        reservaCompleta.setFechaInicial(reserva.getFechaInicial());
        reservaCompleta.setFechaFinal(reserva.getFechaFinal());
        reservaCompleta.setProducto(reserva.getProducto());
        reservaCompleta.setUsuario(reserva.getUsuario());
        logger.info("entra al repositorio");
        reservaRepository.save(reservaCompleta);
    }
    public List<Reserva> listarReserva() throws Exception {
        try {
            logger.info("Se inició una operación de listado de reservas");
            return reservaRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al listar las reservas: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todas las reservas");
        }
    }
    public void eliminarReserva (Long id) throws Exception {
        try {
            reservaRepository.deleteById(id);
            logger.warn("Se eliminó la reserva con ID: "+id+" de la BBDD");
        } catch (Exception e){
            logger.error("Error al eliminar la reserva: Exception "+e.getMessage());
            throw new Exception("Ocurrio un error al eliminar la reserva");
        }
    }
    public List<Reserva> reservaPorProducto (Long id) throws Exception {
        try {
            logger.info("Buscando todos las reservas para el producto con id: "+id);
            return reservaRepository.findByProductoId(id);
        } catch (Exception e){
            logger.error("Error al buscar reservas por producto por su id. Exception: "+e.getMessage());
            throw new Exception("Ocurrio un error al buscar las reservas asignadas al id: "+id+" del producto.");
        }
    }

}
