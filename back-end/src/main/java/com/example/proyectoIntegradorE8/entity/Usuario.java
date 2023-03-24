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
public class Usuario  implements UserDetails {
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

//    public Usuario(Long id, String nombre, String apellido, String ciudad, String email, String password, UsuarioRole usuarioRole, List<Reserva> reservas) {
//        this.id = id;
//        this.nombre = nombre;
//        this.apellido = apellido;
//        this.ciudad = ciudad;
//        this.email = email;
//        this.password = password;
//        this.usuarioRole = usuarioRole;
//        this.reservas = reservas;
//    }
//
//    public Usuario() {
//    }
//
//    public Usuario(String nombre, String apellido, String ciudad, String email, String password, UsuarioRole usuarioRole, List<Reserva> reservas) {
//        this.nombre = nombre;
//        this.apellido = apellido;
//        this.ciudad = ciudad;
//        this.email = email;
//        this.password = password;
//        this.usuarioRole = usuarioRole;
//        this.reservas = reservas;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public String getApellido() {
//        return apellido;
//    }
//
//    public void setApellido(String apellido) {
//        this.apellido = apellido;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getCiudad() {
//        return ciudad;
//    }
//
//    public List<Reserva> getReservas() {
//        return reservas;
//    }
//
//    public void setReservas(List<Reserva> reservas) {
//        this.reservas = reservas;
//    }
//
//    public void setCiudad(String ciudad) {
//        this.ciudad = ciudad;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public UsuarioRole getUsuarioRole() {
//        return usuarioRole;
//    }
//
//    public void setUsuarioRole(UsuarioRole usuarioRole) {
//        this.usuarioRole = usuarioRole;
//    }

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
        return Collections.emptyList(); //sirve por si hay roles
    }

    //el método siempre devuelve true, lo que significa que la cuenta nunca expira.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // para que la cuenta se bloquee luego de determinado tiempo que este inactiva
//    public void bloquearCuenta(int tiempoBloqueoMinutos) {
//        Date ahora = new Date();
//        long tiempoBloqueoMillis = tiempoBloqueoMinutos / 1000;
//        lockedUntil = new Date(ahora.getTime() + tiempoBloqueoMillis);
//    }
//
        @Override
        public boolean isAccountNonLocked () {
//            Date ahora = new Date();
//            return lockedUntil == null || ahora.after(lockedUntil);
            return true;
        }
    //Indica si las credenciales (contraseña u otros datos de autenticación) de la cuenta de usuario han expirado o no.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //indica si la cuenta de usuario está habilitada o deshabilitada.
    @Override
    public boolean isEnabled() {
        return true;
    }


}


