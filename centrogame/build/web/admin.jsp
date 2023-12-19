<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <% 
            session = request.getSession(false);
            if (session != null && session.getAttribute("tipo") != "admin") {
                response.sendRedirect("index.jsp");
            }/*
            String tipodeconsulta = request.getParameter("tipo-de-consulta"); 
            String nombrejuego = request.getParameter("nombrejuego");
            if(nombrejuego!=null){
                response.sendRedirect("tabla.jsp?juego=" + nombrejuego);
            }
            if (tipodeconsulta != null) {
                if (tipodeconsulta.equals("juegos")) {
                    inputjuego = "Inserte el nombre del juego a buscar <input name='nombrejuego'>";
                    selectjuego = "<option value='juegos' selected>buscar un juego</option>";
                } else {
                    response.sendRedirect("tabla.jsp?consulta=" + tipodeconsulta);
                }
            }*/
        %>
    </head>
    <body>
        <h1>solo admin</h1>
        <a href="tablaadmin.jsp">tabla</a>
    </body>
</html>
