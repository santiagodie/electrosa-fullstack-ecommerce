<%-- 
    Document   : pedido-list
    Created on : 4 abr 2026, 16:52:42
    Author     : Santiago Die
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <% //Estas variables son para que en cada pagina pese a usar todas los partials sea mas "personalizada"
        String VAR=" Pedidos"; 
       String CSS="pedido-list";
    %>
    <%@ include file='/partials/__head.jsp'%>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        
        
        <main class="cd-main-content main">
            <h2>Pedidos</h2>
            <div class="pedido-elemento">
            <p>Se han encontrado ${pedidos.size()} pedidos</p>
            </div>
            <div id="t_catalogo">
            <table>
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Cliente</th>
                        <th>Código Postal</th>
                        <th>Calle</th>
                        <th>Ciudad</th>
                        <th>Provincia</th>                    
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pedido" items="${pedidos}" varStatus="status">
                        <tr class="table-row ${status.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><a id="table-row-link" href="#">${pedido.codigo}</a></td>
                            <td>${pedido.codigoCliente}</td>
                            <td>${pedido.cp}</td>
                            <td>${pedido.calle}</td>
                            <td>${pedido.ciudad}</td>
                            <td>${pedido.provincia}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${empty pedidos}">
                <p>No se encontraron pedidos.</p>
        </c:if>
               
        </main>
        
        
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
