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
            if(tipos[0].equals("eliminar")){
                if(tipos[1].equals("consola")){
                    respuesta = miconexion.admineliminar("consola");
                }else{
                    respuesta = miconexion.admineliminar("consola");
                }
            }else{
                if(tipos[1].equals("consola")){
                    
                }else{

                }
            }
            miconexion.desConectar();
        %>
    </head>
    <body>
        <h1>solo admin TABLA</h1>
        <%=respuesta %>
        <a href="index.jsp">inicio</a>
    </body>
</html>