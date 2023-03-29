    package com.example.proyectoIntegradorE8.exception;

    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.ResponseStatus;

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends Exception{
        public ResourceNotFoundException(String mensaje) {super(mensaje);}
    }
