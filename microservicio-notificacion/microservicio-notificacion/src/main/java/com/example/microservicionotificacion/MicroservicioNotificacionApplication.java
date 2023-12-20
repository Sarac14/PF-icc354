package com.example.microservicionotificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroservicioNotificacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioNotificacionApplication.class, args);
    }

}
