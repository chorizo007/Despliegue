/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package paquetecentrogame;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class valizamiento extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("user");
        String contra = request.getParameter("password");
        String tipo = request.getParameter("boton");
        if(usuario == null || contra == null){
            if(tipo.equals("login")){
                response.sendRedirect("login.jsp?error=rellenar los campos");
            }
            response.sendRedirect("registro.jsp?error=rellenar los campos");
        }
        Conexion miconexion = new Conexion();
        miconexion.Conectar();
        
        if(tipo.equals("login")){
            String respuesta = miconexion.ComprobarUser(usuario, contra);
            if(respuesta.equals("admin")){
                HttpSession session = request.getSession(true);
                session.setAttribute("tipo", "admin");
                response.sendRedirect("index.jsp");
            }else if(respuesta.equals("user")){
                HttpSession session = request.getSession(true);
                session.setAttribute("tipo", "user");
                response.sendRedirect("index.jsp");
            }else{
                response.sendRedirect("login.jsp?error="+respuesta);
            }
        }else{
            String respuesta = miconexion.insertarUser(usuario,contra);
            if(respuesta.equals("insertado")){
                HttpSession session = request.getSession(true);
                session.setAttribute("tipo", "user");
                response.sendRedirect("index.jsp");
            }else{
                response.sendRedirect("registro.jsp?error="+respuesta);
            }
        }
        
        try {
            miconexion.desConectar();
        } catch (SQLException ex) {
            Logger.getLogger(valizamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
