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
            String selectjuego = "";
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
                    selectjuego = "<option value='juegos' selected>buscar un juego</option>";
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

        <form action="index.jsp" method="post">
            <select name="tipo-de-consulta">
                <%=selectjuego%>
                <option value="consolas">consolas</option>
                <option value="juegos">buscar un juego</option>
                <option value="total-juegos">consultar el total de juegos</option> 
                <option value="total">todos los productos</option>
            </select>
            <br>
            <%=inputjuego%>
            <button type="submit" name='boton' value='consultar'>BUSCAR</button>
        </form>
        <br>
        <br>
        <br>
        <br>
        <a href="cerrar">cerrar sesion</a>
    </body>
</html>
