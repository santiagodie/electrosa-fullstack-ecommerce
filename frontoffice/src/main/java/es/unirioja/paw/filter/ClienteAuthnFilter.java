/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.filter;

/**
 *
 * @author Santiago Die
 */
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/cliente/*", "/clientes/*"})
public class ClienteAuthnFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession(false);

        boolean autenticado = (session != null && session.getAttribute("cliente") != null);

        if (autenticado) {
            chain.doFilter(request, response);
        } else {
            String destino = httpReq.getRequestURI().substring(httpReq.getContextPath().length());
            String query = httpReq.getQueryString();
            if (query != null) {
                destino += "?" + query;
            }
            session = httpReq.getSession(true);
            session.setAttribute("retorno", destino);
            httpResp.sendRedirect(httpReq.getContextPath() + "/auth/login");
        }
    }
}
