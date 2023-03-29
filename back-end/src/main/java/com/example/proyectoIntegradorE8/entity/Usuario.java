package com.example.proyectoIntegradorE8.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email")
@Table(name = "usuario")

public class Usuario  implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String ciudad;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "usuario_role")
    private UsuarioRole usuarioRole;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorito> favoritos = new ArrayList<>();

    @Override
    public String getUsername() {
     return this.getEmail();
    }
    @Override
    public String getPassword() {
      return this.password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.emptyList();
    }
    @Override
    public boolean isAccountNonExpired() {
          return true;
    }
    @Override
    public boolean isAccountNonLocked () {
    //  Date ahora = new Date();
    //  return lockedUntil == null || ahora.after(lockedUntil);
           return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
            return true;
    }
    @Override
    public boolean isEnabled() {
          return true;
    }
}


