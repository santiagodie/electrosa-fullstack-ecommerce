<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="es.unirioja.paw.model.Articulo" %>
<%@page import="java.util.List"%>
<%@page import="es.unirioja.paw.mysql.CatalogoDaoMySQL" %>
<%@page import="es.unirioja.paw.dao.CatalogoDAO" %>
<!DOCTYPE html>
<html lang="es">
<%
    // Estas variables son para que en cada pagina pese a usar todas los partials sea mas "personalizada"
    String VAR = "Catalogo Articulo";
    String CSS = "catalogo";
%>
<%@ include file='/partials/__head.jsp' %>

<body>
    <%@ include file='/partials/__body_top.jsp' %>

    <main class="cd-main-content main">
        <h2>Catálogo de productos</h2>
        <%
            CatalogoDAO dao = new CatalogoDaoMySQL();
            List<Articulo> articulos = dao.findAll();
            int cont = 0;
        %>
        <div id="t_catalogo">
            <p style="text-align:right">Número de elementos: <%= articulos.size() %></p>
            <table>
                <thead>
                    <tr>
                        <th>Foto</th>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Precio €</th>
                        <th>Tipo</th>
                        <th>Fabricante</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Articulo a : articulos) { %>
                        <tr class="table-row <%= (cont % 2 == 0) ? "even" : "odd" %>">
                            <td><a href="/backoffice/articulo.jsp?codigo=<%= a.getCodigo() %>"><img src ="assets/images/store/<%= a.getFoto() %>"/></a></td>
                            <td><a href="/backoffice/articulo.jsp?codigo=<%= a.getCodigo() %>"><%= a.getCodigo() %></a></td>
                            <td><%= a.getNombre() %></td>
                            <td><%= String.format("%.2f", a.getPvp()) %> €</td>
                            <td><%= a.getTipo() %></td>
                            <td><%= a.getFabricante() %></td>
                        </tr>
                        <% cont++; %>
                    <% } %>
                </tbody>
            </table>
        </div>
        <% if (articulos.isEmpty()) { %>
            <p>No se encontraron artículos.</p>
        <% } %>
    </main>

    <%@ include file='/partials/__footer.jsp' %>
    <%@ include file='/partials/__body_bottom.jsp' %>
</body>

</html>
