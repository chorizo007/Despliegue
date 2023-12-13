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
        <title>tabla</title>
        <% 
            session = request.getSession(false);
            if (session == null || session.getAttribute("tipo") == null) {
                response.sendRedirect("login.jsp");
            }
            String tipodeconsulta = request.getParameter("consulta"); 
            String juego = request.getParameter("juego"); 
            Conexion miconexion = new Conexion();
            miconexion.Conectar();
            ResultSet resultado = null;
            String tabla = "";
            if (juego != null) {
                tipodeconsulta = juego;
            } else if (tipodeconsulta.equals("consolas") || tipodeconsulta.equals("total-juegos") || tipodeconsulta.equals("total") || juego!=null) {
                tabla = miconexion.total(tipodeconsulta);
            } else {
                response.sendRedirect("index.jsp");
            }
            miconexion.desConectar();
        %>
    </head>
    <body>
        <h1>RESUTLADO : </h1>
        <form action="compra" method="post">
            <%=tabla %>
        </form>
        
    </body>
</html>