<%-- 
    Document   : article-change
    Created on : 5 abr 2026, 9:56:56
    Author     : Santiago Die
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="es.unirioja.paw.model.Articulo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
    <%
        Articulo articulo = (Articulo) request.getAttribute("articulo");
        String VAR = " " + (articulo != null ? "Modificar "+articulo.getNombre() : "Artículo no disponible");
        String CSS = "article-detail";
    %>
    <%@ include file='/partials/__head.jsp'%>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>

        <main class="cd-main-content main">

            <a href="${pageContext.request.contextPath}/">Inicio</a>
            <span>›</span>
            <a href="<c:url value='/catalogue?p=${p}'/>">Catálogo</a>
            <span>›</span>
            <a href="<c:url value='/article?artId=${articulo.codigo}&p=${page}'/>">${articulo.codigo}</a>  

            <h2 class="article-page-title">Modificación de ${articulo.nombre}</h2>

            <c:if test="${not empty errorMessage}">
                <div class="modificacion_error">${errorMessage}</div>
                <% request.getSession().removeAttribute("errorMessage"); %>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="modi_exitosa">${successMessage}</div>
                <% request.getSession().removeAttribute("successMessage"); %>
            </c:if>   


            <form action="<c:url value='/article_mod'/>" method="POST">
                <div class="article-container">
                    <div class="article-image-container">
                        <div class="cambio">
                            <a href="<c:url value='/article_img?artId=${articulo.codigo}&p=${page}'/>">📄Cambiar foto</a>       
                        </div>
                        <img src="assets/images/store/${articulo.foto}" alt="Imagen del artículo">
                    </div>  

                    <div class="article-details">

                        <a href="<c:url value='/catalogue?p=${page}'/>">← Volver al catálogo</a>

                        <h3 class="article-title">️Información básica</h3>
                        <div class="article-details">

                            <span class="article-details-label">Nombre <br></span>
                            <input type="text" id="article_nom" name="article_nom" value="${articulo.nombre}" required />
                            <span class="article-details-label">Tipo</span>
                            <select id="article-tipo" name="article-tipo" required>
                                <option value="${articulo.tipo}">${articulo.tipo}</option>
                                <c:forEach var="tipo" items="${tipos}">
                                    <c:if test="${tipo.nombre != articulo.tipo}">
                                        <option value="${tipo.nombre}">${tipo.nombre}</option>
                                    </c:if>
                                </c:forEach> 
                            </select>
                            <span class="article-details-label">Descripción<br></span>
                            <textarea id="article_desp" name="article_desp" class="article_desp" required>${articulo.descripcion}</textarea>

                            <span class="article-details-label">Fabricante</span>
                            <select id="article-fab" name="article-fab" required>
                                <option value="${articulo.fabricante}">${articulo.fabricante}</option>
                                <c:forEach var="fabricante" items="${fabricantes}">
                                    <c:if test="${fabricante.nombre != articulo.fabricante}">
                                        <option value="${fabricante.nombre}">${fabricante.nombre}</option>
                                    </c:if>
                                </c:forEach> 
                            </select>
                            <span class="article-details-label">Precio <br></span>
                            <input type="number" id="article_pvp" name="article_pvp" value="${articulo.pvp}" step="0.01" min="0.01" required />
                        </div>
                        <div class="article_botton">
                            <input type="hidden" name="artId" value="${articulo.codigo}" />
                            <input type="hidden" name="p" value="${page}" />

                            <button type="submit" class="button_article">                                 
                                <div class="svg-wrapper">
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        viewBox="0 0 24 24"
                                        width="30"
                                        height="30"
                                        class="icon"
                                        ><path
                                        d="M22,15.04C22,17.23 20.24,19 18.07,19H5.93C3.76,19 2,17.23 2,15.04C2,13.07 3.43,11.44 5.31,11.14C5.28,11 5.27,10.86 5.27,10.71C5.27,9.33 6.38,8.2 7.76,8.2C8.37,8.2 8.94,8.43 9.37,8.8C10.14,7.05 11.13,5.44 13.91,5.44C17.28,5.44 18.87,8.06 18.87,10.83C18.87,10.94 18.87,11.06 18.86,11.17C20.65,11.54 22,13.13 22,15.04Z"
                                        ></path>
                                    </svg>
                                </div>

                                <span>Aplicar cambios</span>
                            </button>
                        </div>
                    </div>  
                </div>
            </form>

            <c:if test="${articulo == null}">
                <p>Artículo no encontrado</p>
                <a href="<c:url value='/catalogue?p=${page}'/>">Volver al catálogo</a>
            </c:if>
        </main>

        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>