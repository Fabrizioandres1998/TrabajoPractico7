package Vista;

import Modelo.Alumno;
import Persistencia.AlumnoData;
import modelo.Conexion;
import Persistencia.MateriaData;      
import Persistencia.InscripcionData;
import java.time.LocalDate;
import org.mariadb.jdbc.Connection;

public class TrabajoPractico7 {

    public static void main(String[] args) {
        
        // Datos de la base de datos 
        String url = "jdbc:mariadb://localhost:3306/sgulp"; // o jdbc:mysql://...
        String usuario = "root";
        String password = "";

        // Crea objeto de conexión
        Conexion conexion = new Conexion(url, usuario, password);
        Connection con = (Connection) conexion.buscarConexion();

        // Verifica conexion
        if (con != null) {
            System.out.println("Conectado correctamente a la base de datos.");
        } else {
            System.out.println("No se pudo conectar.");
        }
        
    
        //  Crea las CAPAS DE DATOS 
        AlumnoData alumnoData = new AlumnoData(conexion);
        MateriaData materiaData = new MateriaData(conexion);
        InscripcionData inscripcionData = new InscripcionData(conexion);
    
        //  Crea y inicia la ventana principal (SGULP)
        
        SGULP menuPrincipal = new SGULP(alumnoData, materiaData, inscripcionData); 
        menuPrincipal.setVisible(true);
        menuPrincipal.setLocationRelativeTo(null); // Centra la ventana
       }
        }

    

