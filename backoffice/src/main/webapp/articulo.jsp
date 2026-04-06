<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="es.unirioja.paw.model.Articulo" %>
<%@page import="java.util.List"%>
<%@page import="es.unirioja.paw.mysql.CatalogoDaoMySQL" %>
<%@page import="es.unirioja.paw.dao.CatalogoDAO" %>
<%@page import="es.unirioja.paw.model.ExcepcionDeAplicacion" %>
<!DOCTYPE html>
<html lang="es">
<%
    String codigo = request.getParameter("codigo");
    Articulo articulo = null;
    CatalogoDAO dao = new CatalogoDaoMySQL();
    if (codigo != null && !codigo.isEmpty()) {
        try {
            articulo = dao.findOneByCodigo(codigo);
        } catch (ExcepcionDeAplicacion e) {
            out.println("<p>Error al recuperar el artículo: " + e.getMessage() + "</p>");
        }
    }
    String titulo = (articulo != null) ? "Ficha de " + articulo.getCodigo() : "Ficha de producto";
    // Estas variables son para que en cada pagina pese a usar todas los partials sea mas "personalizada"
    String VAR = titulo;
    String CSS = "articulo";
%>
<%@ include file='/partials/__head.jsp' %>

<body>
    <%@ include file='/partials/__body_top.jsp' %>

    <main class="cd-main-content main">
        <h2><%= (articulo != null) ? "Ficha de : " + articulo.getNombre(): "Articulo no encontrado" %></h2>
        <a href="/backoffice/">Inicio</a>
        <span>/</span>
        <a href="/backoffice/catalogo.jsp">Catalogo</a>
        <span>/</span>
        <a href="/backoffice/articulo.jsp?codigo=<%= codigo %>"><%= codigo %></a>
        <% if (articulo != null) { %>

        <div class="article-container">
            <div class="article-image-container">
                <img src ="assets/images/store/<%= articulo.getFoto() %>">
            </div>
            <div class="article-details">
                <a href="/backoffice/catalogo.jsp">Volver al catálogo</a>
                <p class="article-manufacturer"><%= articulo.getFabricante() %></p>
                <h3 class="article-title"><%= articulo.getNombre() %></h3>
                <p class="article-price"><%= String.format("%.2f", articulo.getPvp()) %> €</p>
                <p>Código: <%= articulo.getCodigo() %></p>
                <p>Descripción: <%= articulo.getDescripcion() %></p>
            </div>
        </div>
        <% } else { %>
        <p>Artículo no encontrado para el código: <%= (codigo != null) ? codigo : "desconocido" %></p>
        <a href="/backoffice/catalogo.jsp">Volver al catálogo</a>
        <% } %>
    </main>

    <%@ include file='/partials/__footer.jsp' %>
    <%@ include file='/partials/__body_bottom.jsp' %>
</body>

</html>
