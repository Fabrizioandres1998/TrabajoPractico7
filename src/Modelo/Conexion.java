package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private String url;
    private String usuario;
    private String password;

    public static Connection con = null;

    public Conexion(String url, String usuario, String password) {
        this.url = url;
        this.usuario = usuario;
        this.password = password;
    }

    public Connection buscarConexion() {
        if (con == null) {
            try {
                Class.forName("org.mariadb.jdbc.Driver"); //conectar driver
                con = DriverManager.getConnection(url, usuario, password); //hacer conexion
            } catch (ClassNotFoundException ex) { //excepcion para problemas relacionados al driver
                System.err.println("Error al obtener la clase driver " + ex.getMessage());
            } catch (SQLException sqlEx) { //excepcion para problemas relacionados al jdbc
                System.err.println("Error al conectar la bd");
            }
        }
        return con;
    }
}
