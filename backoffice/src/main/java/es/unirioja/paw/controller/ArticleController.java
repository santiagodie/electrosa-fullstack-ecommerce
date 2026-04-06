/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.ArticuloDAO;
import es.unirioja.paw.dao.CatalogoDAO;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.ArticuloDaoMySQL;
import es.unirioja.paw.mysql.CatalogoDaoMySQL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Santiago Die
 */
@WebServlet(name = "ArticleController", urlPatterns = {"/article"})
public class ArticleController extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CatalogoDAO articuloDAO = new CatalogoDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String artId = request.getParameter("artId");
        String p = request.getParameter("p");

        if (artId == null || artId.isBlank()) {
            logger.info("artId null or blank");
            response.sendRedirect("catalogue?p=1");
            return;
        }

        Articulo a;
        try {
            a = articuloDAO.findOneByCodigo(artId);
        } catch (ExcepcionDeAplicacion ex) {
            logger.error("Error recuperando artículo: {}", artId, ex);
            request.setAttribute("enlaceSalir", "catalogue");
            throw new ServletException("Error al buscar el artículo", ex);
        }

        if (a == null) {
            logger.error("Artículo con artId {} no encontrado", artId);
            request.setAttribute("enlaceSalir", "catalogue");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "El artículo no existe");
            return;
        }

        request.setAttribute("articulo", a);
        request.setAttribute("p", p);

        RequestDispatcher rd = request.getRequestDispatcher("article-detail.jsp");
        rd.forward(request, response);
    }
}
