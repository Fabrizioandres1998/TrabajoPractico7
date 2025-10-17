package Persistencia;

import Modelo.Alumno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Conexion;

public class AlumnoData {

    private Connection conex = null;

    public AlumnoData(Conexion con) {
        this.conex = con.buscarConexion();
    }

    public AlumnoData() {
    }
    

    public void guardarAlumno(Alumno a) {
        String query = "INSERT INTO alumno(dni, apellido, nombre, fechaNacimiento, estado) VALUES (?, ?, ?, ?, ?)"; //armar query

        try {
            PreparedStatement ps = conex.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); //crear PreparedStatement ps para ejecutar consultas sql en una bd reemplazando los valroes con parametros
            ps.setInt(1, a.getDni()); //reemplazar los comodines del query
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getNombre());
            ps.setDate(4, java.sql.Date.valueOf(a.getFechaNacimiento()));
            ps.setBoolean(5, a.getEstado());
            ps.executeUpdate(); //ejecutar

            ResultSet rs = ps.getGeneratedKeys(); //recupero y asigno
            if (rs.next()) {
                a.setIdAlumno(rs.getInt(1)); //asigno alumno el id que viene de resultset 
            } else {
                System.out.println("No se pudo obtener el ID");
            }
            ps.close();
            System.out.println("Guardado");
        } catch (SQLException ex) {
            System.err.println("Error al guardar el alumno: " + ex.getMessage());
            ex.printStackTrace(); // imprime detalles del error
        } catch (NullPointerException ex) {
            System.err.println("Error: La fecha de nacimiento es nula.");
            ex.printStackTrace();
        }
    }

    public void actualizarAlumno(Alumno a) {
        String query = "UPDATE alumno SET dni=?,apellido=?,nombre=?,fechaNacimiento=?,estado= ? WHERE idAlumno=?"; //armar query

        try {
            PreparedStatement ps = conex.prepareStatement(query); //crear PreparedStatement ps
            ps.setInt(1, a.getDni()); //reemplazar los comodines del query
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getNombre());
            ps.setDate(4, java.sql.Date.valueOf(a.getFechaNacimiento()));
            ps.setBoolean(5, a.getEstado());
            ps.setInt(6, a.getIdAlumno());
            ps.executeUpdate(); //ejecutar

            ps.close();
            System.out.println("Actualizado");
        } catch (SQLException ex) {
            System.err.println("Error al actualizar el alumno: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void bajafisicaAlumno(Alumno a) {
        String query = "DELETE FROM alumno WHERE idAlumno = ?"; //armar query

        try {
            PreparedStatement ps = conex.prepareStatement(query); //crear PreparedStatement ps
            ps.setInt(1, a.getIdAlumno());
            ps.executeUpdate(); //ejecutar

            ps.close();
            System.out.println("Eliminado");
        } catch (SQLException ex) {
            System.err.println("Error al eliminar el alumno: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void bajalogicaAlumno(Alumno a) {
        String query = "UPDATE alumno SET estado = false WHERE idAlumno = ?"; //armar query para cambiar el estado

        try {
            PreparedStatement ps = conex.prepareStatement(query); //crear PreparedStatement ps
            ps.setInt(1, a.getIdAlumno()); //reemplazar el comodin con el id del alumno
            ps.executeUpdate(); //ejecutar
            ps.close();
            System.out.println("Baja logica realizada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al realizar la baja logica: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void altalogicaAlumno(Alumno a) {
        String query = "UPDATE alumno SET estado = true WHERE idAlumno = ?"; //armar query para volver a activar el alumno

        try {
            PreparedStatement ps = conex.prepareStatement(query); //crear PreparedStatement ps
            ps.setInt(1, a.getIdAlumno()); //reemplazar el comodin con el id del alumno
            ps.executeUpdate(); //ejecutar
            ps.close();
            System.out.println("Alta logica realizada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al realizar la alta logica: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // MÉTODO PARA BAJA LÓGICA (Inactivar Alumno)
public void borrarAlumnoLogico(int id) {
    // La consulta SQL cambia el estado a 0 (false) para el ID de alumno específico.
    String query = "UPDATE alumno SET estado = 0 WHERE idAlumno = ?"; 
    
    try {
        //Prepara la sentencia
        PreparedStatement ps = conex.prepareStatement(query);
        ps.setInt(1, id); 
        
        //Ejecuta la actualización
        int filasAfectadas = ps.executeUpdate(); 
        
        if (filasAfectadas > 0) {
            System.out.println("Alumno con ID " + id + " dado de baja lógicamente (Estado: Inactivo).");
        } else {
            System.err.println("No se encontró ningún alumno con ID " + id + " para dar de baja.");
        }
        ps.close();
        
    } catch (SQLException ex) {
        System.err.println("Error al realizar la baja lógica del alumno: " + ex.getMessage());
        ex.printStackTrace();
    }
}

// MÉTODO COMPLEMENTARIO PARA ALTA LÓGICA (Activar Alumno)
public void activarAlumno(int id) {
    
    String query = "UPDATE alumno SET estado = 1 WHERE idAlumno = ?"; 
    
    try {
        PreparedStatement ps = conex.prepareStatement(query);
        ps.setInt(1, id);
        
        int filasAfectadas = ps.executeUpdate(); 
        
        if (filasAfectadas > 0) {
            System.out.println("Alumno con ID " + id + " dado de alta lógicamente (Estado: Activo).");
        } else {
            System.err.println("No se encontró ningún alumno con ID " + id + " para dar de alta.");
        }
        ps.close();
        
    } catch (SQLException ex) {
        System.err.println("Error al realizar el alta lógica del alumno: " + ex.getMessage());
        ex.printStackTrace();
    }
}

public void borrarAlumno(int id) {
        // Query SQL para eliminar el registro de la tabla alumno por su ID
        String query = "DELETE FROM alumno WHERE idAlumno = ?"; 
        
        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ps.setInt(1, id); // Establece el ID del alumno a borrar
            
            int filasAfectadas = ps.executeUpdate(); // Ejecuta la consulta
            
            if (filasAfectadas > 0) {
                System.out.println("Alumno eliminado físicamente (ID: " + id + ")");
            } else {
                System.out.println("No se encontró el alumno con ID: " + id + " para borrar.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            System.err.println("Error al borrar el alumno: " + ex.getMessage());
            ex.printStackTrace();
            
            throw new RuntimeException("Error de BD al borrar el alumno."); 
        }
    }

    public Alumno buscarAlumno(int idAlumno) {
        Alumno a = null;
        String query = "SELECT * FROM alumno WHERE idAlumno = ?"; //armar query

        try {
            PreparedStatement ps = conex.prepareStatement(query); //crear PreparedStatement ps
            ps.setInt(1, idAlumno); //reemplazar comodin con el id del alumno
            ResultSet rs = ps.executeQuery(); //ejecutar y obtener resultados

            if (rs.next()) { //si encuentra el alumno
                a = new Alumno(); //crear nuevo objeto alumno
                a.setIdAlumno(rs.getInt("idAlumno"));
                a.setDni(rs.getInt("dni"));
                a.setApellido(rs.getString("apellido"));
                a.setNombre(rs.getString("nombre"));
                a.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                a.setEstado(rs.getBoolean("estado"));
            }
            ps.close();
        } catch (SQLException ex) {
            System.err.println("Error al buscar el alumno: " + ex.getMessage());
            ex.printStackTrace();
        }
        return a;
    }

    public List<Alumno> obtenerTodosLosAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        String query = "SELECT * FROM alumno"; //armar query

        try {
            PreparedStatement ps = conex.prepareStatement(query); //crear PreparedStatement ps
            ResultSet rs = ps.executeQuery(); //ejecutar consulta y obtener resultados

            while (rs.next()) { //recorrer los resultados
                Alumno alumno = new Alumno(); //crear objeto alumno
                alumno.setIdAlumno(rs.getInt("idAlumno")); //asignar valores al objeto
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
                alumnos.add(alumno); //agregar alumno a la lista
            }
            ps.close();
        } catch (SQLException ex) {
            System.err.println("Error al obtener todos los alumnos: " + ex.getMessage());
            ex.printStackTrace();
        }
        return alumnos;
    }
}
