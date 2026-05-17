/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

/**
 *
 * @author Santiago Die
 */

import es.unirioja.paw.exception.AccessNotAuthorizedException;
import es.unirioja.paw.exception.PedidoNotFoundException;
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.jpa.PedidoanuladoEntity;
import es.unirioja.paw.service.AnularPedidoUseCase;
import es.unirioja.paw.service.AvatarClienteChangeUseCase;
import es.unirioja.paw.service.PedidoAnuladoService;
import es.unirioja.paw.service.PedidoService;
import es.unirioja.paw.service.SessionConstants;
import es.unirioja.paw.service.data.AnularPedidoRequest;
import es.unirioja.paw.service.data.AvatarClienteChangeRequest;
import es.unirioja.paw.service.data.AvatarClienteChangeResponse;
import es.unirioja.paw.web.data.ClienteAvatarInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/cliente", "/clientes"})
public class ClienteController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoAnuladoService pedidoAnuladoService;
    @Autowired
    private AnularPedidoUseCase anularPedidoUseCase;
    @Autowired
    private AvatarClienteChangeUseCase avatarClienteChangeUseCase;

    // Cuenta
    @GetMapping("/cuenta")
    public String cuenta(HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.CLIENTE_KEY);
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        ClienteAvatarInfo avatar = (ClienteAvatarInfo) session.getAttribute("clienteAvatar");
        if (avatar == null) {
            avatar = new ClienteAvatarInfo(cliente);
            session.setAttribute("clienteAvatar", avatar);
        }
        model.addAttribute("cliente", cliente);
        return "cuenta";
    }

    @PostMapping("/cuenta")
    public String uploadAvatar(HttpSession session, HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.CLIENTE_KEY);
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        String realTargetPath = request.getServletContext().getRealPath("/resources/static/assets/avatar");
        Optional<AvatarClienteChangeResponse> response = avatarClienteChangeUseCase.execute(new AvatarClienteChangeRequest(cliente.getCodigo(), file, realTargetPath));
        if (response.isPresent()) {
            session.setAttribute("clienteAvatar", new ClienteAvatarInfo(response.get().cliente));
        }
        return "redirect:/cliente/cuenta";
    }

    // Pedidos
    @GetMapping("/pedidos")
    public String listarPedidos(HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.CLIENTE_KEY);
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        List<PedidoEntity> pedidos = pedidoService.findByCliente(cliente.getCodigo());
        model.addAttribute("pedidos", pedidos);
        return "lista_pedidos";
    }

    @GetMapping("/pedidos/{codigo}")
    public String detallePedido(@PathVariable String codigo, HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.CLIENTE_KEY);
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        PedidoEntity pedido = pedidoService.findOne(codigo);
        if (pedido != null && !pedido.getCodigoCliente().equals(cliente.getCodigo())) {
            throw new AccessNotAuthorizedException("No está autorizado para consultar este pedido");
        }
        if (pedido == null) {
            throw new PedidoNotFoundException("Pedido " + codigo + " no encontrado");
        }
        model.addAttribute("pedido", pedido);
        return "detalle_pedido";
    }

    // Pedidos anulados
    @GetMapping("/pedidos/anulados")
    public String pedidosAnulados(HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.CLIENTE_KEY);
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        List<PedidoanuladoEntity> anulados = pedidoAnuladoService.findAnuladosByCliente(cliente.getCodigo());
        model.addAttribute("pedidosAnulados", anulados);
        return "lista_pedidos_anulados";
    }

    @GetMapping("/pedidos/anulados/{codigo}")
    public String detallePedidoAnulado(@PathVariable("codigo") String codigo, HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.CLIENTE_KEY);
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        PedidoanuladoEntity pedido = pedidoAnuladoService.findOne(codigo);
        if (pedido == null) {
            throw new PedidoNotFoundException("Pedido " + codigo + " no encontrado");
        }
        if (!pedido.getCodigoCliente().equals(cliente.getCodigo())) {
            throw new AccessNotAuthorizedException("No está autorizado para consultar este pedido");
        }
        BigDecimal importeTotal = pedido.getLineas().stream()
                .filter(l -> l.getArticulo() != null && l.getArticulo().getPvp() != null)
                .map(l -> l.getArticulo().getPvp().multiply(BigDecimal.valueOf(l.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("pedidoanulado", pedido);
        model.addAttribute("importe", importeTotal);
        return "detalle_pedido_anulado";
    }

    @PostMapping("/pedidos/anular")
    public String anularPedido(@RequestParam("codigoPedido") String codigoPedido, HttpSession session, RedirectAttributes redirectAttributes) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.CLIENTE_KEY);
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        try {
            anularPedidoUseCase.execute(new AnularPedidoRequest(codigoPedido));
            redirectAttributes.addFlashAttribute("success", "Pedido anulado correctamente.");
            return "redirect:/cliente/pedidos/anulados";
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cliente/pedidos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error inesperado al anular el pedido.");
            return "redirect:/cliente/pedidos";
        }
    }
}
