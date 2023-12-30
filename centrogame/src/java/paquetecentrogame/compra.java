/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package paquetecentrogame;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 *
 * @author Nico
 */
@WebServlet(name = "compra", urlPatterns = {"/compra"})
public class compra extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String botoadmin = "";
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("tipo") == null) {
            response.sendRedirect("login.jsp");
        }
        if(session.getAttribute("tipo") == "admin"){
            botoadmin = "<button><a href='admin.jsp'>administrador</a></button>";
        }
        String volver = request.getParameter("botonvolver");
        String tipo = request.getParameter("tipo");
        String[] arrayboton = tipo.split(",");
        Conexion miconexion = new Conexion();
        String resultado = "no se realizo nada";
        miconexion.Conectar();
        try {
            if (arrayboton.length == 2) {
                resultado = miconexion.eliminar(arrayboton[0], arrayboton[1]);
            } else {
                resultado = "Error: Formato incorrecto de parámetros.";
            }
        } catch (SQLException e) {
            resultado = "Error: " + e.getMessage();
        }
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<style>");
            out.println("    nav{");
            out.println("        background-color: rgb(213, 252, 213);");
            out.println("        display: flex;");
            out.println("        justify-content: space-between;");
            out.println("    }");
            out.println("    nav *{");
            out.println("        margin: 14px;");
            out.println("    }");
            out.println("    button{");
            out.println("        background-color:white;");
            out.println("        border:2px solid rgba(50, 55, 56, 0.839);");
            out.println("        border-radius: 10px;");
            out.println("        margin: 5px;");
            out.println("        padding: 8px;");
            out.println("        padding-left: 25px;");
            out.println("        padding-right: 25px;");
            out.println("        text-decoration: none;");
            out.println("        color: black;");
            out.println("    }");
            out.println("    button:hover{");
            out.println("        background-color: rgba(50, 55, 56, 0.839);");
            out.println("        color: white;");
            out.println("        border: 2px solid white;");
            out.println("    }");
            out.println("    a{");
            out.println("        text-decoration: none;");
            out.println("        color:black;");
            out.println("    }");
            out.println("</style>");
            out.println("<title>Servlet compra</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<nav>");
            out.println(botoadmin);
            out.println("<h1><a href=\"index.jsp\">GAMING</a></h1>");
            out.println("<button><a href=\"cerrar\">cerrar sesion</a></button>");
            out.println("</nav>");
            out.println("<h1>" + resultado + "</h1>");
            out.println("<a href='tabla.jsp?" + volver + "'>seguir comprando</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
