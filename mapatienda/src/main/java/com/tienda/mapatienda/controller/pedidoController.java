package com.tienda.mapatienda.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienda.mapatienda.model.cliente;
import com.tienda.mapatienda.model.pedido;
import com.tienda.mapatienda.repository.clienteRepository;
import com.tienda.mapatienda.repository.pedidosRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
@RequestMapping("/pedidos")
public class pedidoController {

    @Autowired
    private pedidosRepository pdRep;

    @Autowired
    private clienteRepository clRep;

    @GetMapping("/{id}")
    public String verPedidosCliente(@PathVariable Long id, Model model) {
        Optional<cliente> cliente = clRep.findById(id);

        if (cliente.isPresent()) {
            List<pedido> pedidos = pdRep.findByClienteIdCliente(id);
            model.addAttribute("cliente", cliente.get());
            model.addAttribute("pedidos", pedidos);
            return "pedido/pedidosPorCliente";
        } else {
            return "redirect:/clientes";
        }
    }

    @PostMapping("/{id}/crear")
    public String crearPedido(@PathVariable Long id,@ModelAttribute("pedido") pedido pd, Model model) {
        Optional<cliente> clntop = clRep.findById(id);


        if (clntop.isEmpty()) {
            model.addAttribute("error", "no se ha encontrado cliente");
            return "redirect:/pedidos/"+ pd.getCliente().getIdCliente();
        }

        cliente nClnt = clntop.get();
        pd.setCliente(nClnt);
        pdRep.save(pd);
        
        return "redirect:/pedidos/"+ pd.getCliente().getIdCliente() ;
    }

    @GetMapping("/{id}/nuevo")
    public String nuevoPedido(@PathVariable Long id, Model model) {
        Optional<cliente> clnt = clRep.findById(id);
        
        
        if (clnt.isEmpty()) {
            model.addAttribute("error", "El cliente no existe");
            return "redirect:/clientes";
        }

        cliente clnt2 = clnt.get();
        pedido pd = new pedido();
        model.addAttribute("cliente", clnt2);
        model.addAttribute("pedido", pd);
        return "pedido/nuevoPedido";
    }

    @GetMapping("/{id}/eliminar/{idPedido}")
    public String eliminarAhorro(@PathVariable Long id, @PathVariable Long idPedido, RedirectAttributes redAttrib) {
        if (!pdRep.existsById(idPedido)) {
            redAttrib.addFlashAttribute("error", "El pedido no existe");
        } else {     
            pdRep.deleteById(idPedido);
            redAttrib.addFlashAttribute("success", "El pedido se ha borrado correctamente.");
        }
        if (clRep.existsById(id)) {
            return "redirect:/pedidos/" + id;
        }
        return "redirect:/pedidos/" + id;
    }

    @GetMapping("/editar/{id}")
    public String editarPedido(@PathVariable Long id, Model model) {
        Optional<pedido> estePedido = pdRep.findById(id);

        if (estePedido.isEmpty()) {
            model.addAttribute("error", "El pedido no existe");
            return "redirect:/pedidos";
        }

        pedido pd = estePedido.get();
        cliente clnt = pd.getCliente();

        model.addAttribute("cliente", clnt);
        model.addAttribute("pedido", pd);

        return "pedido/editarPedido";
    }

    @PostMapping("/modificar")
    public String modificarPedido(@ModelAttribute("pedido") pedido pd, Model model) {
        if (pd.getIdPedido() == null || !pdRep.existsById(pd.getIdPedido())) {
            model.addAttribute("error", "El pedido no existe");
            return "redirect:/pedidos";
        }

        try {
            pdRep.save(pd);
            Long idCliente = pd.getCliente() != null ? pd.getCliente().getIdCliente() : null;
            
            if (idCliente != null) {
                return "redirect:/pedidos/" + idCliente;
            } else {
                return "redirect:/pedidos/" + idCliente;
            }
        } catch (Exception e) {
            model.addAttribute("error", "El pedido no se ha podido guardar");
            return "pedido/editarPedido";
        }
    }
    

    

}
