package com.tienda.mapatienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Necesario para encriptar
import org.springframework.stereotype.Service;

import com.tienda.mapatienda.dto.registroDTO;
import com.tienda.mapatienda.model.cliente;
import com.tienda.mapatienda.repository.clienteRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RegistroService {

    @Autowired
    private clienteRepository clRep;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    public boolean registrarCliente(registroDTO rgDTO) throws Exception {

        // --- VALIDACIONES ---

        // Validar Username
        if (rgDTO.getUsername() == null || rgDTO.getUsername().trim().isEmpty()) {
            throw new Exception("El usuario es requerido");
        }
        if (rgDTO.getUsername().length() < 3) {
            throw new Exception("El usuario debe tener al menos 3 caracteres");
        }

        // Validar Password
        if (rgDTO.getPassword() == null || rgDTO.getPassword().trim().isEmpty()) {
            throw new Exception("La contraseña es requerida");
        }
        if (rgDTO.getPassword().length() < 4) { // Ejemplo de validación mínima
            throw new Exception("La contraseña debe tener al menos 4 caracteres");
        }

        // Validar Apellido
        if(rgDTO.getApellido() == null || rgDTO.getApellido().trim().isEmpty()){
            throw new Exception("El apellido es requerido");
        }
        if (rgDTO.getApellido().length() < 3) {
            throw new Exception("El apellido debe tener al menos 3 caracteres");
        }

        // Validar Teléfono (Corregido: antes validabas apellido aquí)
        if(rgDTO.getTelefono() == null || rgDTO.getTelefono().trim().isEmpty()){
            throw new Exception("El teléfono es requerido");
        }

        // --- VERIFICAR DUPLICADOS ---
        
        // Buscamos si ya existe un cliente con ese username
        Optional<cliente> existe = clRep.findByUsername(rgDTO.getUsername());
        if (existe.isPresent()) {
            throw new Exception("El nombre de usuario ya está en uso");
        }

        // --- GUARDAR CLIENTE ---

        cliente nuevoCliente = new cliente();

        // Pasamos los datos del DTO a la Entidad
        nuevoCliente.setUsername(rgDTO.getUsername());
        nuevoCliente.setNombre(rgDTO.getNombre());
        nuevoCliente.setApellido(rgDTO.getApellido());
        nuevoCliente.setTelefono(rgDTO.getTelefono());
        nuevoCliente.setFechaRegistro(LocalDate.now());
        nuevoCliente.setRol("USER");

        // ENCRIPTAMOS la contraseña antes de guardarla
        String passwordEncriptada = passwordEncoder.encode(rgDTO.getPassword());
        nuevoCliente.setPassword(passwordEncriptada);

        // Guardamos en base de datos
        clRep.save(nuevoCliente);

        return true;
    }
}
