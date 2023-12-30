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
            .busqueda{
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                
            }
            .busqueda div{
                display: flex;
                flex-direction: column;
                align-items: center;
                width: 50%;
                background-color: rgb(213, 252, 213);
                padding: 15px;
                border-radius: 25px;
                padding-bottom: 15px;
            }
            select, input{
                padding: 4px;
                border-radius: 5px;
            }
        </style>
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
        <nav>
            <button><a href='admin.jsp'>administrador</a></button>
            <h1><a href="index.jsp">GAMING</a></h1>
            <button><a href="cerrar">cerrar sesion</a></button>
        </nav>
        <br>
        <div class="busqueda">
            <div>
                <div>
                    <h3>seleciona la accion luego la tabla a administrar</h3>
                </div>
            
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
                    <button type="submit" name='boton' value='consultar'>administrar</button>
                </form>
            </div>
        </div>
    </body>
</html>
