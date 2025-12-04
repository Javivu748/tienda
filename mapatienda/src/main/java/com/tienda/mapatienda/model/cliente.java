package com.tienda.mapatienda.model;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCliente;

    // Username para iniciar sesión
    @Column(unique = true, nullable = false)
    private String username;
    
    // Contraseña para iniciar sesión
    @Column(nullable = false)
    private String password;

    private LocalDate fechaRegistro;
    private LocalDate ultimaSesion;
    private String nombre;
    private String apellido;
    private String telefono;
    private String rol;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<pedido> pedidos = new ArrayList<pedido>();


    public Optional<cliente> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now(); // El usuario al registrarse se añadirá la fecha de hoy
        }
        if (ultimaSesion == null) {
            ultimaSesion = LocalDate.now(); // El usuario al registrarse se añadirá la ultima sesión como hoy
        }
    }

  
    

}
