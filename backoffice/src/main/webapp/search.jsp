<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="es">
<%
    // Estas variables son para que en cada pagina pese a usar todas los partials sea mas "personalizada"
    String VAR = "Buscar Articulo";
    String CSS = "estilos_P2";
%>
<%@ include file='/partials/__head.jsp' %>

<body>
    <%@ include file='/partials/__body_top.jsp' %>

    <main class="cd-main-content main">
        <div class="search_container">
            <div class="card">
                <div class="card_container">
                    <h2 id="cd-hola">Hola</h2>
                    <p id="cd-p">Electrosa ha venido acompañando a los alumnos de informática de la Universidad de La Rioja desde 2003</p>
                    <section>
                        <form action="articles.jsp">
                            <fieldset>
                                <legend id="cd-Bus">¿Qué producto busca?</legend>
                                <div class ="search_container_aux">
                                    <div>
                                        <h3 id="cd-search" for="some-input">Buscar en todo el catálogo</h3>
                                    </div>
                                    <div class="group">
                                        <input type="text" required="" id="nom" name="q" value="" class="input">
                                        <span class="highlight"></span>
                                        <span class="bar"></span>
                                        <label>Buscar</label>
                                    </div>
                                    <div class="boton">
                                        <button type="submit" class="boton_p2" id="cd-buscador">
                                            <span>Buscar productos</span>
                                        </button>
                                    </div>
                                </div>

                            </fieldset>
                        </form>
                    </section>
                </div>

            </div>
        </div>

    </main>

    <%@ include file='/partials/__footer.jsp' %>
    <%@ include file='/partials/__body_bottom.jsp' %>
</body>

</html>
