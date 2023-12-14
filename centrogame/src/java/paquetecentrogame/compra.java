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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("tipo") == null) {
            response.sendRedirect("login.jsp");
        }
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
            out.println("<title>Servlet compra</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + resultado + "</h1>");
            out.println("<a href='tabla.jsp?consultaA=" + arrayboton[1] + "'>seguir comprando</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
