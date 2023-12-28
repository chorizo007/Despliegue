<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="paquetecentrogame.Conexion"%>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.logging.Level" %>
<%@ page import="java.util.logging.Logger" %>
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
            }
            Conexion miconexion = new Conexion();
            miconexion.Conectar();
            String respuesta = "";
            String consulta = request.getParameter("consulta"); 
            String []tipos = consulta.split(";");
            String tabla = "";

            if(tipos[0].equals("eliminar")) {
                if(tipos[1].equals("consola")) {
                    tabla = miconexion.admineliminar("consola");
                } else {
                    tabla = miconexion.admineliminar("juego");
                }
            } else {
                if(tipos[1].equals("consola")) {
                    respuesta = 
                        "<label for='idConsola'>ID de Consola:</label>" +
                        "<input type='text' name='idConsola' id='idConsola' required>" +
                        "<br>" +
                        "<label for='nombre'>Nombre:</label>" +
                        "<input type='text' name='nombre' id='nombre' required>" +
                        "<br>" +
                        "<label for='potenciaCPU'>Potencia CPU:</label>" +
                        "<input type='text' name='potenciaCPU' id='potenciaCPU'>" +
                        "<br>" +
                        "<label for='potenciaGPU'>Potencia GPU:</label>" +
                        "<input type='text' name='potenciaGPU' id='potenciaGPU'>" +
                        "<br>" +
                        "<label for='compania'>Compañía:</label>" +
                        "<input type='text' name='compania' id='compania'>" +
                        "<br>" +
                        "<label for='precio'>Precio:</label>" +
                        "<input type='text' name='precio' id='precio'>" +
                        "<br>" +
                        "<label for='unidadesDisponibles'>Unidades Disponibles:</label>" +
                        "<input type='text' name='unidadesDisponibles' id='unidadesDisponibles'>" +
                        "<br>"+
                        "<input type='text' name='consola' value='consola' hidden>";

                } else {
                    respuesta = 
                        "<label for='idJuego'>ID de Juego:</label>" +
                        "<input type='text' name='idJuego' id='idJuego' required>" +
                        "<br>" +
                        "<label for='idConsola'>ID de Consola:</label>" +
                        "<input type='text' name='idConsola' id='idConsola'>" +
                        "<br>" +
                        "<label for='nombreJuego'>Nombre del Juego:</label>" +
                        "<input type='text' name='nombreJuego' id='nombreJuego' required>" +
                        "<br>" +
                        "<label for='desarrolladora'>Desarrolladora:</label>" +
                        "<input type='text' name='desarrolladora' id='desarrolladora'>" +
                        "<br>" +
                        "<label for='genero'>Género:</label>" +
                        "<input type='text' name='genero' id='genero'>" +
                        "<br>" +
                        "<label for='puntuacionMetacritic'>Puntuación Metacritic:</label>" +
                        "<input type='text' name='puntuacionMetacritic' id='puntuacionMetacritic'>" +
                        "<br>" +
                        "<label for='precio'>Precio:</label>" +
                        "<input type='text' name='precio' id='precio'>" +
                        "<br>" +
                        "<label for='unidadesDisponibles'>Unidades Disponibles:</label>" +
                        "<input type='text' name='unidadesDisponibles' id='unidadesDisponibles'>" +
                        "<br>"+
                        "<input type='text' name='juegos' value='juegos' hidden>";
                }
                respuesta += "<br><br><br><br><button type='submit'>INSERTAR</button>";
            }
            miconexion.desConectar();
            
        %>
    </head>
    <body>
        <h1>Solo Admin TABLA</h1>
        <form action="adminresul" method="post">
            <%=respuesta%>
            <%=tabla%>
        </form>
        <br>
        <br>
        </br>
        <a href="index.jsp">Inicio</a>
    </body>
</html>
