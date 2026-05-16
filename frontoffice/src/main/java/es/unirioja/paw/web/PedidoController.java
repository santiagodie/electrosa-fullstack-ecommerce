/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

/**
 *
 * @author Santiago Die
 */
import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.service.CestaCompraUseCase;
import es.unirioja.paw.service.SessionConstants;
import es.unirioja.paw.service.data.FinalizarCompraRequest;
import es.unirioja.paw.service.data.FinalizarCompraResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/cliente/pedido", "/clientes/pedido"})
public class PedidoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CestaCompraUseCase cestaCompraUseCase;

    @GetMapping("/formulario")
    public String formulario(HttpSession session, Model model) {
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute(SessionConstants.CESTA_KEY);
        if (cesta == null || cesta.getLineas() == null || cesta.getLineas().isEmpty()) {
            return "redirect:/cliente/cesta";
        }
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.CLIENTE_KEY);
        model.addAttribute("cliente", cliente);
        return "realizar_pedido";
    }

    @PostMapping
    public String confirmar(
            @RequestParam("calle") String calle,
            @RequestParam("cp") String cp,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("provincia") String provincia,
            @RequestParam("fechaEntrega") String fechaEntrega,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute(SessionConstants.CESTA_KEY);
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.CLIENTE_KEY);

        if (cesta == null || cliente == null) {
            return "redirect:/cliente/cesta";
        }

        try {
            LocalDate fecha = LocalDate.parse(fechaEntrega);
            FinalizarCompraRequest request = new FinalizarCompraRequest(
                    cesta, calle, cp, ciudad, provincia, fecha);

            FinalizarCompraResponse response = cestaCompraUseCase.finalizarCompra(request);

            // Limpiar cesta de sesión
            session.setAttribute(SessionConstants.CESTA_KEY, null);

            redirectAttributes.addFlashAttribute("codigoPedido", response.pedido.getCodigo());
            return "redirect:/cliente/pedido/confirmacion";

        } catch (Exception e) {
            logger.error("Error al finalizar compra: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al procesar el pedido. Inténtalo de nuevo.");
            return "redirect:/cliente/pedido/formulario";
        }
    }

    @GetMapping("/confirmacion")
    public String confirmacion(Model model) {
        return "pedido_confirmacion";
    }
}
