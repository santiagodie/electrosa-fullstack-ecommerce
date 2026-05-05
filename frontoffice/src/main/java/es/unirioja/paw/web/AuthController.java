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
import es.unirioja.paw.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(Model model, HttpServletRequest request) {
        String usuario = request.getParameter("login_username");
        String contrasena = request.getParameter("login_password");

        boolean hayErrores = false;

        if (usuario == null || usuario.isBlank()) {
            model.addAttribute("errorUsername", "El nombre de usuario no puede estar vacío");
            hayErrores = true;
        } else {
            model.addAttribute("username", usuario);
        }

        if (contrasena == null || contrasena.isBlank()) {
            model.addAttribute("errorPassword", "La contraseña no puede estar vacía");
            hayErrores = true;
        }

        if (hayErrores) {
            return "login";
        }

        ClienteEntity cliente = authService.authenticate(usuario, contrasena);

        if (cliente == null) {
            model.addAttribute("errorMensaje", "Usuario o contraseña incorrectos");
            model.addAttribute("username", usuario);
            return "login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("cliente", cliente);

        String retorno = (String) session.getAttribute("retorno");
        session.removeAttribute("retorno");

        String destinoFinal = (retorno != null && !retorno.isBlank()) ? retorno : "/cliente/cuenta";
        return "redirect:" + destinoFinal;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
