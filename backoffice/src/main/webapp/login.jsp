<%-- 
    Document   : login
    Created on : 9 abr 2026, 18:22:55
    Author     : Santiago Die
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sign In | Electrosa</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/logo/favicon.ico" type="image/x-icon">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
        <script src="assets/js/modernizr.js"></script>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    </head>
    <%--
    
    Algunos contenidos se han sacado de internet entre el jsp y el css, son implementaciones esteticas esteticos
    el div:title animation de https://uiverse.io/satyamchaudharydev/quick-chicken-50
    el div:button_wrapper es una ispiracion de https://uiverse.io/mrhyddenn/red-stingray-4
    
    div: username_icon y div:password_icon provienen de copiar con devtools el codigo de la pagina de inicio de cesion de deepseek
    --%>

    <body>

        <div class="login_container">
            <div class="message_error_container">
                <c:if test="${not empty errorMessage}">
                    <div class="error">${errorMessage}</div>
                </c:if>
            </div>
            <div class="login_image_wrapper">
                <div class="title_animation" data-text="Awesome">
                    <span class="actual-text">&nbsp;ELECTROSA&nbsp;</span>
                    <span aria-hidden="true" class="hover-text">&nbsp;ELECTROSA&nbsp;</span>
                </div>
                <img src="${pageContext.request.contextPath}/assets/images/logo-electrosa-retro.png" alt="Electrosa Logo"/>
            </div>
            <div class="login_details_wrapper">
                <form action="${pageContext.request.contextPath}/auth/login" method="POST">
                    <div class="username_wrapper">
                        <div class="username_icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" fill="none">
                            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" fill="currentColor"></path>
                            </svg>
                        </div>
                        <input type="text" id="login_username" name="login_username" value="${username}" placeholder="Username" size="1"  />

                    </div>
                    <div class="message_error_username">
                        <c:if test="${not empty errorUsername}">
                            <div class="error">${errorUsername}</div>
                        </c:if>
                    </div>
                    <div class="password_wrapper">
                        <div class="password_icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" fill="none">
                            <path d="M12 2C8.13 2 5 5.13 5 9v2H4c-.55 0-1 .45-1 1v8c0 .55.45 1 1 1h16c.55 0 1-.45 1-1v-8c0-.55-.45-1-1-1h-1V9c0-3.87-3.13-7-7-7zm0 2c2.76 0 5 2.24 5 5v2H7V9c0-2.76 2.24-5 5-5zm-1 9h2v2h-2v-2z" fill="currentColor"></path>
                            </svg>
                        </div>
                        <input type="password" id="login_password" name="login_password" placeholder="Password" size="1"  />

                    </div>
                    <div class="message_error_password">
                        <c:if test="${not empty errorPassword}">
                            <div class="error">${errorPassword}</div>
                        </c:if>
                    </div>
                    <div class="button_wrapper">
                        <button type="submit" class="btn_login">
                            <span>Log In</span>
                        </button>
                    </div>
                <input type="hidden" name="next" value="${param.next}">
                </form>
            </div>
        </div>
    </body>
</html>