package com.tienda.mapatienda.service;

import com.tienda.mapatienda.model.cliente;
import com.tienda.mapatienda.repository.clienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private clienteRepository clRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // Buscar el cliente en la base de datos
        Optional<cliente> clienteOpt = clRep.findByUsername(username);
        
        if (clienteOpt.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        
        cliente clnt = clienteOpt.get();
        
        // Asegúrate de que el rol NO tenga el prefijo "ROLE_"
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + clnt.getRol()));
        
        // Retornar el UserDetails con username, password encriptada y roles
        return new User(
            clnt.getUsername(),
            clnt.getPassword(),
            true, 
            true, 
            true, 
            true, 
            authorities
        );
    }
}
