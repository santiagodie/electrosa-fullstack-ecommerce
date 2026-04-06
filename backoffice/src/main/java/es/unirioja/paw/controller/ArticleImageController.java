/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.CatalogoDAO;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.CatalogoDaoMySQL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Santiago Die
 */
@WebServlet(name = "ArticleImageController", urlPatterns = {"/article_img"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class ArticleImageController extends HttpServlet {

    private static final String ruta_img = "assets/images/store/";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CatalogoDAO articuloDAO = new CatalogoDaoMySQL();

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String articleId = request.getParameter("artId");
    String p = request.getParameter("p");

    if (articleId == null || articleId.isBlank()) {
        logger.info("artId null or blank");
        response.sendRedirect("catalogue?p=1");
        return;
    }

    Articulo a;
    try {
        a = articuloDAO.findOneByCodigo(articleId);
    } catch (ExcepcionDeAplicacion ex) {
        logger.error("Error recuperando artículo: {}", articleId, ex);
        request.setAttribute("enlaceSalir", "catalogue");
        throw new ServletException("Error al buscar el artículo", ex);
    }

    if (a == null) {
        logger.error("Artículo con artId {} no encontrado", articleId);
        request.setAttribute("enlaceSalir", "catalogue");
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "El artículo no existe");
        return;
    }

        request.setAttribute("articulo", a);
        request.setAttribute("page", p);
        RequestDispatcher rd = request.getRequestDispatcher("article-image.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String artId = request.getParameter("artId");
        String p = request.getParameter("p");
        Part archivo = request.getPart("article__imagen");
        String newPhotoPath = null;

        boolean modValida = true;
        StringBuilder errorMessage = new StringBuilder();

        if (artId == null || artId.trim().isEmpty()) {
            modValida = false;
            errorMessage.append(" El código del artículo es obligatorio.");
        } else if (artId.trim().length() > 10) {
            modValida = false;
            errorMessage.append(" El código no puede exceder 10 caracteres (actual: " + artId.trim().length() + ").");
        }

        if (archivo == null || archivo.getSize() <= 0) {
            modValida = false;
            errorMessage.append(" Debe seleccionar una imagen para subir.<br>");
        }

        Articulo articulo;
        try {
            articulo = articuloDAO.findOneByCodigo(artId);
        } catch (ExcepcionDeAplicacion ex) {
            logger.error("Error al buscar el artículo con artId: {}", artId, ex);
            request.setAttribute("enlaceSalir", "catalogue");
            throw new ServletException("Error al buscar el artículo", ex);
        }

        if (articulo == null) {
            logger.error("Artículo con artId {} no encontrado", artId);
            request.setAttribute("enlaceSalir", "catalogue");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "El artículo no existe");
            return;
        }

        if (modValida) {
            try {
                String originalFileName = archivo.getSubmittedFileName();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
                String timestamp = String.valueOf(System.currentTimeMillis());
                String fileName = baseName + "_" + timestamp + extension;

                if (fileName.length() > 50) {
                    throw new ServletException("El nombre de la imagen no puede exceder 50 caracteres (actual: " + fileName.length() + ")");
                }

                String uploadPath = getServletContext().getRealPath("") + File.separator + ruta_img;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                Path filePath = Paths.get(uploadPath + fileName);
                Files.copy(archivo.getInputStream(), filePath);
                newPhotoPath = fileName;

                articulo.setFoto(newPhotoPath);
                articuloDAO.modificarArticulo(articulo);
                request.getSession().setAttribute("successMessage", "Foto cambiada correctamente");
                response.sendRedirect(request.getContextPath() + "/article?artId=" + artId + "&p=" + p);
                return;
            } catch (IOException e) {
                logger.error("Error al guardar la imagen", e);
                response.sendRedirect(request.getContextPath() + "/article?artId=" + artId + "&p=" + p);
                throw new ServletException("Error al subir la imagen", e);
            } catch (ExcepcionDeAplicacion ex) {
                logger.error("Error al guardar el artículo", ex);
                response.sendRedirect(request.getContextPath() + "/article?artId=" + artId + "&p=" + p);
                throw new ServletException("Error al guardar los cambios", ex);
            }
        } else {
            request.setAttribute("errorMessage", errorMessage.toString().replace("\n", "<br>"));
        }

        request.setAttribute("articulo", articulo);
        request.setAttribute("page", p);
        RequestDispatcher rd = request.getRequestDispatcher("article-image.jsp");
        rd.forward(request, response);
    }
}