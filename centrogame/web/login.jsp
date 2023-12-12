<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%session = request.getSession(false); %>
        <% 
            if (session != null && session.getAttribute("tipo") != null) {
                response.sendRedirect("index.jsp");
            }
        %>
    </head>
    <body>
        <h1>login</h1>
        <form action="valizamiento" method="post">
            usuario<input name="user">
            contraseña<input name="password">
            <button type="submit" name='boton' value='login'>enviar</button>
            <%
                String mensaje = request.getParameter("error");
                if (mensaje != null) {
            %>
                   <p><%= mensaje %></p>
            <%
                }
            %>
        </form>
        <br>
        <br>
        <a href="registro.jsp">NO tienes cuenta ??</a>
    </body>
</html>
