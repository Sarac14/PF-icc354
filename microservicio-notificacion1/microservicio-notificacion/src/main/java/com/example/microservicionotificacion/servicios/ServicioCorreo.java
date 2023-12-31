package com.example.microservicionotificacion.servicios;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ServicioCorreo {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoAsignacion(String[] destinatarios) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setFrom("noreply@sarablog.me");
            helper.setTo(destinatarios);
            helper.setSubject("Asignacion de trabajo");
            String htmlMsg = "<h3>Un nuevo evento ha sido agendado</h3>"
                    + "<p>Para la asignacion del trabajo:</p>"
                    + "<a href='http://localhost:8181/compra'>Haz clic aquí</a>"
                    + "<p>Asignar trabajo lo mas rapido posible. Gracias de ante mano!</p>";
            helper.setText(htmlMsg, true); // true indica que el mensaje es HTML
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }

    public void enviarCorreoRegistro1(String destinatario, String nombreUsuario, String contrasena) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setFrom("noreply@sarablog.me");
            helper.setTo(destinatario);
            helper.setSubject("Información de Acceso");
            String htmlMsg = "<h3>Bienvenid@!</h3>"
                    + "<p>Hola,</p>"
                    + "<p>Tu cuenta ha sido creada con éxito. Aquí están tus credenciales y el enlace para acceder al sistema:</p>"
                    +"<p>Usuario:</p>"+nombreUsuario
                    +"<p>Contreseña:</p>"+contrasena
                    + "<p>Para iniciar sesión ir al siguiente enlace:</p>"
                    + "<a href='http://localhost:8181/login'>Haz clic aquí</a>"
                    +"<br>"
                    + "<p>S & H MULTIMEDIA</p>";
            helper.setText(htmlMsg, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }

    public void enviarCorreoResumen(String destinatario, byte[] reportePdf) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // Configura MimeMessageHelper para modo multiparte
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        try {
            helper.setFrom("noreply@sarablog.me");
            helper.setTo(destinatario);
            helper.setSubject("Resumen de Compra");
            helper.setText("Gracias por preferirnos! Aquí está su reporte de compra.");

            // Adjuntar PDF
            helper.addAttachment("ReporteCompra.pdf", new ByteArrayResource(reportePdf));

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);
    }

}