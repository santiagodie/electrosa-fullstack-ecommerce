<%-- 
    Document   : article-detail
    Created on : 3 abr 2026, 12:36:32
    Author     : Santiago Die
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="es.unirioja.paw.model.Articulo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
    <%
        Articulo articulo = (Articulo) request.getAttribute("articulo");
        String VAR = " " + (articulo != null ? articulo.getNombre() : "Artículo no disponible");
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
            <span>${articulo.codigo}</span>


            <h2 class="article-page-title">Ficha de ${articulo.nombre}</h2>

            <c:if test="${not empty successMessage}">
                <div class="modi_exitosa">${successMessage}</div>
                <% request.getSession().removeAttribute("successMessage"); %>
            </c:if>

            <div class="article-container">


                <div class="article-image-container">
                    <img src="${pageContext.request.contextPath}/assets/images/store/${articulo.foto}"
                         alt="${articulo.nombre}">
                </div>


                <div class="article-details">

                    <a href="<c:url value='/catalogue?p=${p}'/>">← Volver al catálogo</a>

                    <p class="article-fab">${articulo.fabricante}</p>
                    <h3 class="article-title">${articulo.nombre}</h3>
                    <p class="article-price">
                        <fmt:formatNumber value="${articulo.pvp}" type="currency" currencySymbol="€" />
                    </p>

                    <div class="article-details">
                        <div class="article-details-row">
                            <span class="article-details-label">Código</span>
                            <span class="article-details-value">${articulo.codigo}</span>
                        </div>
                        <div class="article-details-row">
                            <span class="article-details-label">Tipo</span>
                            <span class="article-details-value">${articulo.tipo}</span>
                        </div>
                        <div class="article-details-row">
                            <span class="article-details-label">Descripción</span>
                            <span class="article-details-value">${articulo.descripcion}</span>
                        </div>
                    </div>

                    <div class="article_botton">
                        <a href="<c:url value='/article_mod?artId=${articulo.codigo}&p=${p}'/>">
                            <button type="button" class="button_article">
                                <div class="svg-wrapper">
                                    <svg stroke-linejoin="round" stroke-linecap="round" fill="none"
                                         stroke-width="2" stroke="#FFFFFF" height="24" width="24"
                                         viewBox="0 0 24 24">
                                    <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                                    </svg>
                                </div>
                                <span>Modificar artículo</span>
                            </button>
                        </a>
                    </div>

                </div>
            </div>

        </main>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
