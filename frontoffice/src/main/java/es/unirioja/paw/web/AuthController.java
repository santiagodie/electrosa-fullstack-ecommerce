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
import es.unirioja.paw.repository.UsuarioRepository;
import es.unirioja.paw.service.AuthService;
import es.unirioja.paw.service.PedidoService;
import es.unirioja.paw.service.RegistroClienteUseCase;
import es.unirioja.paw.service.SessionConstants;
import es.unirioja.paw.service.data.RegistroClienteRequest;
import es.unirioja.paw.service.data.RegistroClienteResponse;
import es.unirioja.paw.web.data.ClienteAvatarInfo;
import es.unirioja.paw.web.data.RegistroClienteValidationResponse;
import es.unirioja.paw.web.data.RegistroPostPayload;
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

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private RegistroClienteUseCase registroClienteUseCase;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        session.setAttribute(SessionConstants.CLIENTE_KEY, cliente);

        // Cargar la cesta del cliente en sesión
        CestaCompraEntity cestaEntity = pedidoService.findCestaCliente(cliente.getCodigo());
        session.setAttribute(SessionConstants.CESTA_KEY, cestaEntity);
        session.setAttribute("clienteAvatar", new ClienteAvatarInfo(cliente));
        String retorno = (String) session.getAttribute(SessionConstants.RETORNO_KEY);
        session.removeAttribute(SessionConstants.RETORNO_KEY);

        String destinoFinal = (retorno != null && !retorno.isBlank()) ? retorno : "/cliente/cuenta";
        return "redirect:" + destinoFinal;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registro(Model model) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registroSubmit(@ModelAttribute RegistroPostPayload modelAttribute,HttpSession session, Model model) {

        RegistroClienteValidationResponse validationResponse = validateRegistroPayload(modelAttribute);
        if (!validationResponse.isSuccess()){
            model.addAttribute("messages", validationResponse.getMessageCollection());
            model.addAttribute("backing", modelAttribute);
            return "auth/register";
        }
        RegistroClienteResponse registroClienteResponse = registroClienteUseCase.registrar(new RegistroClienteRequest(modelAttribute));

        if (registroClienteResponse.isSuccess()) {
            session.setAttribute(SessionConstants.USER_REGISTERED_KEY, registroClienteResponse.cliente);
            return "redirect:/auth/welcome";
        }
        model.addAttribute("backing", modelAttribute);
        return "auth/register";
    }

    @GetMapping("/welcome")
    public String welcome(Model model, HttpSession session) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(SessionConstants.USER_REGISTERED_KEY);
        if (cliente != null) {
            model.addAttribute("clienteRegistrado", cliente);
            session.removeAttribute(SessionConstants.USER_REGISTERED_KEY);
        }
        return "auth/welcome";
    }

    private RegistroClienteValidationResponse validateRegistroPayload(RegistroPostPayload p) {
        RegistroClienteValidationResponse r = new RegistroClienteValidationResponse();
        r.setSuccess(true);

        if (p.getEmail() == null || p.getEmail().isBlank()) {
            r.getMessageCollection().add("Campo 'Email': debe proporcionar un valor");
            r.setSuccess(false);
        }
        if (p.getUsername() == null || p.getUsername().isBlank()) {
            r.getMessageCollection().add("Campo 'Nombre de usuario': debe proporcionar un valor");
            r.setSuccess(false);
        }
        if (p.getPassword1() == null || p.getPassword1().isBlank()) {
            r.getMessageCollection().add("Campo 'Contraseña': debe proporcionar un valor");
            r.setSuccess(false);
        }
        if (p.getPassword2() == null || p.getPassword2().isBlank()) {
            r.getMessageCollection().add("Campo 'Repita contraseña': debe proporcionar un valor");
            r.setSuccess(false);
        }

        if (p.getEmail() != null && p.getEmail().length() > 100) {
            r.getMessageCollection().add("Campo 'Email': demasiado largo (máximo 100 caracteres)");
            r.setSuccess(false);
        }
        if (p.getUsername() != null && p.getUsername().length() > 50) {
            r.getMessageCollection().add("Campo 'Nombre de usuario': demasiado largo (máximo 50 caracteres)");
            r.setSuccess(false);
        }

        if (p.getPassword1() != null && !p.getPassword1().isBlank()&& p.getPassword2() != null && !p.getPassword2().isBlank()&& !p.getPassword1().equals(p.getPassword2())) {
            r.getMessageCollection().add("Las contraseñas no coinciden");
            r.setSuccess(false);
        }

        if (p.getUsername() != null && !p.getUsername().isBlank()&& usuarioRepository.existsById(p.getUsername())) {
            r.getMessageCollection().add("El nombre de usuario '" + p.getUsername() + "' ya está en uso");
            r.setSuccess(false);
        }
        return r;
    }
}
