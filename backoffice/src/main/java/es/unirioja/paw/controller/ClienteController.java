package es.unirioja.paw.controller;

import es.unirioja.paw.dao.ClienteDAO;
import es.unirioja.paw.model.Cliente;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.ClienteDaoMySQL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "ClienteController", urlPatterns = {"/cliente"})
public class ClienteController extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ClienteDAO clienteDAO = new ClienteDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientId = request.getParameter("clientId");
        if (clientId == null || clientId.isBlank()) {
            logger.info("clientId null or blank");
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        Cliente a = null;
        try {
            a = clienteDAO.findOneByCodigo(clientId);
        } catch (ExcepcionDeAplicacion ex) {
            throw new ServletException(ex);
        }

        if (a == null) {
            request.setAttribute("enlaceSalir", "clientes");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "El cliente no existe");
            return;
        }
        request.setAttribute("cliente", a);
        RequestDispatcher rd = request.getRequestDispatcher("client-detail.jsp");
        rd.forward(request, response);
    }

}
