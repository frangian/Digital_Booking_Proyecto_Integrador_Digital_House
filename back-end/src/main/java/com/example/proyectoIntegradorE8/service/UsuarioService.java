package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Usuario;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    //    public Usuario guardarUsuario (Usuario usuario) throws ConstraintViolationException {
////        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
////        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
//        try{
//            log.info("guardarUsuario: accediendo al repositorio de usuario..");
//            return usuarioRepository.save(usuario);
//        } catch (ConstraintViolationException e) {
//            throw e;
//        }
//    }
    public Usuario buscarUsuario (Long id) throws Exception {
        try {
            log.info("buscarUsuario: accediendo al repositorio de usuario...");
            Usuario usuarioBuscado = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("El usuario con id: "+id+" no existe en la BBDD"));
            log.info("Se encontró el producto con id: " + id + " en la BBDD exitosamente");
            return usuarioBuscado;
        } catch (EntityNotFoundException enfe){
            log.info(enfe.getMessage());
            throw new EntityNotFoundException(enfe.getMessage());
        } catch (DataAccessException dae) {
            throw new Exception("Error al acceder a la base de datos. Mensaje:"+ dae.getMessage());
        }
    }
    @Transactional
    public void actualizarUsuario (Usuario usuario) throws Exception {
        try {
            log.info("actualizarUsuario: accediendo al repositorio de usuario...");
            Usuario usuarioExistente = buscarUsuario(usuario.getId());
            usuarioExistente.setCiudad(usuario.getCiudad());
            usuarioExistente.setValidado(usuario.isValidado());
            usuarioRepository.save(usuarioExistente);
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
    public List<Usuario> listarUsuario() throws Exception {
        try {
            log.info("listarUsuario: accediendo al repositorio de usuario...");
            return usuarioRepository.findAll();
        } catch (Exception e) {
            log.error("Error al listar los usuarios: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todos los usuarios" + e.getMessage());
        }
    }
    public void eliminarUsuario (Long id) throws Exception {
        try {
            log.info("eliminarUsuario: accediendo al repositorio de producto...");
            usuarioRepository.deleteById(id);
        } catch (Exception e){
            log.error("Error al eliminar el usuario: Exception "+e.getMessage());
            throw new Exception("Ocurrió un error al eliminar el usuario" + e.getMessage());
        }
    }

    public Usuario findByUsuarioPorEmail(String email) throws Exception {
        try {
            log.info("findByUsuarioPorEmail: accediendo al repositorio de usuario");
            Usuario usuarioBuscado = usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("El usuario con email: "+email+" no existe en la BBDD"));
            log.info("Se encontró el producto con email: " + email + " en la BBDD exitosamente");
            return usuarioBuscado;
        } catch (EntityNotFoundException enfe){
            log.info(enfe.getMessage());
            throw new EntityNotFoundException(enfe.getMessage());
        } catch (DataAccessException dae) {
            throw new Exception("Error al acceder a la base de datos. Mensaje:"+ dae.getMessage());
        }

    }

}