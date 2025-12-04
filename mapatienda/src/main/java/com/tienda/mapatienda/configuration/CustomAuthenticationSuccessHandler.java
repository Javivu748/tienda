package com.tienda.mapatienda.configuration;

import com.tienda.mapatienda.model.cliente;
import com.tienda.mapatienda.repository.clienteRepository;
import com.tienda.mapatienda.service.LoginService; // <-- NUEVO IMPORT
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private clienteRepository clRep;

    @Autowired
    private LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        try {
            // 1. Actualiza la fecha de último logueo
            loginService.updateUltimaLogueo(authentication);

            // 2. Obtener el username
            String username = authentication.getName();

            // 3. Buscar el cliente
            Optional<cliente> clienteOpt = clRep.findByUsername(username);

            if (clienteOpt.isPresent()) {
                Long idCliente = clienteOpt.get().getIdCliente();
                String redirectUrl = "/pedidos/" + idCliente;
                response.sendRedirect(redirectUrl);
            } else {
                // Si no se encuentra el cliente, redirigir al login con error
                response.sendRedirect("/login?error=cliente_no_encontrado");
            }
        } catch (Exception e) {
            // En caso de cualquier error, redirigir al login
            response.sendRedirect("/login?error=true");
        }
    }
}