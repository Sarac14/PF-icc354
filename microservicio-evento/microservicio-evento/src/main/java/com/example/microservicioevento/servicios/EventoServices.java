package com.example.microservicioevento.servicios;

import com.example.microservicioevento.entidades.Evento;
import com.example.microservicioevento.repositorio.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoServices {
    @Autowired
    EventoRepository eventoRepository;

    public List<Evento> getAll() {
        return eventoRepository.findAll();
    }
    public List<Evento> getEventoByCliente(int idCliente) {
        return eventoRepository.findEventoByIdCliente(idCliente).orElse(null);
    }
    public Evento save(Evento evento) {
        Evento newEvento = eventoRepository.save(evento);
        return newEvento;
    }
    public void delete(int id) {
        eventoRepository.delete(getEventoById(id));
    }

    public Evento getEventoById(int id) {
        return eventoRepository.findById(id).orElse(null);
    }
}
