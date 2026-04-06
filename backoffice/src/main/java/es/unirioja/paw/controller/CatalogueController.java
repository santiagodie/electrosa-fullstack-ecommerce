/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.ArticuloDAO;
import es.unirioja.paw.pagination.PageNumberPagination;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.ArticuloDaoMySQL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Santiago Die
 */
@WebServlet(name = "CatalogueController", urlPatterns = {"/catalogue"},
        initParams = {
            @WebInitParam(name = "pageSize", value = "12")})
public class CatalogueController extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private int pageSize;
    private ArticuloDAO catalogoDAO = new ArticuloDaoMySQL();

    @Override
    public void init() throws ServletException {
        super.init();
        String pagina = getInitParameter("pageSize");
        pageSize = Integer.parseInt(pagina);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String p = request.getParameter("p");
        if (p == null || p.isBlank()) {
            logger.info("Parametro p null o blank");
            response.sendRedirect("catalogue?p=1");
            return;
        }
        int actual;
        try {
            actual = Integer.parseInt(p);
        } catch (NumberFormatException e) {
            response.sendRedirect("catalogue?p=1");
            return;
        }

        if (actual < 1) {
            response.sendRedirect("catalogue?p=1");
            return;
        }
        List<Articulo> articulos;
        PageNumberPagination page = null;
        try {
            page = catalogoDAO.buildPageNumberPagination(this.pageSize, actual);
            if (actual > page.getTotalPages()) {
                response.sendRedirect("catalogue?p=" + page.last());
                return;
            }
            articulos = catalogoDAO.findByPage(actual, this.pageSize);
        } catch (ExcepcionDeAplicacion ex) {
            throw new ServletException(ex);
        }
        logger.info("articulos={} articulos", articulos == null ? "null" : articulos.size());
        request.setAttribute("articulos", articulos);
        request.setAttribute("pagination", page);
        request.setAttribute("pageSize", this.pageSize);
        RequestDispatcher rd = request.getRequestDispatcher("article-list.jsp");
        rd.forward(request, response);
    }
}
