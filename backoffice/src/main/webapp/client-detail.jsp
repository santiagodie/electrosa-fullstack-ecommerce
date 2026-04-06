<%-- 
    Document   : client-detail.jsp
    Created on : 27 mar 2026, 12:22:47
    Author     : Santiago Die
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page import="es.unirioja.paw.model.Cliente" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <%
        Cliente cliente = (Cliente) request.getAttribute("cliente");
        String VAR = "Perfil " + cliente.getUsername();
        String CSS = "cliente-detail";
    %>
    <%@ include file='/partials/__head.jsp' %>

    <body>
        <%@ include file='/partials/__body_top.jsp' %>

        <main class="cd-main-content main">

            <div class="perfil-header">
                <div class="perfil-breadcrumb">
                    <a href="${pageContext.request.contextPath}/clientes">Clientes</a>
                    <span class="sep">/</span>
                    <span>${cliente.username}</span>
                </div>
                <h2>Perfil de cliente</h2>
            </div>

            <div class="perfil-card">

                <div class="perfil-sidebar">
                    <div class="perfil-avatar">
                        <img src="${pageContext.request.contextPath}/assets/images/avatar/7294811.jpg"
                             alt="Avatar de ${cliente.nombre}">
                    </div>
                    <div class="perfil-badge">${cliente.username}</div>
                </div>

                <div class="perfil-body">
                    <h3 class="perfil-nombre">${cliente.nombre}</h3>
                    <div class="perfil-divider"></div>

                    <dl class="perfil-datos">
                        <div class="perfil-dato">
                            <dt>
                                <span class="perfil-icon">&#9632;</span>
                                Código
                            </dt>
                            <dd>${cliente.codigo}</dd>
                        </div>
                        <div class="perfil-dato">
                            <dt>
                                <span class="perfil-icon">&#9632;</span>
                                Email
                            </dt>
                            <dd><a href="mailto:${cliente.email}">${cliente.email}</a></dd>
                        </div>
                        <div class="perfil-dato">
                            <dt>
                                <span class="perfil-icon">&#9632;</span>
                                Ciudad
                            </dt>
                            <dd>${cliente.ciudad}</dd>
                        </div>
                        <div class="perfil-dato">
                            <dt>
                                <span class="perfil-icon">&#9632;</span>
                                Provincia
                            </dt>
                            <dd>${cliente.provincia}</dd>
                        </div>
                        <div class="perfil-dato">
                            <dt>
                                <span class="perfil-icon">&#9632;</span>
                                Código postal
                            </dt>
                            <dd>${cliente.codigoPostal}</dd>
                        </div>
                    </dl>
                </div>

            </div>

        </main>

        <%@ include file='/partials/__footer.jsp' %>
        <%@ include file='/partials/__body_bottom.jsp' %>
    </body>
</html>
