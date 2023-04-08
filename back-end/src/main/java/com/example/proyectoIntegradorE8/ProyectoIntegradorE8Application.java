package com.example.proyectoIntegradorE8;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(
		info = @Info(
				title = "Proyecto Integrador - Equipo 8",
				version = "1.0.0",
				description = "Documentacion de la API del proyecto"
		)
)
@SpringBootApplication
public class ProyectoIntegradorE8Application {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoIntegradorE8Application.class, args);
	}

}

