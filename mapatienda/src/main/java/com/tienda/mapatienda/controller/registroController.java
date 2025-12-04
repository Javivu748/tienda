package com.tienda.mapatienda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienda.mapatienda.dto.registroDTO;
import com.tienda.mapatienda.service.RegistroService;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("/registro")
public class registroController {
    
    //creamos el logger
    Logger logger = LoggerFactory.getLogger(clienteController.class);

    @Autowired
    private RegistroService rgService;

    @GetMapping
    public String getRegistro(Model model) {
        model.addAttribute("RegistroDTO", new registroDTO());
        return "registro";
    }

    @PostMapping
    public String registrarse(registroDTO rgDTO,Model model) {
        
        try {
            rgService.registrarCliente(rgDTO);
            model.addAttribute("mensaje", "Registro exitoso. Por favor inicia sesión.");
            logger.info("El usuario se ha registrado correctamente");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            logger.error("Error al registrarse porfavor intentelo mas tarde");
            return "registro";
        }

    }
    

    


}
