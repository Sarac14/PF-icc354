package com.example.microserviciousuario.Client;

import com.example.microserviciousuario.model.Evento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservicio-evento", path = "/evento")
public interface EventoClient {
    @PostMapping()
    Evento save(@RequestBody Evento evento);
    @GetMapping("/misEventos/{idCliente}")
    List<Evento> getEventos(@PathVariable("idCliente") int idCliente);

    @PostMapping("/marcarComoPagado/{idEvento}")
    String marcarComoPagado(@PathVariable("idEvento") int idEvento);
    //    ResponseEntity<Void> marcarComoPagado(@PathVariable("idEvento") int idEvento);

    @GetMapping("/{id}")
    Evento getById(@PathVariable("id") int id);
}
