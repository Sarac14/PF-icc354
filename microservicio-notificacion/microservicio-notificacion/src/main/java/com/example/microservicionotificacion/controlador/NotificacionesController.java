package com.example.microservicionotificacion.controlador;

import com.example.microservicionotificacion.servicios.ServicioCorreo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionesController {

    @Autowired
    private ServicioCorreo servicioCorreo;

    @PostMapping("/enviar-correo")
    public ResponseEntity<?> enviarCorreo(
            @RequestParam String email,
            @RequestParam String nombreUsuario,
            @RequestParam String contrasena) {
        servicioCorreo.enviarCorreoRegistro(email, nombreUsuario, contrasena);
        System.out.println("Correo de bienvenida enviado a " + email);
        return ResponseEntity.ok("Correo de bienvenida enviado a " + email);
    }
}