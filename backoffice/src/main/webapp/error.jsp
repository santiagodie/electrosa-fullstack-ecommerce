<%-- 
    Document   : error
    Created on : 6 abr 2026, 14:05:29
    Author     : Santiago Die
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <%
        String VAR = " Error";
        String CSS = "error";
    %>
    <%@ include file='/partials/__head.jsp'%>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>

        <main class="cd-main-content main">

            <div class="error_container">

                <div class="error_imagen_container">
                    <img src="${pageContext.request.contextPath}/assets/images/96938.jpg"
                         alt="Error">
                </div>

                <div class="error_detail">

                    <h3>Lo sentimos, algo salió mal</h3>

                    <c:if test="${not empty requestScope['jakarta.servlet.error.status_code']}">
                        <h2>Error ${requestScope['jakarta.servlet.error.status_code']}</h2>
                    </c:if>

                    <div class="error-message">
                        <p>
                            <strong>Mensaje:</strong>
                            <c:out value="${requestScope['jakarta.servlet.error.message']}"
                                   default="Error de aplicación"/>
                        </p>
                    </div>

                    <div class="error_info">
                        <c:if test="${not empty requestScope['jakarta.servlet.error.request_uri']}">
                            <p>
                                <strong>URI</strong>
                                <c:out value="${requestScope['jakarta.servlet.error.request_uri']}"/>
                            </p>
                        </c:if>
                        <c:if test="${not empty requestScope['jakarta.servlet.error.servlet_name']}">
                            <p>
                                <strong>Servlet</strong>
                                <c:out value="${requestScope['jakarta.servlet.error.servlet_name']}"/>
                            </p>
                        </c:if>
                        <c:if test="${not empty requestScope['jakarta.servlet.error.exception_type']}">
                            <p>
                                <strong>Tipo</strong>
                                <c:out value="${requestScope['jakarta.servlet.error.exception_type']}"/>
                            </p>
                        </c:if>
                    </div>

                    <form action="${empty requestScope['enlaceSalir'] ?
                                    pageContext.request.contextPath.concat('') :
                                    requestScope['enlaceSalir']}">
                        <button type="submit" class="btn_error">Salir de aquí</button>
                    </form>

                </div>
            </div>

        </main>

        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
