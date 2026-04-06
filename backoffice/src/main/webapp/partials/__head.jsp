<%-- 
    Document   : __head
    Created on : 27 mar 2026, 9:57:28
    Author     : Santiago Die
--%>

<head>
    <% String title ="Electrosa - "+ VAR;
        String estilo=CSS;                                   
     %>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= title %></title>

        <link rel="shortcut icon" href="assets/images/logo/favicon.ico" type="image/x-icon">

        <link href="https://cdn.lineicons.com/5.0/lineicons.css" rel="stylesheet" />

        <link rel="stylesheet" href="assets/css/reset.css">
        <link rel="stylesheet" href="assets/css/megamenu.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/css/<%= estilo%>.css">

        <script src="assets/js/modernizr.js"></script>

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    </head>
