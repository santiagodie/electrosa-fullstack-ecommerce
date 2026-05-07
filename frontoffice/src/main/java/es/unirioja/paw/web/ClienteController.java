/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

/**
 *
 * @author Santiago Die
 */
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.jpa.PedidoanuladoEntity;
import es.unirioja.paw.service.PedidoAnuladoService;
import es.unirioja.paw.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/cliente", "/clientes"})
public class ClienteController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoAnuladoService pedidoAnuladoService;

    @GetMapping("/cuenta")
    public String cuenta(HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("cliente", cliente);
        return "cuenta";
    }

    @GetMapping("/pedidos")
    public String listarPedidos(HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        List<PedidoEntity> pedidos = pedidoService.findByCliente(cliente.getCodigo());
        model.addAttribute("pedidos", pedidos);
        return "lista_pedidos";
    }

    @GetMapping("/pedidos/{codigo}")
    public String detallePedido(@PathVariable String codigo, HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        PedidoEntity pedido = pedidoService.findOne(codigo);
        if (pedido == null || !pedido.getCodigoCliente().equals(cliente.getCodigo())) {
            return "redirect:/cliente/pedidos";
        }
        model.addAttribute("pedido", pedido);
        return "detalle_pedido";
    }

    @GetMapping("/pedidos/anulados")
    public String pedidosAnulados(HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/auth/login";
        }

        List<PedidoanuladoEntity> anulados = pedidoAnuladoService.findAnuladosByCliente(cliente.getCodigo());
        model.addAttribute("pedidosAnulados", anulados);
        return "lista_pedidos_anulados";
    }

    @GetMapping("/pedidos/anulados/{codigo}")
    public String detallePedidoAnulado(@PathVariable("codigo") String codigo, HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/auth/login";
        }

        PedidoanuladoEntity pedido = pedidoAnuladoService.findOne(codigo);
        if (pedido == null || !pedido.getCodigoCliente().equals(cliente.getCodigo())) {
            return "redirect:/cliente/pedidos/anulados";
        }
        BigDecimal importeTotal = pedido.getLineas().stream()
                .filter(l -> l.getArticulo() != null && l.getArticulo().getPvp() != null)
                .map(l -> l.getArticulo().getPvp().multiply(BigDecimal.valueOf(l.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("pedidoanulado", pedido);
        model.addAttribute("importe", importeTotal);
        return "detalle_pedido_anulado";
    }
}
