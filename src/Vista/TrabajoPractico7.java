package Vista;

import Modelo.Alumno;
import Persistencia.AlumnoData;
import modelo.Conexion;
import Persistencia.MateriaData;      
import Persistencia.InscripcionData;
import java.sql.Connection;
import java.time.LocalDate;

public class TrabajoPractico7 {

    public static void main(String[] args) {
        
        // Datos de la base de datos 
        String url = "jdbc:mariadb://localhost:3306/sgulp";
        String usuario = "root";
        String password = "";

        // Crear conexión
        Conexion conexion = new Conexion(url, usuario, password);
        Connection con = conexion.buscarConexion(); // ✅ sin cast

        // Verificar conexión
        if (con != null) {
            System.out.println("Conectado correctamente a la base de datos.");
        } else {
            System.out.println("No se pudo conectar.");
        }
        
        // Crear capas de datos
        AlumnoData alumnoData = new AlumnoData(conexion);
        MateriaData materiaData = new MateriaData(conexion);
        InscripcionData inscripcionData = new InscripcionData(conexion);
        
        // Crear vistas
        VistaCargarNotas vistaNotas = new VistaCargarNotas(alumnoData, materiaData, inscripcionData);
        ListarInscripciones listarInscripciones = new ListarInscripciones(alumnoData, materiaData, inscripcionData);

        // Crear ventana principal
        SGULP menuPrincipal = new SGULP(alumnoData, materiaData, inscripcionData, vistaNotas, listarInscripciones); 
        menuPrincipal.setVisible(true);
        menuPrincipal.setLocationRelativeTo(null);
    }
}
