/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paquetecentrogame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
    private String usuario;
    private String passwordc;
    private String ruta;
    private Connection miConexion;

    public Conexion() {
        usuario = "daw2";
        passwordc = "1234";
        //ruta ="jdbc:mysql://localhost:3306/centrogame";
        ruta = "jdbc:mysql://192.168.1.144:3306/centrogame";
    }

    public void Conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.miConexion = DriverManager.getConnection(ruta, usuario, passwordc);
        } catch (Exception e) {
        }
    }
    public void desConectar() throws SQLException {
        miConexion.close();
    }

    public String ComprobarUser(String user, String password) {
        try {
            String consulta = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = this.miConexion.prepareStatement(consulta)) {
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, password);

                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    if (resultado.next()) {
                        if (resultado.getBoolean("isAdmin")) {
                            return "admin";
                        }
                        return "user";
                    }
                    return "no se ha encontrado";
                }
            }
        } catch (SQLException e) {
            return "no se ha encontrado";
        }
    }

    public String insertarUser(String user, String password) {
        try {
            String consulta = "SELECT * FROM usuarios WHERE username = ?";
            try (PreparedStatement preparedStatement = this.miConexion.prepareStatement(consulta)) {
                preparedStatement.setString(1, user);
                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    if (resultado.next()) {
                        return "ya existe este usuario";
                    } else {
                        // El usuario no existe, por lo tanto, se puede realizar la inserción
                        String insercion = "INSERT INTO usuarios (username, password, isAdmin) VALUES (?, ?, false)";
                        try (PreparedStatement preparedStatementInsercion = this.miConexion.prepareStatement(insercion)) {
                            preparedStatementInsercion.setString(1, user);
                            preparedStatementInsercion.setString(2, password);

                            int filasAfectadas = preparedStatementInsercion.executeUpdate();

                            if (filasAfectadas > 0) {
                                return "insertado";
                            } else {
                                return "No se pudo insertar el usuario";
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            return "no se ha encontrado";
        }
    }
    

    public String total(String tipo) {
        String tabla = "<table>";

        try {
            String consulta = "";
            if ("total-juegos".equals(tipo)) {
                consulta = "SELECT * FROM juegos";
            } else if ("total".equals(tipo)) {
                consulta = "SELECT * FROM juegos INNER JOIN consolas ON juegos.idConsola = consolas.idConsola";
            } else if ("consolas".equals(tipo)) {
                consulta = "SELECT * FROM consolas";
            } else {
                consulta = "SELECT * FROM juegos WHERE nombreJuego like '%" + tipo + "%'";
            }

            try (PreparedStatement preparedStatement = this.miConexion.prepareStatement(consulta)) {
                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    int columnCount = resultado.getMetaData().getColumnCount();
                    if (columnCount > 0 && resultado.next()) {
                        do {
                            tabla += "<tr>";
                            for (int i = 1; i <= columnCount; i++) {
                                tabla += "<td>" + resultado.getString(i) + "</td>";
                            }
                            if ("total".equals(tipo)) {
                                tabla += "<td><button value='" + resultado.getString(1) + "," + "juego' name='tipo'>COMPRAR JUEGO</button></td>";
                                tabla += "<td><button value='" + resultado.getString(2) + "," + "consolas' name='tipo'>COMPRAR CONSOLA</button></td>";
                            }else{
                                tabla += "<td><button value='" + resultado.getString(1) + "," + tipo + "' name='tipo'>COMPRAR</button></td>";
                            }
                            
                            tabla += "</tr>";
                        } while (resultado.next());
                    } else {
                        tabla = "No se ha encontrado ningún resultado a la búsqueda";
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            tabla = "Error en la consulta: " + e.getMessage();
        }
        return tabla;
    }

    public String eliminar(String codigo, String tipo) throws SQLException {
        String consulta = "";
        String delete = "";

        if (tipo.equals("consolas")) {
            consulta = "SELECT unidadesDisponibles FROM consolas WHERE idConsola = ?";
            delete = "UPDATE consolas set unidadesDisponibles = unidadesDisponibles - 1 WHERE idConsola=?";
        } else {
            consulta = "SELECT unidadesDisponibles FROM juegos WHERE idJuego = ?";
            delete = "UPDATE juegos set unidadesDisponibles = unidadesDisponibles -1 WHERE idJuego=?";
        }

        int cantidad = 0;

        try (PreparedStatement preparedStatementConsulta = this.miConexion.prepareStatement(consulta)) {
            preparedStatementConsulta.setString(1, codigo);
            ResultSet resultado = preparedStatementConsulta.executeQuery();

            if (resultado.next()) {
                cantidad = resultado.getInt(1);
            } else {
                return "Error en la consulta";
            }
        }

        if (cantidad > 0) {
            try (PreparedStatement preparedStatementDelete = miConexion.prepareStatement(delete)) {
                preparedStatementDelete.setString(1, codigo);

                int filasAfectadas = preparedStatementDelete.executeUpdate();

                if (filasAfectadas > 0) {
                    return "Realizado con éxito";
                } else {
                    return "Error al realizar la petición";
                }
            }
        }

        return "No hay unidades disponibles para poder comprar";
    }

    public String tablaeliminar(String tipo) throws SQLException {
        String consulta = "";
        String tabla = "<table border='1'>";
        if (tipo.equals("consola")) {
            tabla += "<input type='hidden' name='tipoborrar' value='consola'>";
            consulta = "SELECT * FROM consolas";
        } else {
            tabla += "<input type='hidden' name='tipoborrar' value='juegos'>";
            consulta = "SELECT * FROM juegos";
        }
        try (PreparedStatement preparedStatement = this.miConexion.prepareStatement(consulta)) {
            try (ResultSet resultado = preparedStatement.executeQuery()) {
                int columnCount = resultado.getMetaData().getColumnCount();
                if (columnCount > 0) {
                    tabla += "<tr>";
                    for (int i = 1; i <= columnCount; i++) {
                        tabla += "<th>" + resultado.getMetaData().getColumnName(i) + "</th>";
                    }
                    tabla += "<th>Seleccionar</th></tr>";
                    while (resultado.next()) {
                        tabla += "<tr>";
                        for (int i = 1; i <= columnCount; i++) {
                            tabla += "<td>" + resultado.getString(i) + "</td>";
                        }
                        tabla += "<td><input type='checkbox' name='eliminar[]' value='" + resultado.getString(1) + "'></td>";
                        tabla += "</tr>";
                    }
                    tabla += "<tr><td colspan='" + columnCount + "'><button type='submit' name='borrar'>BORRAR</button></td></tr>";
                } else {
                    tabla = "No hay existencias";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            tabla = "Error en la consulta: " + e.getMessage();
        }
        tabla += "</table>";
        return tabla;
    }


    public String admininsertar(String tipo, String[] array) throws SQLException {
        String consulta = "";
        int filasAfectadas = 0;
        if (tipo.equals("consola")) {
            consulta = "INSERT INTO CONSOLAS (nombre, potenciaCPU, potenciaGPU, compania, precio, unidadesDisponibles) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement preparedStatementConsulta = this.miConexion.prepareStatement(consulta)) {
                preparedStatementConsulta.setString(1, array[0]); 
                preparedStatementConsulta.setString(2, array[1]);
                preparedStatementConsulta.setString(3, array[2]); 
                preparedStatementConsulta.setString(4, array[3]); 
                preparedStatementConsulta.setString(5, array[4]); 
                preparedStatementConsulta.setString(6, array[5]);
                filasAfectadas = preparedStatementConsulta.executeUpdate();
            }
        } else {
            consulta = "INSERT INTO JUEGOS (idConsola, nombreJuego, desarrolladora, genero, puntuacionMetacritic, precio, unidadesDisponibles) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatementConsulta = this.miConexion.prepareStatement(consulta)) {
                preparedStatementConsulta.setString(1, array[0]);
                preparedStatementConsulta.setString(2, array[1]);
                preparedStatementConsulta.setString(3, array[2]);
                preparedStatementConsulta.setString(4, array[3]);
                preparedStatementConsulta.setString(5, array[4]);
                preparedStatementConsulta.setString(6, array[5]);
                preparedStatementConsulta.setString(7, array[6]);
                filasAfectadas = preparedStatementConsulta.executeUpdate();
            }
        }
        if (filasAfectadas > 0) {
            return "Realizado con exito";
        } else {
            return "Error en la consulta";
        }
    }

    public String eliminarAdmin(String tipo, String[] array) {
        String tabla;
        String campo;
        if ("juegos".equals(tipo)) {
            tabla = "juegos";
            campo = "idjuego";
        } else {
            tabla = "consolas";
            campo = "idconsola";
        }

        String consulta = "DELETE FROM " + tabla + " WHERE " + campo + " IN ('0'";
        for (String id : array) {
            consulta += ",'" + id + "'";
        }
        consulta += ")";
        
        int filasAfectadas = 0;
        try (PreparedStatement preparedStatementConsulta = this.miConexion.prepareStatement(consulta)) {
            filasAfectadas = preparedStatementConsulta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la consulta";
        }

        if (filasAfectadas > 0) {
            return "Realizado con exito";
        } else {
            return "No se encontraron registros para eliminar";
        }
    }

}
