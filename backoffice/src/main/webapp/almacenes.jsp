<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="es.unirioja.paw.model.Almacen" %>
<%@page import="java.util.List"%>
<%@page import="es.unirioja.paw.mysql.AlmacenDaoMySQL" %>
<%@page import="es.unirioja.paw.dao.AlmacenDAO" %>
<%@page import="es.unirioja.paw.model.ExcepcionDeAplicacion" %>
<!DOCTYPE html>
<html lang="es">
<%
    // Estas variables son para que en cada pagina pese a usar todas los partials sea mas "personalizada"
    String VAR = "Almacenes";
    String CSS = "almacenes";
%>
<%@ include file='/partials/__head.jsp' %>

<body>
    <%@ include file='/partials/__body_top.jsp' %>

    <main class="cd-main-content main">
        <h2>Almacenes</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
        <div class="card-layout">
            <%
                AlmacenDaoMySQL dao = new AlmacenDaoMySQL();
                List<Almacen> almacenes = dao.findAll();
                String url="#";
                for (Almacen almacen : almacenes) {
                switch (almacen.getCodigo()) {
                case "a0001-99":
                    url="https://commons.wikimedia.org/wiki/File:Getafe_Air_Base_-_Aerial_photograph_(2).jpg";
                    break;
                case "a0002-99":
                    url="https://commons.wikimedia.org/wiki/File:Catedral_de_Santa_Mar%C3%ADa_de_Burgos_-_03.jpg";

                    break;
                case "a0003-02":
                    url="https://commons.wikimedia.org/wiki/File:Lleida_(40262867523).jpg";

                    break;
                case "a0004-02":
                    url="https://commons.wikimedia.org/wiki/File:Mosque_Cordoba_edited.jpg";
                    break;
                case "a0005-03":
                    url="https://commons.wikimedia.org/wiki/File:La_Casa_de_los_Botines_de_Antonio_Gaud%C3%AD_en_Le%C3%B3n_edited.jpg";
                    break;
            }
            %>
            <div class="card-layout__item" onclick="window.location = '<%= url %>'">
                <p id="card-layout__item_1"><%= almacen.getCodigo() %></p>
                <img src="assets/images/warehouse/512px-<%= almacen.getCodigo() %>.jpg" alt="<%= almacen.getCiudad() %>">
                <h3><%= almacen.getCiudad() %></h3>
                <p><%= almacen.getProvincia() %></p>
                <button type="submit" class="boton_almacen" onclick="event.stopPropagation()">
                    <span>Cambiar</span>
                </button>
            </div>

            <%
                }
            %>
        </div>

    </main>

    <%@ include file='/partials/__footer.jsp' %>
    <%@ include file='/partials/__body_bottom.jsp' %>
</body>

</html>
