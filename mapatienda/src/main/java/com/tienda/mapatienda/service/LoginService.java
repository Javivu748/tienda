package com.tienda.mapatienda.service;
import com.tienda.mapatienda.model.cliente;
import com.tienda.mapatienda.repository.clienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private clienteRepository clRep;

    /**
     * Actualiza la fecha del último login
     */
    public void updateUltimaLogueo(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            
            
            Optional<cliente> clienteOpt = clRep.findByUsername(username); 
            
            if (clienteOpt.isPresent()) {
                cliente clnt = clienteOpt.get();
                
                //ACTUALIZAR EL ULTIMO INICIO DE SESION
                clnt.setUltimaSesion(LocalDate.now());
                
                // GUARDAMOS EL CLIENTE 
                clRep.save(clnt);
                
                System.out.println("Fecha de login actualizada para: " + username);
            } else {
                System.out.println("Cliente autenticado pero no encontrado en base de datos de clientes.");
            }
        }
    }
}
