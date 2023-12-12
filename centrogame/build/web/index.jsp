<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <% 
            String botoadmin = "";
            session = request.getSession(false);
            if (session == null || session.getAttribute("tipo") == null) {
                response.sendRedirect("login.jsp");
            }
            if(session.getAttribute("tipo") == "admin"){
                botoadmin = "<a href='admin.jsp'>administrador</a>";
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
    </body>
</html>
