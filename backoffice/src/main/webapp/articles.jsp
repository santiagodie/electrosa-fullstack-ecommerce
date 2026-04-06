<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="es.unirioja.paw.db.GestorBD" %>
<!DOCTYPE html>
<html lang="es">
<%
    // Estas variables son para que en cada pagina pese a usar todas los partials sea mas "personalizada"
    String VAR = "Buqueda '" + (request.getParameter("q") != null ? request.getParameter("q") : "") + "'";
    String CSS = "estilos_P2";
%>
<%@ include file='/partials/__head.jsp' %>

<body>
    <%@ include file='/partials/__body_top.jsp' %>

    <main class="cd-main-content main">
        <div class="articles_container">
            <div class="card">
                <div class="card_container">
                    <section>
                        <form action="articles.jsp">
                            <fieldset>
                                <div class ="search_container_aux">
                                    <div>
                                        <h3 id="cd-search" for="some-input">Buscar en todo el catálogo</h3>
                                    </div>
                                    <div class="group">
                                        <input type="text" required="" id="nom" name="q"  value="<%= request.getParameter("q") != null ? request.getParameter("q") : "" %>" class="input">
                                        <span class="highlight"></span>
                                        <span class="bar"></span>
                                        <label>Buscar</label>
                                    </div>
                                    <div class="boton">
                                        <button type="submit" class="boton_p2" id="cd-buscador">
                                            <span>Buscar productos</span>
                                        </button>
                                    </div>
                                    <div>
                                        <h3>Resultados de la búsqueda: </h3>
                                    </div>
                                </div>


                            </fieldset>
                        </form>


                        <p>Búsqueda de <span style="font-weight: bold;">'<%= request.getParameter("q") == null ? "" : request.getParameter("q")%>'</span> en todo el catálogo de productos:</p>
                        <div style="margin: 20px 0">
                            <%
                                String nombre = request.getParameter("q");
                                if (nombre != null && !nombre.trim().isEmpty()) {
                                    GestorBD gbd = new GestorBD();
                                    List<String> articulos = gbd.getArticulosLike(nombre);
                                    if (articulos != null && !articulos.isEmpty()) {
                            %>
                            <ul>
                                <%
                                    for (String n : articulos) {
                                %>
                                <li><%= n %></li>
                                    <%
                                        }
                                    %>
                            </ul>
                            <%
                                    } else { //(lista vacía o null)
                            %>
                            <ul>
                                <li>El artículo buscado no se ha podido encontrar...</li>
                            </ul>
                            <%
                                    }
                                }
                            %>

                        </div>
                    </section>
                </div>

            </div>
        </div>
    </main>

    <%@ include file='/partials/__footer.jsp' %>
    <%@ include file='/partials/__body_bottom.jsp' %>
</body>

</html>
