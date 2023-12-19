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

public class adminresul extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Conexion miConexion = new Conexion();
        miConexion.Conectar();
        String juegos = request.getParameter("consola");
        String consolas = request.getParameter("juegos");
        String idConsola = request.getParameter("idConsola");
        String unidadesDisponibles = request.getParameter("unidadesDisponibles");
        if(juegos !=null){
            String nombre = request.getParameter("nombre");
            String potenciaCPU = request.getParameter("potenciaCPU");
            String potenciaGPU = request.getParameter("potenciaGPU");
            String compania = request.getParameter("compania");
            String[] arraycampos = {idConsola, nombre, potenciaCPU, potenciaGPU, compania,unidadesDisponibles};
            try {
                String respuesta = miConexion.admininsertar("consola", arraycampos);
            } catch (SQLException ex) {
                Logger.getLogger(adminresul.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            String idJuego = request.getParameter("idJuego");
            String nombreJuego = request.getParameter("nombreJuego");
            String desarrolladora = request.getParameter("desarrolladora");
            String genero = request.getParameter("genero");
            String puntuacionMetacritic = request.getParameter("puntuacionMetacritic");
            String precio = request.getParameter("precio");
            String[] arraycampos = {idJuego, idConsola, nombreJuego, desarrolladora, genero, puntuacionMetacritic, precio, unidadesDisponibles};
            try {
                String respuesta = miConexion.admininsertar("juegos", arraycampos);
            } catch (SQLException ex) {
                Logger.getLogger(adminresul.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

