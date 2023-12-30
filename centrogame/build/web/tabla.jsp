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
        <style>
            nav{
                background-color: rgb(213, 252, 213);
                display: flex;
                justify-content: space-between;
            }
            nav *{
                margin: 14px;
            }
            button{
                background-color:white;
                border:2px solid rgba(50, 55, 56, 0.839);
                border-radius: 10px;
                margin: 5px;
                padding: 8px;
                padding-left: 25px;
                padding-right: 25px;
                text-decoration: none;
                color: black;
            }
            button:hover{
                background-color: rgba(50, 55, 56, 0.839);
                color: white;
                border: 2px solid white;       
            }
            a{
                text-decoration: none;
                color:black;
            }
            select, input{
                padding: 4px;
                border-radius: 5px;
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>tabla</title>
        <% 
            String botoadmin = "";
            session = request.getSession(false);
            if (session == null || session.getAttribute("tipo") == null) {
                response.sendRedirect("login.jsp");
            }
            if(session.getAttribute("tipo") == "admin"){
                botoadmin = "<button><a href='admin.jsp'>administrador</a></button>";
            }
            String tipodeconsulta = request.getParameter("consulta"); 
            String juego = request.getParameter("juego");
            String botonvolver = "consulta="+tipodeconsulta;
            Conexion miconexion = new Conexion();
            miconexion.Conectar();
            ResultSet resultado = null;
            String tabla = "";
            
            if (juego != null) {
                botonvolver+= "&juego="+juego;
                tipodeconsulta = juego;
            }
            if (tipodeconsulta.equals("consolas") || tipodeconsulta.equals("total-juegos") || tipodeconsulta.equals("total") || juego!=null) {
                tabla = miconexion.total(tipodeconsulta);
            }else {
                response.sendRedirect("index.jsp");
            }
            miconexion.desConectar();
        %>
    </head>
    <body>
        <nav>
            <%=botoadmin%>
            <h1><a href="index.jsp">GAMING</a></h1>
            <button><a href="cerrar">cerrar sesion</a></button>
        </nav>
        <h1>RESULTADO DE LA BUSQUEDA: </h1>
        <form action="compra" method="post">
            <input type="hidden" name="botonvolver" value='<%=botonvolver%>'>
            <%=tabla %>
        </form>
        
    </body>
</html>