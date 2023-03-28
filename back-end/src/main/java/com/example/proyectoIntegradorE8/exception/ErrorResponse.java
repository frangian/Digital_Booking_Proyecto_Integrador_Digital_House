    package com.example.proyectoIntegradorE8.exception;

    import org.springframework.http.HttpStatus;

    public class ErrorResponse {
        private HttpStatus status;
        private String message;

        public ErrorResponse(HttpStatus status, String message) {
            this.status = status;
            this.message = message;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }

