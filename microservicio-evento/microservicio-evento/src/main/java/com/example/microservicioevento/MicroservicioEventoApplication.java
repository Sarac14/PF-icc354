package com.example.microservicioevento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroservicioEventoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioEventoApplication.class, args);
	}

}
