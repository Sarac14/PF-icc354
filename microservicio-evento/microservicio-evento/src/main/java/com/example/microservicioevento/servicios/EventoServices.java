package com.example.microservicioevento.servicios;

import com.example.microservicioevento.entidades.Evento;
import com.example.microservicioevento.repositorio.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
    public List<Evento> getEventoByEncargado(int idEncargado) {
        return eventoRepository.findEventoByIdEncargado(idEncargado).orElse(null);
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

    public List<Evento> getEventosProcesandoPorCliente(int idCliente) {
        return eventoRepository.findByIdClienteAndPagado(idCliente,"PROCESANDO");
    }

    public List<Evento> getEventosPagadosPorCliente(int idCliente) {
        return eventoRepository.findByIdClienteAndPagado(idCliente,"PAGADOS");
    }

    public List<Evento> getEventosPendientesPorCliente(int idCliente) {
        return eventoRepository.findByIdClienteAndPagado(idCliente,"PENDIENTE");
    }

    public void marcarComoProcesando(int idEvento) {
        Evento evento = eventoRepository.findById(idEvento).orElseThrow(() -> new NoSuchElementException("Evento no encontrado con ID: " + idEvento));
        evento.setPagado("PROCESANDO");
        eventoRepository.save(evento);
    }

    public void marcarComoPendiente(int idEvento) {
        Evento evento = eventoRepository.findById(idEvento).orElseThrow(() -> new NoSuchElementException("Evento no encontrado con ID: " + idEvento));
        evento.setPagado("PENDIENTE");
        eventoRepository.save(evento);
    }

    public void marcarComoPagado(int idEvento) {
        Evento evento = eventoRepository.findById(idEvento).orElseThrow(() -> new NoSuchElementException("Evento no encontrado con ID: " + idEvento));
        evento.setPagado("PAGADO");
        eventoRepository.save(evento);
    }

}
