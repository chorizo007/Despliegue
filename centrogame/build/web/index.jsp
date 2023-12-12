<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>centrogame</title>
        <% 
            String botoadmin = "";
            String inputjuego = "";
            session = request.getSession(false);
            if (session == null || session.getAttribute("tipo") == null) {
                response.sendRedirect("login.jsp");
            }
            if(session.getAttribute("tipo") == "admin"){
                botoadmin = "<a href='admin.jsp'>administrador</a>";
            }
            String tipodeconsulta = request.getParameter("tipo-de-consulta"); 
            String nombrejuego = request.getParameter("nombrejuego");
            if(nombrejuego!=null){
                response.sendRedirect("tabla.jsp?juego=" + nombrejuego);
            }
            if (tipodeconsulta != null) {
                if (tipodeconsulta.equals("juegos")) {
                    inputjuego = "Inserte el nombre del juego a buscar <input name='nombrejuego'>";
                } else {
                    response.sendRedirect("tabla.jsp?consulta=" + tipodeconsulta);
                }
            }
        %>
    </head>
    <body>
        <nav>
        <p>barra de navegacion</p>
        </nav>
        <div>
            <p>menu principal</p>
        </div>
        <br>
        <br>
        <%=botoadmin%>
        <br>
        <br>
        <a href="cerrar">cerrar sesion</a>

        <form action="index.jsp" method="post">
            <select name="tipo-de-consulta">
                <option value="consolas" selected>consultar informacion sobre las consolas</option>
                <option value="juegos" >consultar el catalogo de un juego</option>
                <option value="total-juegos">consultar el total de juegos</option> 
                <option value="total">constar todos los produsctos </option>
            </select>
            <br>
            <%=inputjuego%>
            <button type="submit" name='boton' value='consultar'>enviar</button>
        </form>

    </body>
</html>
