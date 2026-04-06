<%-- 
    Document   : article-list.jsp
    Created on : 1 abr 2026, 11:31:10
    Author     : Santiago Die
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <% 
        String VAR=" Catálogo de Productos "; 
        String CSS="article-list";       
    %>
    <%@ include file='/partials/__head.jsp'%>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        
        <main class="cd-main-content main">
          <h2>Catálogo de Productos</h2>
            <div class ="page-nav-container">
                <div class="page-ini">
                   <c:if test="${!pagination.firstPage}"> 
                    <a class="page-primera" href="<c:url value='/catalogue?p=1'/>">Primera</a>
                   </c:if>
                   <c:if test="${!pagination.firstPage && !pagination.lastPage}">
                      <span>/</span> 
                   </c:if> 
                   <c:if test="${!pagination.lastPage}">
                     <a class="page-ultima" href="<c:url value='/catalogue?p=${pagination.totalPages}'/>">Última</a>
                   </c:if> 
                </div>
                <div class="page_navegacion">
                    <c:if test="${!pagination.firstPage}">
                        <a class="page-anterior" href="<c:url value='/catalogue?p=${pagination.currentPage - 1}'/>">Anterior</a>
                    </c:if>
                    <span class="current">Página ${pagination.currentPage} de ${pagination.totalPages}</span>
                    <c:if test="${!pagination.lastPage}">
                        <a class="page-siguiente" href="<c:url value='/catalogue?p=${pagination.currentPage + 1}'/>">Siguiente</a>
                    </c:if>
                </div>
            </div>
            <div class="page-elementos">
                <c:if test="${!pagination.lastPage}">
                    <span>Mostrando los elementos ${(pagination.currentPage - 1) * pagination.pageSize + 1} - ${pagination.currentPage * pagination.pageSize} de ${pagination.totalCount} resultados</span>
                </c:if>
                <c:if test="${pagination.lastPage}">
                    <span>Mostrando los elementos ${(pagination.currentPage - 1) * pagination.pageSize + 1} - ${pagination.totalCount} de ${pagination.totalCount} resultados</span>
                </c:if>
            </div>        
            <section class="card-layout">
                <c:forEach var="articulo" items="${articulos}">
                <div class="card-layout__item clg-item">
                    <div class="cod_articulo">${articulo.codigo}</div>
                    <div class="clg-item__image">
                        <a href="<c:url value='/article?artId=${articulo.codigo}&p=${pagination.currentPage}'/>">
                            <img src="assets/images/store/${articulo.foto}" 
                                    alt="Frigorífico C-340 EL - Edesa" />
                        </a>
                    </div>
                    <div class="clg-item__title">${articulo.nombre}</div>
                </div>
                </c:forEach>
            </section>
        </main>
        
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>