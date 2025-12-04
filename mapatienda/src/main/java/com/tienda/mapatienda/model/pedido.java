package com.tienda.mapatienda.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPedido;

    private Date fechaPedido;
    private String estado;
    private double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false) // Columna de clave foránea
    @JsonBackReference
    private cliente cliente;

    

}
