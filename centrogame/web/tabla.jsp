<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %> 
<%@ page import="paquetecentrogame.Conexion"%> 
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
            ResultSet resultado;
            String tabla = "";
            if(juego!=null){
                resultado = miconexion.buscarjuego(juego);   
            }else if(tipodeconsulta.equals("consultas")){
                resultado = miconexion.total(tipodeconsulta); 
            }else if(tipodeconsulta.equals("total-juegos")){
                resultado = miconexion.total(tipodeconsulta); 
            }else if(tipodeconsulta.equals("total")){
                resultado = miconexion.total(tipodeconsulta); 
            }else{
                response.sendRedirect("index.jsp");
            }
            int columnCount = resultSet.getMetaData().getColumnCount();
            if(columnCount!=null){
                while (resultSet.next()) {
                    tabla+="<tr>";
                    for (int i = 1; i <= columnCount; i++) {
                        tabla =+ "<td>" + resultSet.getString(i) + "</td>";
                    }
                    tabla+="</tr>";
                }
            }else{
                tabla = "no se ha encontrado ningun resultado a la busqueda";
            }
            resultSet.close();
        %>
    </head>
    <body>
        <h1>RESUTLADO : </h1>
        <%=tabla%>
    </body>
</html>