package com.example.proyectoIntegradorE8.service;

import com.example.proyectoIntegradorE8.entity.Ciudad;
import com.example.proyectoIntegradorE8.entity.Usuario;
import com.example.proyectoIntegradorE8.exception.BadRequestException;
import com.example.proyectoIntegradorE8.exception.ResourceNotFoundException;
import com.example.proyectoIntegradorE8.repository.UsuarioRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService (UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario guardarUsuario (Usuario usuario){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        log.info("El usuario fue guradado con éxito");
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuario (Long id) throws ResourceNotFoundException {
        log.info("buscando usuario...");
        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(id);
        if (usuarioBuscado.isPresent()) {
            log.info("Se encontró el usuario con id:" + id + "en la BBDD exitosamente.");
            return usuarioBuscado.get();
        }else {
            log.info("El usuario con id" + id + "no existe en la BBDD");
            throw new ResourceNotFoundException("El usuario con id:" + id + "no existe en la BBDD");
        }
    }
    public List<Usuario> listarTodos() throws Exception {
        try {
            log.info("Se inició una operación de listado de usuarios");
            return usuarioRepository.findAll();
        } catch (Exception e) {
            log.error("Error al listar los usuarios: Exception "+e.getMessage());
            throw new BadRequestException("Ocurrio un error al listar todos los usuarios");
        }
    }
    public void eliminarUsuario (Long id) throws Exception {
        try {
            usuarioRepository.deleteById(id);
            log.warn("Se eliminó el usuario con ID: "+id+" de la BBDD");
        } catch (Exception e){
            log.error("Error al eliminar el usuario: Exception "+e.getMessage());
            throw new Exception("Ocurrió un error al eliminar el usuario");
        }
    }




}
