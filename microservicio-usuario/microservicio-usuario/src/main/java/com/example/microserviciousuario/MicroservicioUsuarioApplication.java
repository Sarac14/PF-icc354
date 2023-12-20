package com.example.microserviciousuario;

import com.example.microserviciousuario.servicios.UserServices;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.example.microserviciousuario"})
public class MicroservicioUsuarioApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(MicroservicioUsuarioApplication.class, args);
		UserServices usuarioService = (UserServices) applicationContext.getBean("userServices");
		if(usuarioService.getUserByUsername("admin") == null){
			System.out.println("klkkkkkkkkkkkkkkkkk");
			usuarioService.initializeUsuario();
			System.out.println("HHHHHHHHHHHHHHHHHHHHHH");
		}else {
			System.out.println("YA EL USUARIO ADMIN EXISTE");
		}

	}

}
