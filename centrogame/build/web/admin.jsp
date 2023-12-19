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
            String tipodeconsulta = request.getParameter("tipo-de-consulta"); 
            String tipo = request.getParameter("tipo"); 
            if (tipodeconsulta != null) {
                response.sendRedirect("tablaadmin.jsp?consulta=" + tipodeconsulta + ";" + tipo);
            }
        %>
    </head>
    <body>
        <h1>solo admin</h1>
            <form action="admin.jsp" method="post">
            <select name="tipo-de-consulta">
                <option value="insertar">insertar</option>
                <option value="eliminar">eliminar</option>
            </select>
            <select name="tipo">
                <option value="consola">consola</option>
                <option value="juego">juego</option>
            </select>
            <br>
            <button type="submit" name='boton' value='consultar'>enviar</button>
        </form>
    </body>
</html>
