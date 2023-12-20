package com.example.microservicionotificacion.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ServicioCorreo {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoRegistro(String destinatario, String nombreUsuario, String contrasena) {

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("noreply@sarablog.me");
        mensaje.setTo(destinatario);
        mensaje.setSubject("Información de Acceso");
        mensaje.setText("Hola,\n\nTu cuenta ha sido creada con éxito. Aquí están tus credenciales y el enlace para acceder al sistema:\n\n" +
                "Usuario: " + nombreUsuario + "\n" +
                "Contraseña: " + contrasena + "\n\n" +
                "Saludos,\nEl Equipo de Soporte");
        mailSender.send(mensaje);
    }
}