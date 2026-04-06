<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%
    request.setCharacterEncoding("UTF-8");
    String nombre = request.getParameter("nom");
%>
<!DOCTYPE html>
<html lang="es">
<%
    // Estas variables son para que en cada pagina pese a usar todas los partials sea mas "personalizada"
    String VAR = "Hola " + nombre;
    String CSS = "estilos_P2";
%>
<%@ include file='/partials/__head.jsp' %>

<body>
    <%@ include file='/partials/__body_top.jsp' %>

    <main class="cd-main-content main">
        <div class="dihola_container">
            <div class="card">

                <%
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String fechaHora = formatoFecha.format(new Date());
                %>
                <h2>¡Hola, <span class="span_p2_diHola"><%= nombre %></span> !<br> Bienvenido a Electrosa </h2>
                <p>La fecha y hora actual es:  <span class="span_p2_diHola"><%= fechaHora %></span></p>
                <div class="boton">
                    <a href="/backoffice/welcome.html">
                        <button type="submit" class="boton_p2">
                            <span>Volver al formulario</span>
                        </button>
                    </a>
                </div>
            </div>
        </div>

    </main>

    <%@ include file='/partials/__footer.jsp' %>
    <%@ include file='/partials/__body_bottom.jsp' %>
</body>

</html>
