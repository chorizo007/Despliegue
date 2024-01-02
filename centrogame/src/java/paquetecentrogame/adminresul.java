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
        String botonBorrar = request.getParameter("borrar"); //para saber si estamos borrando
        if (botonBorrar != null) {
            String[] idsAEliminar = request.getParameterValues("eliminar[]"); //recogemos todos los ids de las filas a borrar
            String tipoBorrar = request.getParameter("tipoborrar"); //tabla de origen
            if (idsAEliminar != null) {
                respuesta = miConexion.eliminarAdmin(tipoBorrar,idsAEliminar);
            } else {
                respuesta = "No has seleccionado ninguna opcion para eliminar."; //si no hemos selecionado ninguno
            }
        } else {
            //recogemos los campos que comparten nombre
            String juegos = request.getParameter("juegos");
            String idConsola = request.getParameter("idConsola");
            String unidadesDisponibles = request.getParameter("unidadesDisponibles");
            String precio = request.getParameter("precio");
            if (juegos != null) {
                String idJuego = request.getParameter("idJuego");
                String nombreJuego = request.getParameter("nombreJuego");
                String desarrolladora = request.getParameter("desarrolladora");
                String genero = request.getParameter("genero");
                String puntuacionMetacritic = request.getParameter("puntuacionMetacritic");
                String[] arraycampos = {idConsola, nombreJuego, desarrolladora, genero, puntuacionMetacritic, precio, unidadesDisponibles};
                try {
                    respuesta = miConexion.admininsertar("juegos", arraycampos); //hacemos el insert de los campos
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    respuesta = "Error en la consulta SQL: " + ex.getMessage(); //manejar las excepciones 
                }
            } else {
                String nombre = request.getParameter("nombre");
                String potenciaCPU = request.getParameter("potenciaCPU");
                String potenciaGPU = request.getParameter("potenciaGPU");
                String compania = request.getParameter("compania");
                String[] arraycampos = {nombre, potenciaCPU, potenciaGPU, compania, precio, unidadesDisponibles};
                try {
                    respuesta = miConexion.admininsertar("consola", arraycampos); //hacemos el insert de los campos
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    respuesta = "Error en la consulta SQL: " + ex.getMessage(); //manejar las excepciones 
                }
            }
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
            out.println("<button><a href='admin.jsp'>administrador</a></button>");
            out.println("<h1><a href=\"index.jsp\">GAMING</a></h1>");
            out.println("<button><a href=\"cerrar\">cerrar sesion</a></button>");
            out.println("</nav>");
            out.println("<h1>" + respuesta + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
