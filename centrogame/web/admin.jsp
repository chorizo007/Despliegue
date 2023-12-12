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
        %>
    </head>
    <body>
        <h1>solo admin</h1>
        <a href="tablaadmin.jsp">tabla</a>
    </body>
</html>
