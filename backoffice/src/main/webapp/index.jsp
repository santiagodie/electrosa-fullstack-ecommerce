<%-- 
    Document   : index
    Created on : 27 mar 2026, 10:47:55
    Author     : Santiago Die
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <%
        // Estas variables son para que en cada pagina pese a usar todas los partials sea mas "personalizada"
        String VAR = "Inicio";
        String CSS = "index";
    %>
    <%@ include file='/partials/__head.jsp' %>
    <body>
        <%@ include file='/partials/__body_top.jsp' %>
        <main class="cd-main-content main">
            <div class="idx-wrapper">

                <header class="idx-header">
                    <div class="idx-header__tag">ELECTROSA / PRÁCTICAS</div>
                    <h1 class="idx-header__title">Indice de practicas</h1>
                    <p class="idx-header__sub">Santiago Die Morejón &mdash; Curso 2025/26</p>
                    <div class="idx-header__line"></div>
                </header>

                <div class="idx-grid">

                    <!-- P1 -->
                    <div class="idx-card">
                        <div class="idx-card__num">01</div>
                        <div class="idx-card__body">
                            <h2 class="idx-card__title">Práctica 1</h2>
                            <ul class="idx-card__links">
                                <li><a href="${pageContext.request.contextPath}/new-product.html">new-product</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- P2 -->
                    <div class="idx-card">
                        <div class="idx-card__num">02</div>
                        <div class="idx-card__body">
                            <h2 class="idx-card__title">Práctica 2</h2>
                            <ul class="idx-card__links">
                                <li><a href="${pageContext.request.contextPath}/welcome.html">welcome</a></li>
                                <li><a href="${pageContext.request.contextPath}/search.jsp">search</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- P3 -->
                    <div class="idx-card">
                        <div class="idx-card__num">03</div>
                        <div class="idx-card__body">
                            <h2 class="idx-card__title">Práctica 3</h2>
                            <ul class="idx-card__links">
                                <li><a href="${pageContext.request.contextPath}/catalogo.jsp">catálogo</a></li>
                                <li><a href="${pageContext.request.contextPath}/articulo.jsp?codigo=Ede-338FO">artículo</a></li>
                                <li><a href="${pageContext.request.contextPath}/almacenes.jsp">almacenes</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- P4 S1 -->
                    <div class="idx-card">
                        <div class="idx-card__num">04<span class="idx-card__session">S1</span></div>
                        <div class="idx-card__body">
                            <h2 class="idx-card__title">Práctica 4 &middot; Paginación</h2>
                            <ul class="idx-card__links">
                                <li><a href="${pageContext.request.contextPath}/cliente?clientId=070011-03">cliente</a></li>
                                <li><a href="${pageContext.request.contextPath}/catalogue?pagina=1">article-list</a></li>
                                <li><a href="${pageContext.request.contextPath}/article?artId=Ede-340FO&pagina=1">article-detail</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- P4 S2 -->
                    <div class="idx-card">
                        <div class="idx-card__num">04<span class="idx-card__session">S2</span></div>
                        <div class="idx-card__body">
                            <h2 class="idx-card__title">Práctica 4 &middot; Formularios</h2>
                            <ul class="idx-card__links">
                                <li><a href="${pageContext.request.contextPath}/orders">pedidos</a></li>
                                <li><a href="${pageContext.request.contextPath}/article_mod?artId=Ede-339FO&pagina=1">article-change</a></li>
                                <li><a href="${pageContext.request.contextPath}/article?artId=malo&pagina=1" class="idx-link--error">error</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- TRABAJO -->
                    <div class="idx-card">
                        <div class="idx-card__num">04<span class="idx-card__session">Trabajo</span></div>
                        <div class="idx-card__body">
                            <h2 class="idx-card__title">Práctica 4 &middot; Trabajo</h2>
                            <ul class="idx-card__links">
                                <li><a href="${pageContext.request.contextPath}/clientes">clientes</a></li>
                                <li><a href="${pageContext.request.contextPath}/article_img?artId=Ede-339FO&p=1">article-image</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- P5 Backoffice -->
                    <div class="idx-card">
                        <div class="idx-card__num">05<span class="idx-card__session">Backoffice</span></div>
                        <div class="idx-card__body">
                            <h2 class="idx-card__title">Práctica 5</h2>
                            <ul class="idx-card__links">
                                <li><a href="${pageContext.request.contextPath}/auth/login">login</a></li>
                                <li><a href="${pageContext.request.contextPath}/auth/logout">logout</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <%@ include file='/partials/__footer.jsp' %>
        <%@ include file='/partials/__body_bottom.jsp' %>
    </body>

</html>

