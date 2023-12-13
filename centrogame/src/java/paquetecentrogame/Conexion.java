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
        //"jdbc:mysql://localhost:3306/centrogame";
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
    

    public String total(String tipo) throws SQLException {
        String consulta = "";
        String tabla = "";
        if (tipo.equals("total-juegos")) {
            consulta = "SELECT * FROM juegos";
        } else if (tipo.equals("total")) {
            consulta = "SELECT * FROM juegos INNER JOIN consolas ON juegos.idConsola = consolas.idConsola";
        } else if (tipo.equals("consolas")) {
            consulta = "SELECT * FROM consolas";
        }else{
            consulta = "SELECT * FROM juegos";
            //consulta = "SELECT * FROM juegos WHERE nombreJuego=" + tipo; 
        }
        try {
            try (PreparedStatement preparedStatement = this.miConexion.prepareStatement(consulta)) {
                ResultSet resultado = preparedStatement.executeQuery();
                if (resultado != null) {
                    int columnCount = resultado.getMetaData().getColumnCount();
                    if (columnCount > 0) {
                        if(resultado.next()){
                            do {
                                tabla += "<tr>";
                                for (int i = 1; i <= columnCount; i++) {
                                    tabla += "<td>" + resultado.getString(i) + "</td>";
                                }
                                if(tipo.equals("total")){
                                    tabla += "<td><button value='"+ resultado.getString(1)+","+"juego"+"' name='tipo'>COMPRRAR JUEGO</td>";
                                    tabla += "<td><button value='"+ resultado.getString(2)+","+"consolas"+"' name='tipo'>COMPRRAR CONSOLA</td>";
                                }
                                tabla += "<td><button value='"+ resultado.getString(1)+","+tipo+"' name='tipo'>COMPRAR</td>";
                                tabla += "</tr>";
                            }while((resultado.next()));
                        }
                        else{
                            tabla += "no se ha encontrado ningún resultado a la búsqueda";
                        }
                    } else {
                        tabla += "no hay columnas";
                    }
                } else {
                    tabla += "Error en la consulta";
                }
                return tabla;
            }
        } catch (SQLException e) {
             return e.getMessage();
        }
    }
    public String eliminar(String codigo, String tipo) throws SQLException {
        String consulta = "";
        String delete = "";

        if (tipo.equals("consolas")) {
            consulta = "SELECT unidadesDisponibles FROM consolas WHERE idConsola = ?";
            delete = "DELETE FROM consolas WHERE idConsola=?";
        } else {
            consulta = "SELECT unidadesDisponibles FROM juegos WHERE idJuego = ?";
            delete = "DELETE FROM juegos WHERE idJuego=?";
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

        return "No hay unidades disponibles para eliminar";
    }

}
