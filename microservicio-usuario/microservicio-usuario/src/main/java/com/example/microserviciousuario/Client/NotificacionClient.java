package com.example.microserviciousuario.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservicio-notificaciones", path = "/api/notificaciones")
public interface NotificacionClient {
    @PostMapping("/enviar-correo")
    String enviarCorreo(
            @RequestParam("email") String email,
            @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("contrasena") String contrasena);
    @PostMapping("/enviar-correo-asignacion")
    String enviarCorreoAsignacion(@RequestParam String[] destinatarios);
}
