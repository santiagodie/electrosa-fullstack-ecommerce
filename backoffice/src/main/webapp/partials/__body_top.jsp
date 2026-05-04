<%-- 
    Document   : __body_top
    Created on : 27 mar 2026, 9:56:52
    Author     : Santiago Die
--%>

<div class="topbar">

    <span class="topbar_userinfo" style="">${sessionScope.usuario.username}</span>
    <span class="topbar_sep">/</span> 
    <a href="${pageContext.request.contextPath}/auth/logout"}" class="topbar_link"><!-- From Uiverse.io by vinodjangid07 --> 
        <button class="logout_animation">
            <div class="logout_icon"><i class="lni lni-power-button"></i></div>

            <div class="logout_text"> Log out</div>
        </button>


    </a>
</div>


<header class="cd-main-header header">
    <a class="cd-logo" href="/backoffice/">
        <h1 class="store-branding-logo">                   
            <img src="assets/images/logo-electrosa-retro.png" />        
            <span>Backoffice</span>
        </h1>
    </a>

    <ul class="cd-header-buttons">
        <li><a class="cd-search-trigger" href="#cd-search">Search<span></span></a></li>
        <li><a class="cd-nav-trigger" href="#cd-primary-nav">Menu<span></span></a></li>
    </ul> <!-- cd-header-buttons -->
    <nav class="cd-nav ">
        <ul id="cd-primary-nav" class="cd-primary-nav is-fixed">

            <li class="has-children">
                <a href="#"><i class="lni lni-rocket-5"></i> Ventas</a>

                <ul class="cd-nav-gallery is-hidden">
                    <li class="go-back"><a href="#0">Menu</a></li>
                    <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                    <li>
                        <a class="cd-nav-item" href="/backoffice/orders">
                            <img src="assets/images/megamenu/pedidos.jpg" alt="Product Image">
                            <h3>Pedidos</h3>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item" href="/backoffice/almacenes.jsp">
                            <img src="assets/images/megamenu/almacen.png" alt="Product Image">
                            <h3>Almacenes</h3>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets/images/megamenu/envio.jpg" alt="Product Image">
                            <h3>Envios</h3>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets/images/megamenu/clientes.jpg" alt="Product Image">
                            <h3>Herramientas de ventas</h3>
                        </a>
                    </li>


                </ul>
            </li>

            <li class="has-children">
                <a href="#"><i class="lni lni-dashboard-square-1"></i> Catalogo</a>
                <ul class="cd-nav-icons is-hidden">
                    <li class="go-back"><a href="#0">Menu</a></li>
                    <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                    <li>
                        <a class="cd-nav-item item-9" href="${pageContext.request.contextPath}/catalogue">
                            <h3>Productos</h3>
                            <p>Todos los productos</p>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item item-10" href="#">
                            <h3>Categorias</h3>
                            <p>Categorias / Tipos de producto</p>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item item-11" href="#">
                            <h3>Fabricantes</h3>
                            <p>Fabricantes y proveedores</p>
                        </a>
                    </li>


                </ul>
            </li>

            <li class="has-children">
                <a href="#"><i class="lni lni-rocket-5"></i> Analitica</a>

                <ul class="cd-nav-gallery is-hidden">
                    <li class="go-back"><a href="#0">Menu</a></li>
                    <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Cuadro de mando</h3>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Marketing</h3>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Facturacion</h3>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Finanzas</h3>
                        </a>
                    </li>
                </ul>
            </li>

            <li class="has-children">
                <a href="#"><i class="lni lni-sliders-horizontal-square-2"></i> Ajustes</a>
                <ul class="cd-nav-icons is-hidden">
                    <li class="go-back"><a href="#0">Menu</a></li>
                    <li>
                        <a class="cd-nav-item item-1" href="/backoffice/clientes">
                            <h3>Usuarios</h3>
                            <p>Usuarios del sistema</p>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item item-2" href="#">
                            <h3>Rendimiento</h3>
                            <p>Optimizaciones, cache y modulos</p>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item item-3" href="#">
                            <h3>Importar</h3>
                            <p>Importacion de datos en el catalogo</p>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item item-4" href="#">
                            <h3>Servicios web</h3>
                            <p>API de servicios web REST</p>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item item-5" href="#">
                            <h3>Seguridad</h3>
                            <p>Parametros de seguridad de la tienda online</p>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item item-6" href="#">
                            <h3>Base de datos</h3>
                            <p>Gestor simple SQL y copias de seguridad</p>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item item-7" href="#">
                            <h3>Informacion</h3>
                            <p>Informacion del sistema</p>
                        </a>
                    </li>

                    <li>
                        <a class="cd-nav-item item-8" href="#">
                            <h3>Logs</h3>
                            <p>Registros de errores y auditoria</p>
                        </a>
                    </li>
                </ul>
            </li>
        </ul> <!-- primary-nav -->

    </nav> <!-- cd-nav -->
</header>
