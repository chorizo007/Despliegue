package paquetecentrogame;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class adminresul extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Conexion miConexion = new Conexion();
        miConexion.Conectar();
        String respuesta = "";
        String botonBorrar = request.getParameter("borrar");
        if (botonBorrar != null) {
            String[] idsAEliminar = request.getParameterValues("eliminar[]");
            String tipoBorrar = request.getParameter("tipoborrar");
            if (idsAEliminar != null) {
                respuesta = miConexion.eliminarAdmin(tipoBorrar,idsAEliminar);
            } else {
                respuesta = "No has seleccionado ninguna opcion para eliminar.";
            }
        } else {
            String juegos = request.getParameter("juegos");
            String idConsola = request.getParameter("idConsola");
            String unidadesDisponibles = request.getParameter("unidadesDisponibles");

            if (juegos != null) {
                String nombre = request.getParameter("nombre");
                String potenciaCPU = request.getParameter("potenciaCPU");
                String potenciaGPU = request.getParameter("potenciaGPU");
                String compania = request.getParameter("compania");
                String[] arraycampos = {idConsola, nombre, potenciaCPU, potenciaGPU, compania, unidadesDisponibles};
                try {
                    respuesta = miConexion.admininsertar("consola", arraycampos);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    respuesta = "Error en la consulta SQL: " + ex.getMessage();
                }
            } else {
                String idJuego = request.getParameter("idJuego");
                String nombreJuego = request.getParameter("nombreJuego");
                String desarrolladora = request.getParameter("desarrolladora");
                String genero = request.getParameter("genero");
                String puntuacionMetacritic = request.getParameter("puntuacionMetacritic");
                String precio = request.getParameter("precio");
                String[] arraycampos = {idJuego, idConsola, nombreJuego, desarrolladora, genero, puntuacionMetacritic, precio, unidadesDisponibles};
                try {
                    respuesta = miConexion.admininsertar("juegos", arraycampos);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    respuesta = "Error en la consulta SQL: " + ex.getMessage();
                }
            }
        }
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Admin</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + respuesta + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
