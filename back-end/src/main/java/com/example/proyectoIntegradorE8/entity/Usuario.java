package com.example.proyectoIntegradorE8.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name = "usuario")
public class Usuario  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private UsuarioRole usuarioRole;

    @Column
    private Date lockedUntil;

    public Usuario(Long id, String nombre, String apellido, String email, String password, UsuarioRole usuarioRole, Date lockedUntil) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.usuarioRole = usuarioRole;
        this.lockedUntil = lockedUntil;
    }

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String password, UsuarioRole usuarioRole, Date lockedUntil) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.usuarioRole = usuarioRole;
        this.lockedUntil = lockedUntil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioRole getUsuarioRole() {
        return usuarioRole;
    }

    public void setUsuarioRole(UsuarioRole usuarioRole) {
        this.usuarioRole = usuarioRole;
    }

    public Date getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(Date lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(usuarioRole.name());
//        return Collections.singletonList(grantedAuthority);
//    }
//
//    //el método siempre devuelve true, lo que significa que la cuenta nunca expira.
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    // para que la cuenta se bloquee luego de determinado tiempo que este inactiva
//    public void bloquearCuenta(int tiempoBloqueoMinutos) {
//        Date ahora = new Date();
//        long tiempoBloqueoMillis = tiempoBloqueoMinutos / 1000;
//        lockedUntil = new Date(ahora.getTime() + tiempoBloqueoMillis);
//    }
//
//        @Override
//        public boolean isAccountNonLocked () {
//            Date ahora = new Date();
//            return lockedUntil == null || ahora.after(lockedUntil);
//        }
//
//    //Indica si las credenciales (contraseña u otros datos de autenticación) de la cuenta de usuario han expirado o no.
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    //indica si la cuenta de usuario está habilitada o deshabilitada.
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }


}


