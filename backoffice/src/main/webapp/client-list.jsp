<%-- 
    Document   : client-list
    Created on : 5 abr 2026, 8:53:30
    Author     : Santiago Die
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <% //Estas variables son para que en cada pagina pese a usar todas los partials sea mas "personalizada"
        String VAR=" Clientes"; 
       String CSS="pedido-list";
    %>
    <%@ include file='/partials/__head.jsp'%>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        
        
        <main class="cd-main-content main">
        <h2>Clientes</h2>
        <div class="pedido-elemento">
            <p>Se han encontrado ${clientes.size()} clientes</p>
            </div>
            <div id="t_catalogo">
            <table>
                <thead>
                    <tr>
                        <th>Usuario</th>
                        <th>Nombre</th>
                        <th>Email</th>                  
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cliente" items="${clientes}" varStatus="status">
                        <tr class="table-row ${status.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><a id="table-row-link" href="<c:url value='/cliente?clientId=${cliente.codigo}'/>">${cliente.codigo}</a></td>
                            <td>${cliente.nombre}</td>
                            <td>${cliente.email}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${empty clientes}">
                <p>No se encontraron clientes.</p>
        </c:if>
              
        </main>
        
        
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
