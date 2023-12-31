package com.example.microserviciousuario.servicios;

import com.example.microserviciousuario.Client.EventoClient;
import com.example.microserviciousuario.Client.NotificacionClient;
import com.example.microserviciousuario.Util.GeneradorReportes;
import com.example.microserviciousuario.entidades.Rol;
import com.example.microserviciousuario.entidades.Usuario;
import com.example.microserviciousuario.model.Evento;
import com.example.microserviciousuario.repositorio.RolRepository;
import com.example.microserviciousuario.repositorio.UserRepository;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RolRepository rolRepository;

    @Autowired
    EventoClient eventoClient;

    @Autowired
    NotificacionClient notificacionClient;

    @Autowired
    GeneradorReportes reporteService;

    public List<Usuario> getAll() {
        return userRepository.findAll();
    }
    public Usuario getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario user) {
        System.out.println("SAVEEEEE!!!!!!!!!!!!!!!!!!1");
        Usuario userNew = userRepository.save(user);
        System.out.println("EMVIARCORREOBIENVENIDA!!!!!!!!!!!!!!!!!!1");
        enviarCorreoBienvenida(user.getEmail(), user.getUsername(), user.getPassword());
        return userNew;
    }

    public Usuario getUserByUsername (String username){
        return userRepository.findByUsername(username).orElse(null);
    }
    public Usuario crearUsuario(String username,  String password, boolean admin, String nombre, String apellido, String email){

        Rol rolAdmin = rolRepository.findByRole("ROLE_ADMIN");
        Rol rolSUser = rolRepository.findByRole("ROLE_USER");

        Usuario user = new Usuario();
        user.setUsername(username);
        //user.setPassword(passwordEncoder.encode(password));
        user.setPassword(password);
        user.setApellido(apellido);
        user.setEmail(email);
        user.setNombre(nombre);
//        user.setActivo(activo);
        if (admin){
            user.setRols(Arrays.asList(rolAdmin));
        }else{
            user.setRols(Arrays.asList(rolSUser));
        }
        save(user);
        return user;
    }
    public void initializeUsuario(){

        Rol rolAdmin = rolRepository.findByRole("ROLE_ADMIN");
        Rol rolUsuario = rolRepository.findByRole("ROLE_USER");
//        if (rolAdmin != null){
//            return;
//        }
        System.out.println("Creación del usuario y rol en la base de datos");

        if(rolAdmin == null){
            rolAdmin = new Rol("ROLE_ADMIN");
            rolRepository.save(rolAdmin);
        }

        if(rolUsuario == null) {
            rolUsuario = new Rol("ROLE_USER");
            rolRepository.save(rolUsuario);
        }

        crearUsuario("admin", "admin", true, "ADMIN", "ADMIN", "saracontreras.2002@hotmail.com");
        crearUsuario("user", "user", false, "Usuario", "User", "saram.contrerast.14@gmail.com");
    }

    public void delete(int id) {
        userRepository.delete(getUserById(id));
    }



    public void enviarCorreoBienvenida(String email, String nombreUsuario, String contrasena) {
        String response = notificacionClient.enviarCorreo(email, nombreUsuario, contrasena);
        System.out.println(response);
    }


    public void enviarCorreoAsignacion(String[] destinatarios) {
        System.out.println("ENTRE A enviarCorreoAsignacion!!!!!!!!!!!!!!!!!!1");
        String response = notificacionClient.enviarCorreoAsignacion(destinatarios);
        System.out.println(response);
    }

    public void enviarCorreoResumen(String destinatario, byte[] pdf) {
        System.out.println("ENTRE A enviarCorreoResumen!!!!!!!!!!!!!!!!!!1");
        String response = notificacionClient.enviarCorreoResumen(destinatario, pdf);
        System.out.println(response);
    }

    public Evento saveEvento(int userId, Evento evento, String[] correoEmpleados) throws JRException, IOException {
        evento.setIdCliente(userId);
        byte[] reportePdf = reporteService.exportToPdf(evento, getUserById(userId).getNombre() + ' ' + getUserById(userId).getApellido());

        Evento newEvento = eventoClient.save(evento);
        enviarCorreoAsignacion(correoEmpleados);
        enviarCorreoResumen(getUserById(userId).getEmail(), reportePdf);
        return newEvento;
    }


    public Evento saveEvento1( Evento evento){

        Evento newEvento = eventoClient.save(evento);
        return newEvento;
    }

    public List<String> obtenerCorreosAdmins() {
        List<String> emails = new ArrayList<>();
        List<Usuario> usuarios = getAll();
        for (Usuario user: usuarios) {
            for (Rol rol : user.getRols()) {
                if (rol.getRole().equals("ROLE_ADMIN")) {
                    emails.add(user.getEmail());
                }
            }
        }

        return emails;
    }

}
