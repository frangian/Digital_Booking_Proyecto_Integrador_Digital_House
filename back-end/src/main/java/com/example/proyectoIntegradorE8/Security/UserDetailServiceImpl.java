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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      Usuario usuario =  usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email" + email + "no éxiste."));

      return new UserDetailsImpl(usuario);

    }
}
