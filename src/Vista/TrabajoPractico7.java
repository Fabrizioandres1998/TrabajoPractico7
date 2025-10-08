package Vista;

import Modelo.Alumno;
import Persistencia.AlumnoData;
import modelo.Conexion;
import java.time.LocalDate;
import java.util.Scanner;

public class TrabajoPractico7 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configurar conexión
        Conexion conexion = new Conexion("jdbc:mariadb://localhost:3306/sgulp", "root", "");
        AlumnoData alumnoData = new AlumnoData(conexion);
        
         // Crear e iniciar la ventana principal
        SGULP menuPrincipal = new SGULP();
        menuPrincipal.setVisible(true);
        menuPrincipal.setLocationRelativeTo(null); // <-- centra la ventana

        // Preguntar primero si quiere ingresar alumnos
        System.out.print("¿Desea ingresar alumnos? (s/n): ");
        String respuesta = scanner.nextLine();

        while (respuesta.equalsIgnoreCase("s")) {
            System.out.print("DNI: ");
            int dni = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Fecha nacimiento (YYYY-MM-DD): ");
            LocalDate fechaNacimiento = LocalDate.parse(scanner.nextLine());

            // Crear y guardar alumno
            Alumno alumno = new Alumno(dni, apellido, nombre, fechaNacimiento, true);
            alumnoData.guardarAlumno(alumno);

            System.out.println("Alumno guardado con ID: " + alumno.getIdAlumno());

            // Preguntar si quiere agregar otro alumno
            System.out.print("\n¿Desea agregar otro alumno? (s/n): ");
            respuesta = scanner.nextLine();
        }

        // Opción para borrar alumno
        System.out.print("\n¿Desea borrar un alumno? (s/n): ");
        String borrar = scanner.nextLine();

        if (borrar.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el ID del alumno a borrar: ");
            int idBorrar = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            Alumno alumnoBorrar = alumnoData.buscarAlumno(idBorrar);

            if (alumnoBorrar != null) {
                System.out.println("Alumno a borrar:");
                System.out.println("ID: " + alumnoBorrar.getIdAlumno());
                System.out.println("Nombre: " + alumnoBorrar.getNombre());
                System.out.println("Apellido: " + alumnoBorrar.getApellido());
                System.out.println("DNI: " + alumnoBorrar.getDni());
            } else {
                System.out.println("No se encontró ningún alumno con ID: " + idBorrar);
            }
        }

        // Buscar un alumno por ID
        System.out.print("\nBuscar alumno por ID: ");
        int idBuscar = scanner.nextInt();
        Alumno alumnoEncontrado = alumnoData.buscarAlumno(idBuscar);

        if (alumnoEncontrado != null) {
            System.out.println("Alumno encontrado:");
            System.out.println("Nombre: " + alumnoEncontrado.getNombre());
            System.out.println("Apellido: " + alumnoEncontrado.getApellido());
            System.out.println("DNI: " + alumnoEncontrado.getDni());
        } else {
            System.out.println("Alumno no encontrado");
        }

        scanner.close();
    }
}
