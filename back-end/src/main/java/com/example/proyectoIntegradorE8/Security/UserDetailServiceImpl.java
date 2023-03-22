package com.example.proyectoIntegradorE8.Security;

import com.example.proyectoIntegradorE8.entity.Usuario;
import com.example.proyectoIntegradorE8.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario loadUserByUsername(String email) throws UsernameNotFoundException {
      Usuario usuario =  usuarioRepository.findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email: " + email + " no éxiste."));

      return usuario;
    }
}
//loadUserByUsername:  busca un usuario en el repositorio a través del correo electrónico y devuelve los detalles del usuario en un objeto UserDetailsImpl.
//Si el usuario no existe en la base de datos, se lanza una excepción de UsernameNotFoundException. Esto se hace con una expresión lambda que se pasa
// como argumento a orElseThrow(). Si no se encuentra ningún usuario la excepción se lanza y se muestra un mensaje que incluye
// el correo electrónico del usuario que no se pudo encontrar.