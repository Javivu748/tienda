package com.tienda.mapatienda.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.mapatienda.model.cliente;
import com.tienda.mapatienda.model.pedido;
import com.tienda.mapatienda.repository.clienteRepository;
import com.tienda.mapatienda.repository.pedidosRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/clientes")
public class clienteController {

    //creamos el logger
    Logger logger = LoggerFactory.getLogger(clienteController.class);

    @Autowired
    private clienteRepository clRep;

    @Autowired
    private pedidosRepository pdRep;

    @GetMapping
    public String listaClientes(Model model) {

        List<cliente> listaClientes = clRep.findAll();

        model.addAttribute("clientes", listaClientes);
        logger.info("Lista de clientes cargada");

        return "cliente/listaClientes";

    }

    @GetMapping("/filtrarCliente")
    public String buscarPorNombreYApellido(
            Model model,
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "apellido", required = false) String apellido,
            @RequestParam(name = "telefono", required = false) String telefono) {

        List<cliente> listaClientes;

        // Si todos los parámetros son nulos o vacíos, mostramos todos los clientes
        if ((nombre == null || nombre.isEmpty()) &&
                (apellido == null || apellido.isEmpty()) &&
                (telefono == null || telefono.isEmpty())) {
                    logger.error("no se ha encontrado los parametros");
            listaClientes = clRep.findAll();
        } else {
            // Ejecutamos el filtro combinado (asumiendo que existe el método en clRep)
            // Nota: Los valores nulos o vacíos se manejarán como una búsqueda por defecto
            // (que no filtra)
            listaClientes = clRep.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrTelefonoContaining(
                    nombre, apellido, telefono);
        }

        // Cargamos los parámetros al modelo para que los inputs no se borren en el HTML
        model.addAttribute("nombreParam", nombre);
        model.addAttribute("apellidoParam", apellido);
        model.addAttribute("telefonoParam", telefono);

        model.addAttribute("clientes", listaClientes);

        return "cliente/listaClientes";
    }

    @GetMapping("/detalles/{id}")
    public String detallesCliente(@PathVariable Long id, Model model) {
        cliente cltn = new cliente();
        if (!clRep.existsById(id)) {
            model.addAttribute("error", "El Cliente no existe");
            logger.error("Error al cargar los detalles");
            return "redirect:/clientes";
        } else {
            cltn = clRep.findById(id).get();
            logger.info("se ha cargado correctamente");
        }
        List<pedido> pdn = pdRep.findByClienteIdCliente(id);
        model.addAttribute("cliente", cltn);
        model.addAttribute("pedidos", pdn);

        return "cliente/detalleCliente";
    }

    @GetMapping("/nuevo")
    public String nuevoCliente(Model model) {
        cliente cln = new cliente();

        model.addAttribute("cliente", cln);

        return "cliente/nuevoCliente";
    }

    @PostMapping("/crear")
    public String crearCliente(@ModelAttribute cliente nCliente) {
        // TODO: process POST request
        // Guardamos el nuevo cliente
        clRep.save(nCliente);

        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editCliente(@PathVariable Long id, Model model) {

        // Primero creo una ciudad en blanco
        cliente clint = new cliente();

        // Si no existe la ciudad con id en la bd devuelo el error
        if (!clRep.existsById(id))
            model.addAttribute("error", "El cliente no Existe");
        // Si existe la ciudad en bd la cargamos
        else
            clint = clRep.findById(id).get();
        // Cargamos la ciudad en el model y cargamos la vista
        model.addAttribute("cliente", clint);
        // TODO la fecha no la coge bien el type=date de html5 al estar guardada
        // en formato americano en bd y intentar mostrarse en un navegador con formato
        // español
        // todo:solucionarlo
        return "cliente/editarCliente";
    }

    @PostMapping("/modificar")
    public String modificarCliente(@ModelAttribute("cliente") cliente clnt, Model model) {
        // Verificación de existencia del Cliente
        // Usamos el ID del objeto que se intenta modificar.
        if (!clRep.existsById(clnt.getIdCliente())) {
            model.addAttribute("error", "El cliente no existe.");
            // Si no existe, lo mejor es redirigir a la lista con un error visible.
            return "redirect:/clientes?error=Cliente_No_Encontrado";
        }

        try {
            // Guardar (Actualizar) el Cliente
            clRep.save(clnt);
            Long clienteId = clnt.getIdCliente();
            // Redireccionamiento Exitoso
            return "redirect:/clientes/detalles/" + clienteId;
            // Si quieres ir a la lista de clientes con un mensaje de éxito:
            // return "redirect:/clientes?exito=Cliente_Modificado";

        } catch (Exception e) {
            // Manejo de Errores al Guardar
            model.addAttribute("error", "El cliente no se ha podido guardar debido a un error: " + e.getMessage());
            return "cliente/editarCliente";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String removeCliente(@PathVariable Long id, RedirectAttributes redAttrib) {

        if (!clRep.existsById(id))
            redAttrib.addFlashAttribute("error", "El cliente no Existe");
        else {
            clRep.deleteById(id);
            redAttrib.addFlashAttribute("success", "Se ha borrado Correctamente El cliente con id " + id);
        }

        return "redirect:/clientes";
    }

}
