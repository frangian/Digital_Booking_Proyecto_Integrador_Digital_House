package com.example.proyectoIntegradorE8.controller;

import com.example.proyectoIntegradorE8.security.Authentication.AuthenticationRequest;
import com.example.proyectoIntegradorE8.security.Authentication.AuthenticationResponse;
import com.example.proyectoIntegradorE8.security.Authentication.AuthenticationService;
import com.example.proyectoIntegradorE8.security.Authentication.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Log4j
public class UsuarioController {
    private final AuthenticationService service;

    @PostMapping("/registro")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        log.info("entrando al controller");
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/autenticacion")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        log.info("entrando al controller");
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping
    public ResponseEntity<String> get (){
        return ResponseEntity.ok("todo bien");
    }


}
