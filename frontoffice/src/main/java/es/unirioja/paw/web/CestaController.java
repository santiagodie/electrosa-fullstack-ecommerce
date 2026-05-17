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
import es.unirioja.paw.service.PedidoService;
import es.unirioja.paw.service.SessionConstants;
import es.unirioja.paw.service.data.AddToCartRequest;
import es.unirioja.paw.service.data.AddToCartResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/cliente/cesta", "/clientes/cesta"})
public class CestaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CestaCompraUseCase cestaCompraUseCase;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public String verCesta(HttpSession session, Model model) {
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");

        // Calcular total
        BigDecimal total = BigDecimal.ZERO;
        if (cesta != null && cesta.getLineas() != null) {
            for (var linea : cesta.getLineas()) {
                if (linea.getPrecio() != null) {
                    total = total.add(linea.getPrecio().multiply(BigDecimal.valueOf(linea.getCantidad())));
                }
            }
        }

        model.addAttribute("cesta", cesta);
        model.addAttribute("total", total);
        return "cesta";
    }

    @PostMapping
    public String addToCart(@RequestParam("codigoArticulo") String codigoArticulo, HttpSession session) {
        CestaCompraEntity cesta = cestaFromSession(session);
        Optional<AddToCartResponse> response = cestaCompraUseCase.add(new AddToCartRequest(codigoArticulo, cesta));

        if (response.isPresent()) {
            session.setAttribute("cesta", response.get().cesta);
            logger.info("Cesta actualizada en sesión");
        }

        return "redirect:/cliente/cesta";
    }

    private CestaCompraEntity cestaFromSession(HttpSession session) {
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");

        if (cliente == null) {
            logger.warn("No hay cliente en sesión");
            return null;
        }
        if (cesta == null) {
            logger.warn("Cliente {}: se crea cesta nueva", cliente.getCodigo());
            cesta = new CestaCompraEntity();
            cesta.setCodigoCliente(cliente.getCodigo());
            cesta.setFechaInicio(Timestamp.valueOf(LocalDateTime.now()));
            cesta.setLineas(new ArrayList<>());
        }
        return cesta;
    }

    @GetMapping("/vaciar")
    public String vaciarCesta(HttpSession session) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.CLIENTE_KEY);
        if (cliente != null) {
            pedidoService.deleteCestaCliente(cliente.getCodigo());
            session.setAttribute(SessionConstants.CESTA_KEY, null);
        }
        return "redirect:/cliente/cesta";
    }
}
