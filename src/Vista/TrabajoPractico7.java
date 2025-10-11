package Vista;

import Modelo.Alumno;
import Persistencia.AlumnoData;
import modelo.Conexion;
import Persistencia.MateriaData;      
import Persistencia.InscripcionData;
import java.time.LocalDate;

public class TrabajoPractico7 {

    public static void main(String[] args) {
        
    
        // 1. Configurar y crear la CONEXION (¡Asegúrate que los parámetros sean correctos!)
        Conexion conexion = new Conexion("jdbc:mariadb://localhost:3306/sgulp", "root", "");
    
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

    

