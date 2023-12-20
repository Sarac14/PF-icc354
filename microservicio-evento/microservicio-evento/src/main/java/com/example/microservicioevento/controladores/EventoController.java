package com.example.microservicioevento.controladores;

import com.example.microservicioevento.entidades.Evento;
import com.example.microservicioevento.servicios.EventoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    EventoServices eventoServices;

    @GetMapping
    public ResponseEntity<List<Evento>> getAll() {
        List<Evento> eventos = eventoServices.getAll();
        if(eventos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getById(@PathVariable("id") int id) {
        Evento evento = eventoServices.getEventoById(id);
        if(evento == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(evento);
    }

    @GetMapping("/misEventos/{idCliente}")
    public ResponseEntity<List<Evento>> getByCliente(@PathVariable("idCliente") int idCliente) {
        List<Evento> evento = eventoServices.getEventoByCliente(idCliente);
        if(evento == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(evento);
    }

    @PostMapping()
    public ResponseEntity<Evento> save(@RequestBody Evento evento) {
        Evento newEvento = eventoServices.save(evento);
        return ResponseEntity.ok(newEvento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        Evento user = eventoServices.getEventoById(id);
        if (user == null)
            return ResponseEntity.notFound().build();

        eventoServices.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> update(@PathVariable("id") int id, @RequestBody Evento updatedEvento) {
        Evento evento = eventoServices.getEventoById(id);
        if (evento == null)
            return ResponseEntity.notFound().build();


        evento.setIdEncargado(updatedEvento.getIdEncargado());
        evento.setIdCliente(updatedEvento.getIdCliente());
        evento.setFechaEvento(updatedEvento.getFechaEvento());
        evento.setFechaPago(updatedEvento.getFechaPago());
        evento.setTipoEvento(updatedEvento.getTipoEvento());
        evento.setIncluirVideo(updatedEvento.isIncluirVideo());
        evento.setTotal(updatedEvento.getTotal());

        Evento updatedUserEntity = eventoServices.save(evento);

        return ResponseEntity.ok(updatedUserEntity);
    }
}
