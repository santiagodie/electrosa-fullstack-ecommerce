/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.CatalogoDAO;
import es.unirioja.paw.dao.FabricanteDAO;
import es.unirioja.paw.dao.TipoArticuloDAO;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Fabricante;
import es.unirioja.paw.model.TipoArticulo;
import es.unirioja.paw.mysql.CatalogoDaoMySQL;
import es.unirioja.paw.mysql.FabricanteDaoMySQL;
import es.unirioja.paw.mysql.TipoArticuloDaoMySQL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author sadie
 */
@WebServlet(name = "ArticleModificarController", urlPatterns = {"/article_mod"})
public class ArticleModificarController extends HttpServlet {

    private static final String ruta_img = "assets/images/store/";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CatalogoDAO articuloDAO = new CatalogoDaoMySQL();
    private TipoArticuloDAO tiposdao = new TipoArticuloDaoMySQL();
    private FabricanteDAO fabricantedao = new FabricanteDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String articleId = request.getParameter("artId");
        String pagina = request.getParameter("p");

        if (articleId == null || articleId.isBlank()) {
            logger.info("artId null or blank");
            response.sendRedirect(request.getContextPath() + "/catalogue?p=1");
            return;
        }

        Articulo a;
        try {
            a = articuloDAO.findOneByCodigo(articleId);
        } catch (ExcepcionDeAplicacion ex) {
            logger.error("Error recuperando artículo: {}", articleId, ex);
            request.setAttribute("enlaceSalir", "catalogue?p=1");
            throw new ServletException("Error al buscar el artículo", ex);
        }

        if (a == null) {
            logger.error("Artículo con artId {} no encontrado", articleId);
            request.setAttribute("enlaceSalir", "catalogue?p=1");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "El artículo no existe");
            return;
        }

        List<TipoArticulo> tipos;
        List<Fabricante> fabricantes;
        try {
            tipos = tiposdao.findall();
            fabricantes = fabricantedao.findall();
        } catch (ExcepcionDeAplicacion ex) {
            logger.error("Error al cargar tipos o fabricantes", ex);
            request.setAttribute("enlaceSalir", "catalogue?p=1");
            throw new ServletException("Error al cargar datos adicionales", ex);
        }

        request.setAttribute("articulo", a);
        request.setAttribute("tipos", tipos);
        request.setAttribute("fabricantes", fabricantes);
        request.setAttribute("page", pagina);
        RequestDispatcher rd = request.getRequestDispatcher("article-change.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String artId = request.getParameter("artId");
        String nombre = request.getParameter("article_nom");
        String descripcion = request.getParameter("article_desp");
        String tipo = request.getParameter("article-tipo");
        String fabricante = request.getParameter("article-fab");
        String PVP = request.getParameter("article_pvp");
        String pagina = request.getParameter("p");

        logger.info("Received parameters: artId={}, nombre={}, descripcion={}, tipo={}, fabricante={}, PVP={}, pagina={}",
                artId, nombre, descripcion, tipo, fabricante, PVP, pagina);

        boolean modValida = true;
        StringBuilder errorMessage = new StringBuilder();

        if (nombre == null || nombre.trim().isEmpty()) {
            modValida = false;
            errorMessage.append(" El nombre es obligatorio.");
        } else if (nombre.trim().length() > 50) {
            modValida = false;
            errorMessage.append(" El nombre no puede exceder 50 caracteres (actual: " + nombre.trim().length() + ").");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            modValida = false;
            errorMessage.append(" La descripción es obligatoria.");
        } else {
            int tamaño_desp = descripcion.trim().length();
            if (tamaño_desp < 90 || tamaño_desp > 200) {
                modValida = false;
                errorMessage.append(" La descripción debe tener entre 90 y 200 caracteres (actual: " + tamaño_desp + ").");
            }
        }
        if (PVP == null || PVP.trim().isEmpty()) {
            modValida = false;
            errorMessage.append(" El precio es obligatorio.");
        } else {
            try {
                String pvpStr = PVP.trim();
                double pvp = Double.parseDouble(pvpStr);
                if (pvp <= 0) {
                    modValida = false;
                    errorMessage.append("El precio debe ser mayor que 0.<br>");
                }
                String[] parts = pvpStr.split("\\.");
                if (parts.length > 1) {
                    int integerPartLength = parts[0].length();
                    int decimalPartLength = parts[1].length();
                    if (integerPartLength > 8 || decimalPartLength > 2) {
                        modValida = false;
                        errorMessage.append("El precio debe tener máximo 8 dígitos antes del decimal y 2 después.<br>");
                    }
                } else if (pvpStr.length() > 10) {
                    modValida = false;
                    errorMessage.append("El precio debe tener máximo 10 dígitos.<br>");
                }
            } catch (NumberFormatException e) {
                modValida = false;
                errorMessage.append("El precio debe ser un número válido.<br>");
            }
        }

        Articulo articulo;
        try {
            articulo = articuloDAO.findOneByCodigo(artId);
        } catch (ExcepcionDeAplicacion ex) {
            logger.error("Error al buscar el artículo con artId: {}", artId, ex);
            request.setAttribute("enlaceSalir", "catalogue?p=1");
            throw new ServletException("Error al buscar el artículo", ex);
        }

        if (articulo == null) {
            logger.error("Artículo con artId {} no encontrado", artId);
            request.setAttribute("enlaceSalir", "catalogue?p=1");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "El artículo no existe");
            return;
        }

        if (modValida) {
            try {
                articulo.setNombre(nombre.trim());
                articulo.setDescripcion(descripcion.trim());
                articulo.setTipo(tipo);
                articulo.setFabricante(fabricante);
                articulo.setPvp(Double.parseDouble(PVP.trim()));
                articuloDAO.modificarArticulo(articulo);
                request.getSession().setAttribute("successMessage", "Cambios guardados exitosamente");
                response.sendRedirect(request.getContextPath() + "/article?artId=" + artId + "&p=" + pagina);
                return;
            } catch (NumberFormatException e) {
                logger.error("PVP no es un número válido: {}", PVP, e);
                request.setAttribute("errorMessage", "El precio debe ser un número válido.");
            } catch (ExcepcionDeAplicacion ex) {
                logger.error("Error al guardar el artículo", ex);
                request.setAttribute("enlaceSalir", "/article_mod?artId=" + artId + "&p=" + pagina);
                throw new ServletException("Error al guardar los cambios", ex);
            }
        } else {
            request.setAttribute("errorMessage", errorMessage.toString().replace("\n", "<br>"));
        }

        List<TipoArticulo> tipos;
        List<Fabricante> fabricantes;
        try {
            tipos = tiposdao.findall();
            fabricantes = fabricantedao.findall();
        } catch (ExcepcionDeAplicacion ex) {
            logger.error("Error al cargar tipos o fabricantes", ex);
            request.setAttribute("enlaceSalir", "catalogue?p=1");
            throw new ServletException("Error al cargar datos adicionales", ex);
        }

        request.setAttribute("articulo", articulo);
        request.setAttribute("tipos", tipos);
        request.setAttribute("fabricantes", fabricantes);
        request.setAttribute("page", pagina);

        RequestDispatcher rd = request.getRequestDispatcher("article-change.jsp");
        rd.forward(request, response);
    }
}
