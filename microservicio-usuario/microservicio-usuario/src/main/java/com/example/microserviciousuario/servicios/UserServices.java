package com.example.microserviciousuario.servicios;

import com.example.microserviciousuario.Client.EventoClient;
import com.example.microserviciousuario.entidades.Rol;
import com.example.microserviciousuario.entidades.Usuario;
import com.example.microserviciousuario.model.Evento;
import com.example.microserviciousuario.repositorio.RolRepository;
import com.example.microserviciousuario.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RolRepository rolRepository;

    @Autowired
    EventoClient eventoClient;

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

    private void enviarCorreoBienvenida(String email, String nombreUsuario, String contrasena) {
        System.out.println("ENTRE A EMVIARCORREOBIENVENIDA!!!!!!!!!!!!!!!!!!1");
        RestTemplate restTemplate = new RestTemplate();
        String urlNotificaciones = "http://localhost:8003/api/notificaciones/enviar-correo";
        String parametros = "?email=" + email + "&nombreUsuario=" + nombreUsuario + "&contrasena=" + contrasena;
        restTemplate.postForObject(urlNotificaciones + parametros, null, String.class);
    }

    public Evento saveEvento(int userId, Evento evento){
        evento.setIdCliente(userId);
        Evento newEvento = eventoClient.save(evento);
        return newEvento;
    }

    public Evento saveEvento1( Evento evento){

        Evento newEvento = eventoClient.save(evento);
        return newEvento;
    }
}
