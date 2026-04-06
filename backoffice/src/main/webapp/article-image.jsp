<%-- 
    Document   : article-image
    Created on : 6 abr 2026, 18:19:01
    Author     : Santiago Die
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="es.unirioja.paw.model.Articulo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
    <% //Estas variables son para que en cada pagina pese a usar todas los partials sea mas "personalizada"
       Articulo articulo = (Articulo) request.getAttribute("articulo");        
       String VAR = " " + (articulo != null ? articulo.getNombre() : "Artículo no disponible");
       String CSS="article-image";
    %>
    <%@ include file='/partials/__head.jsp'%>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>


        <main class="cd-main-content main">

            <a href="<c:url value='/article?artId=${articulo.codigo}&p=${page}'/>">${articulo.codigo}</a>

            <h2 class="article-image-page-title">Modificar foto de ${articulo.nombre}</h2>
            <c:if test="${not empty errorMessage}">
                <div class="modificacion_error">${errorMessage}</div>
            </c:if>

            <div class="article-container"> 

                <div class="article-image-container">
                    <div>
                        <img src="assets/images/store/${articulo.foto}" alt="Imagen del artículo">
                    </div>
                </div>            

                <form action="<c:url value='/article_img'/>" method="POST" enctype="multipart/form-data" >

                    <div class="article-details">
                        
                        <a href="<c:url value='/article?artId=${articulo.codigo}&p=${p}'/>">← Volver al artículo</a>
                        <div>
                            <label for="article__imagen">Adjuntar foto</label>
                            <div class="drop-zone">
                                <span class="drop-zone__icon">
                                    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M22,15.04C22,17.23 20.24,19 18.07,19H5.93C3.76,19 2,17.23 2,15.04C2,13.07 3.43,11.44 5.31,11.14C5.28,11 5.27,10.86 5.27,10.71C5.27,9.33 6.38,8.2 7.76,8.2C8.37,8.2 8.94,8.43 9.37,8.8C10.14,7.05 11.13,5.44 13.91,5.44C17.28,5.44 18.87,8.06 18.87,10.83C18.87,10.94 18.87,11.06 18.86,11.17C20.65,11.54 22,13.13 22,15.04Z"/>
                                    </svg>
                                </span>
                                <p class="drop-zone__text">Seleccionar archivo</p>
                                <p class="drop-zone__hint">PNG, JPG, WEBP — máx. 5 MB</p>
                                <input type="file"
                                       id="article__imagen"
                                       name="article__imagen"
                                       accept="image/*"
                                       onchange="document.getElementById('file-name').textContent = this.files[0]?.name || ''">
                            </div>
                            <p class="file-name" id="file-name"></p>
                        </div>
                        
                        <div class="article_botton">
                            <input type="hidden" name="artId" value="${articulo.codigo}" />
                            <input type="hidden" name="p" value="${page}" />
                            <button type="submit" class="button_article">                                 
                                <div class="svg-wrapper">
                                    <svg xmlns="http://www.w3.org/2000/svg"viewBox="0 0 24 24"width="30"height="30"class="icon">
                                    <path d="M22,15.04C22,17.23 20.24,19 18.07,19H5.93C3.76,19 2,17.23 2,15.04C2,13.07 3.43,11.44 5.31,11.14C5.28,11 5.27,10.86 5.27,10.71C5.27,9.33 6.38,8.2 7.76,8.2C8.37,8.2 8.94,8.43 9.37,8.8C10.14,7.05 11.13,5.44 13.91,5.44C17.28,5.44 18.87,8.06 18.87,10.83C18.87,10.94 18.87,11.06 18.86,11.17C20.65,11.54 22,13.13 22,15.04Z"></path>
                                    </svg>
                                </div>

                                <span>Guardar cambios</span>
                            </button>
                        </div>
                    </div>
                </form>  
            </div>
        </main>


        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
