package com.tienda.mapatienda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tienda.mapatienda.model.cliente;

public interface clienteRepository extends JpaRepository<cliente, Long> {
    
    
    List<cliente> findByNombreAndApellido(String nombre, String apellido);


    List<cliente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrTelefonoContaining(
    String nombre, String apellido, String telefono);

    Optional<cliente> findByUsername(String username);
    
    

    // Consulta personalizada con @Query (JPQL) que reciba al menos 1 parámetro
    /**
     * Busca clientes por el número de teléfono.
     * @param telefono El número de teléfono a buscar.
     */
    @Query("SELECT c FROM cliente c WHERE c.telefono = :telefonoCliente")
    cliente buscarPorTelefono(@Param("telefonoCliente") String telefono);
    

}
