package com.example.microserviciousuario.model;

import lombok.Data;

@Data
public class Evento {
    private int id;
    private int idEncargado;
    private int idCliente;
    private String fechaEvento;
    private String fechaPago;
    private int total;
    private String tipoEvento;
    private boolean incluirVideo;
    private boolean pagado;
}
