package com.example.microserviciousuario.controladores;

import com.example.microserviciousuario.entidades.Rol;
import com.example.microserviciousuario.entidades.Usuario;
import com.example.microserviciousuario.model.Evento;
import com.example.microserviciousuario.repositorio.RolRepository;
import com.example.microserviciousuario.servicios.JwtTokenProvider;
import com.example.microserviciousuario.servicios.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServices userService;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> users = userService.getAll();
        if(users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable("id") int id) {
        Usuario user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }
    @PostMapping()
    public ResponseEntity<Usuario> save(@RequestBody Usuario user) {
        if(user.getRols() == null){
            Rol rolSUser = rolRepository.findByRole("ROLE_USER");
            user.setRols(Arrays.asList(rolSUser));
        }

        Usuario userNew = userService.save(user);
        return ResponseEntity.ok(userNew);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        Usuario user = userService.getUserById(id);
        if (user == null)
            return ResponseEntity.notFound().build();

        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable("id") int id, @RequestBody Usuario updatedUser) {
        Usuario user = userService.getUserById(id);
        if (user == null)
            return ResponseEntity.notFound().build();


        user.setNombre(updatedUser.getNombre());
        user.setApellido(updatedUser.getApellido());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setUsername(updatedUser.getUsername());
        user.setRols(updatedUser.getRols());

        Usuario updatedUserEntity = userService.save(user);  // Guarda los cambios en la base de datos

        return ResponseEntity.ok(updatedUserEntity);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginUser) {
        Usuario user = userService.getUserByUsername(loginUser.getUsername());
        if (user != null && user.getPassword().equals(loginUser.getPassword())) {
            // Aquí deberías generar el token JWT
            String token = jwtTokenProvider.createToken(user.getUsername(), user.getId(), user.getRols());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", user.getUsername());
            model.put("token", token);
            return ResponseEntity.ok(model);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }



    @PostMapping("/saveEvento/{userId}")
    public ResponseEntity<Evento> saveEvento(@PathVariable("userId") int userId, @RequestBody Evento evento) {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }

        List<String> correosEmpleados = userService.obtenerCorreosAdmins();
        String[] correosArray = correosEmpleados.toArray(new String[0]);


        Evento eventoNew = userService.saveEvento(userId, evento, correosArray);
        return ResponseEntity.ok(eventoNew);
    }

    @PostMapping("/saveEvento")
    public ResponseEntity<Evento> saveEvento1(@RequestBody Evento evento) {
//        if(userService.getUserById(userId) == null)
//            return ResponseEntity.notFound().build();
        Evento eventoNew = userService.saveEvento1(evento);
        return ResponseEntity.ok(evento);
    }


}
