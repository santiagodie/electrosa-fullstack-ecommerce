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
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClienteController {

    @GetMapping({"/cliente/cuenta", "/clientes/cuenta"})
    public String cuenta(HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        model.addAttribute("cliente", cliente);
        return "cuenta";
    }
}
