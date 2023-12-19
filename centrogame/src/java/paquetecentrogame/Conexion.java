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
        ruta ="jdbc:mysql://localhost:3306/centrogame";
        //ruta = "jdbc:mysql://192.168.1.144:3306/centrogame";
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
                consulta = "SELECT * FROM juegos WHERE nombreJuego='" + tipo + "'";
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

}
