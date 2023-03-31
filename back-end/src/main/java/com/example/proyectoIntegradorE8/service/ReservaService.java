package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Reserva;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.repository.ReservaRepository;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class ReservaService {
    private ReservaRepository reservaRepository;
    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva guardarReserva (Reserva reserva) throws ConstraintViolationException {
        try {
            log.info("incia la capa de servicio, se accede a la BBDD");
            return reservaRepository.save(reserva);
        } catch (ConstraintViolationException e) {
            throw e;
        }
    }
    public Reserva buscarReserva (Long id) throws ResourceNotFoundException {
        log.info("buscando reserva...");
        Optional<Reserva> reservaBuscada = reservaRepository.findById(id);
        if (reservaBuscada.isPresent()) {
            log.info("Se encontro la reserva con id: " + id + " en la BBDD exitosamente");
            return reservaBuscada.get();
        } else {
            log.info("La reserva con id: "+id+" no existe en la BBDD");
            throw new ResourceNotFoundException("La reserva con id: "+id+" no existe en la BBDD");
        }
    }
    public void actualizarReserva(Reserva reserva) throws SQLException, ResourceNotFoundException {
        log.info("entra al service");
        log.info("busca la reserva en service");
        Reserva reservaCompleta = buscarReserva(reserva.getId());
        reservaCompleta.setHoraComienzo(reserva.getHoraComienzo());
        reservaCompleta.setFechaInicial(reserva.getFechaInicial());
        reservaCompleta.setFechaFinal(reserva.getFechaFinal());
        reservaCompleta.setProducto(reserva.getProducto());
        reservaCompleta.setUsuario(reserva.getUsuario());
        log.info("entra al repositorio");
        reservaRepository.save(reservaCompleta);
    }
    public List<Reserva> listarReserva() throws Exception {
        try {
            log.info("Se inició una operación de listado de reservas");
            return reservaRepository.findAll();
        } catch (Exception e) {
            log.error("Error al listar las reservas: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todas las reservas");
        }
    }
    public void eliminarReserva (Long id) throws Exception {
        try {
            reservaRepository.deleteById(id);
            log.warn("Se eliminó la reserva con ID: "+id+" de la BBDD");
        } catch (Exception e){
            log.error("Error al eliminar la reserva: Exception "+e.getMessage());
            throw new Exception("Ocurrio un error al eliminar la reserva");
        }
    }
    public List<Reserva> reservaPorProducto (Long id) throws Exception {
        try {
            log.info("Buscando todos las reservas para el producto con id: "+id);
            return reservaRepository.findByProductoId(id);
        } catch (Exception e){
            log.error("Error al buscar reservas por producto por su id. Exception: "+e.getMessage());
            throw new Exception("Ocurrio un error al buscar las reservas asignadas al id: "+id+" del producto.");
        }
    }

}
