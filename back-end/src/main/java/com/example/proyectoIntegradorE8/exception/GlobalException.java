package com.example.proyectoIntegradorE8.exception;

import lombok.extern.log4j.Log4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

    @RestControllerAdvice
    @Log4j
    public class GlobalException {
        @ExceptionHandler({ResourceNotFoundException.class})
        public ResponseEntity<String> procesarResourceNotFoundException (ResourceNotFoundException resourceNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNotFoundException.getMessage());
        }
        @ExceptionHandler({BadRequestException.class})
        public ResponseEntity<String> procesarBadRequestException(BadRequestException badRequestException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestException.getMessage());
        }
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
            String message = "Tipo de argumento incorrecto. Se esperaba un numero entero de tipo: \'" + ex.getRequiredType().getSimpleName() + "\', pero se recibió: \'" + ex.getValue() + "\'";
    //        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    //        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
        }
    //    @ExceptionHandler(DataIntegrityViolationException.class)
    //    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    //        String message = "DIVE | Error de integridad de datos: " + ex.getCause()+ex.getMessage()+"|";
    //        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    //    }
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
            log.error("Error al acceder a la base de datos: "+ ex.getCause().getMessage());
            String message = "Error de integridad de datos: " +ex.getCause().getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        @ExceptionHandler(DuplicateKeyException.class)
        public ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException ex) {
            String message = "Ya existe un registro con ese identificador";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, message);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }
}
