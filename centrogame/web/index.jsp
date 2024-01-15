<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="paquetecentrogame.Conexion"%> 
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
        <title>centrogame</title>
        <% 
            String botoadmin = ""; //string que contendra el codigo de html de acceso solo a los administradores
            String inputjuego = ""; //si es es juego se añadara este input para poder escribirlo 
            String selectjuego = ""; //para que cuando le de demos a enviar, este en el select la busqueda de juego
            session = request.getSession(false); //con esto no iniciamos una sesion
            if (session == null || session.getAttribute("tipo") == null) { //comprobacion de si esta logeado
                response.sendRedirect("login.jsp");
            }
            if(session.getAttribute("tipo") == "admin"){ //comprobar si es admin
                botoadmin = "<button><a href='admin.jsp'>administrador</a></button>";
            }

            //añadir el input del juego o ir directamente a tabla.jsp para hacer la busqueda 
            String tipodeconsulta = request.getParameter("tipo-de-consulta"); 
            String nombrejuego = request.getParameter("nombrejuego");
            String consola = request.getParameter("consola");
            if(nombrejuego!=null){
                response.sendRedirect("tabla.jsp?juego=" + nombrejuego + "&consola=" + consola); 
            }
            if (tipodeconsulta != null) {
                if (tipodeconsulta.equals("juegos")) {
                    Conexion miconexion = new Conexion();
                    miconexion.Conectar();
                    inputjuego = "Inserte el nombre del juego a buscar <input name='nombrejuego'>";
                    selectjuego = "<option value='juegos' selected>buscar un juego</option>";
                    inputjuego += "<br>";
                    inputjuego += "<br>";
                    inputjuego += "<label >Selecciona la consola del juego</label>";
                    inputjuego += miconexion.selectconsolas();
                } else {
                    response.sendRedirect("tabla.jsp?consulta=" + tipodeconsulta);
                }
            }
        %>
    </head>
    <body>
        <nav>
            <%=botoadmin%>
            <h1><a href="index.jsp">GAMING</a></h1>
            <button><a href="cerrar">cerrar sesion</a></button>
        </nav>
        <br>

        <div class="busqueda">
            <div>
                <div>
                    <h2>Que quieres buscar?</h2>
                </div>
                <form action="index.jsp" method="post">
                    <select name="tipo-de-consulta">
                        <%=selectjuego%>
                        <option value="consolas">consolas</option>
                        <option value="juegos">buscar un juego</option>
                        <option value="total-juegos">consultar el total de juegos</option> 
                        <option value="total">todos los productos</option>
                    </select>
                    <br>
                    <%=inputjuego%>
                    <button type="submit" name='boton' value='consultar'>BUSCAR</button>
                </form>
            </div>
        </div>

        <br>
        <br>
        <br>
        <br>
        
    </body>
</html>
