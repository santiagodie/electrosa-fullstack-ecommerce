/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.UsuarioDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Usuario;
import es.unirioja.paw.mysql.UsuarioDaoMySQL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sadie
 */
@WebServlet(name = "LoginController", urlPatterns = {"/auth/login"})
public class LoginController extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UsuarioDAO usuariodao = new UsuarioDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("login_username");
        String password = request.getParameter("login_password");
        boolean valido = true;

        logger.info("Received parameters: username={}", username);

        StringBuilder errorMessage = new StringBuilder();
        StringBuilder errorPassword = new StringBuilder();
        StringBuilder errorUsername = new StringBuilder();

        if (username == null || username.trim().isEmpty()) {
            valido = false;
            errorUsername.append("Please enter your username");
        }
        if (password == null || password.trim().isEmpty()) {
            valido = false;
            errorPassword.append(" Please enter your password.");
        }
        if (errorPassword.length() > 0 || errorUsername.length() > 0) {
            request.setAttribute("errorPassword", errorPassword.toString());
            request.setAttribute("errorUsername", errorUsername.toString());
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
            return;
        }

        if (valido) {
            String providedHash = encodeValue(password, "SHA-1");
            Usuario user;
            try {
                user = usuariodao.findOneByUsername(username, providedHash);
            } catch (ExcepcionDeAplicacion ex) {
                logger.error("Error al buscar el usuario", username, ex);
                response.sendRedirect("/backoffice/auth/login");
                return;
            }

            if (user == null) {
                logger.error("Artículo con username {} no encontrado", username);
                errorMessage.append("Your password is incorrect or this account doesn’t exist. Please check and try again");
            } else try {
                if (!usuariodao.esAdmin(username)) {
                    errorMessage.append("User does not have administrative privileges.");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", new Usuario(username));
                String nextUrl = request.getParameter("next");
                if (nextUrl != null && !nextUrl.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + nextUrl);
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
                    return;
                }
            } catch (ExcepcionDeAplicacion ex) {
                java.util.logging.Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (errorMessage.length() > 0) {
                request.setAttribute("errorMessage", errorMessage.toString());
                request.setAttribute("username", username);
                request.setAttribute("next", request.getParameter("next"));
                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                rd.forward(request, response);
            }
        }
    }

    private String encodeValue(String value, String algorithm) {
        byte[] valueBytes = value.getBytes();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (Exception ex) {
            logger.error("Error getting MessageDigest instance", ex);
            return value;
        }
        messageDigest.reset();
        messageDigest.update(valueBytes);
        byte[] hashBytes = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (int b = 0; b < hashBytes.length; b++) {
            if ((hashBytes[b] & 0xFF) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toString((hashBytes[b] & 0xFF), 16));
        }
        return stringBuffer.toString();
    }

}
