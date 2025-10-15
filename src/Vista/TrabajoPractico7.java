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
        
        // Datos de tu base de datos (los mismos que en phpMyAdmin)
        String url = "jdbc:mariadb://localhost:3307/sgulp"; // o jdbc:mysql://...
        String usuario = "root";
        String password = "";

        // Crear objeto de conexión
        Conexion conexion = new Conexion(url, usuario, password);
        Connection con = (Connection) conexion.buscarConexion();

        // Verificar conexión
        if (con != null) {
            System.out.println("Conectado correctamente a la base de datos.");
        } else {
            System.out.println("No se pudo conectar.");
        }
        
    
        // 2. Crear las CAPAS DE DATOS (Persistencia)
        AlumnoData alumnoData = new AlumnoData(conexion);
        MateriaData materiaData = new MateriaData(conexion);
        InscripcionData inscripcionData = new InscripcionData(conexion);
    
        // 3. Crear e iniciar la ventana principal (SGULP)
        // Le pasamos todas las capas de datos al constructor de SGULP.
        SGULP menuPrincipal = new SGULP(alumnoData, materiaData, inscripcionData); 
        menuPrincipal.setVisible(true);
        menuPrincipal.setLocationRelativeTo(null); // Centra la ventana
       }
        }

    

