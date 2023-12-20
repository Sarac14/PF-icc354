package com.example.microserviciousuario.repositorio;

import com.example.microserviciousuario.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol,String> {
    Rol findByRole(String role);
}
