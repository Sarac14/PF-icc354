package com.example.microservicioevento.repositorio;

import com.example.microservicioevento.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
//    Optional<Evento> findByIdCliente(int idCliente);
    Optional<List<Evento>> findEventoByIdCliente(int idCliente);
}
