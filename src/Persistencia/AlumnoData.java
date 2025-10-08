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

        }
    }

    public Alumno buscarAlumno(int idAlumno) {
        Alumno a = null;
        String query = "SELECT * FROM alumno WHERE idAlumno = ?";

        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a = new Alumno();
                a.setIdAlumno(rs.getInt("idAlumno"));
                a.setDni(rs.getInt("dni"));
                a.setApellido(rs.getString("apellido"));
                a.setNombre(rs.getString("nombre"));
                a.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                a.setEstado(rs.getBoolean("estado"));
            }
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    public List<Alumno> obtenerTodosLosAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        String query = "SELECT * FROM alumno";

        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
                alumnos.add(alumno);
            }
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return alumnos;
    }
}
