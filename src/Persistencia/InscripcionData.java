package Persistencia;

import Modelo.Alumno;
import Modelo.Materia;
import Modelo.Inscripcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Conexion;
import Persistencia.AlumnoData;
import Persistencia.MateriaData;

public class InscripcionData {

    private Connection conex = null;

    public InscripcionData(Conexion con) {
        this.conex = con.buscarConexion();
    }

    public void guardarInscripcion(Inscripcion i) {
        String query = "INSERT INTO inscripcion(nota, idAlumno, idMateria) VALUES (?, ?, ?)"; //armar query

        try {
            PreparedStatement ps = conex.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); //crear PreparedStatement ps para ejecutar consultas sql en una bd reemplazando los valores con parametros
            ps.setInt(1, i.getNota()); //reemplazar los comodines del query
            ps.setInt(2, i.getAlumno().getIdAlumno());
            ps.setInt(3, i.getMateria().getIdMateria());
            ps.executeUpdate(); //ejecutar

            ResultSet rs = ps.getGeneratedKeys(); //recupero y asigno
            if (rs.next()) {
                i.setIdInscripto(rs.getInt(1)); //asigno inscripcion el id que viene de resultset 
            } else {
                System.out.println("No se pudo obtener el ID");
            }
            ps.close();
            System.out.println("Guardado");
        } catch (SQLException ex) {
            System.err.println("Error al guardar la inscripcion: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void actualizarInscripcion(Inscripcion i) {
        String query = "UPDATE inscripcion SET nota = ?, idAlumno = ?, idMateria = ? WHERE idInscripto = ?"; //armar query

        try {
            PreparedStatement ps = conex.prepareStatement(query); //crear PreparedStatement ps
            ps.setInt(1, i.getNota()); //reemplazar los comodines del query
            ps.setInt(2, i.getAlumno().getIdAlumno());
            ps.setInt(3, i.getMateria().getIdMateria());
            ps.setInt(4, i.getIdInscripto());
            ps.executeUpdate(); //ejecutar

            ps.close();
            System.out.println("Actualizado");
        } catch (SQLException ex) {
            System.err.println("Error al actualizar la inscripcion: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void bajafisicaInscripcion(Inscripcion i) {
        String query = "DELETE FROM inscripcion WHERE idInscripto = ?"; //armar query

        try {
            PreparedStatement ps = conex.prepareStatement(query); //crear PreparedStatement ps
            ps.setInt(1, i.getIdInscripto());
            ps.executeUpdate(); //ejecutar

            ps.close();
            System.out.println("Eliminado");
        } catch (SQLException ex) {
            System.err.println("Error al eliminar la inscripcion: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Inscripcion buscarInscripcion(int idInscripto) {
        Inscripcion i = null;
        String query = "SELECT i.*, m.nombre as nombre_materia, m.año, m.estado as estado_materia, "
                + "a.nombre as nombre_alumno, a.apellido as alumno_apellido "
                + "FROM inscripcion i "
                + "JOIN materia m ON i.idMateria = m.idMateria "
                + "JOIN alumno a ON i.idAlumno = a.idAlumno "
                + "WHERE i.idInscripto = ?";

        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ps.setInt(1, idInscripto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                i = new Inscripcion();
                i.setIdInscripto(rs.getInt("idInscripto"));
                i.setNota(rs.getInt("nota"));

                // Alumno completo
                Alumno a = new Alumno();
                a.setIdAlumno(rs.getInt("idAlumno"));
                a.setNombre(rs.getString("nombre_alumno"));
                a.setApellido(rs.getString("alumno_apellido"));
                i.setAlumno(a);

                // Materia completa con nombre
                Materia m = new Materia();
                m.setIdMateria(rs.getInt("idMateria"));
                m.setNombre(rs.getString("nombre_materia")); // ✅ ¡Nombre incluido!
                m.setAño(rs.getInt("año"));
                m.setEstado(rs.getBoolean("estado_materia"));
                i.setMateria(m);
            }
            ps.close();
        } catch (SQLException ex) {
            System.err.println("Error al buscar la inscripcion: " + ex.getMessage());
            ex.printStackTrace();
        }
        return i;
    }

    public List<Inscripcion> obtenerTodasLasInscripciones() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT i.*, m.nombre as nombre_materia, m.año, m.estado as estado_materia, "
                + "a.nombre as nombre_alumno, a.apellido as alumno_apellido "
                + "FROM inscripcion i "
                + "JOIN materia m ON i.idMateria = m.idMateria "
                + "JOIN alumno a ON i.idAlumno = a.idAlumno";

        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripto(rs.getInt("idInscripto"));
                inscripcion.setNota(rs.getInt("nota"));

                // Alumno con datos completos
                Alumno a = new Alumno();
                a.setIdAlumno(rs.getInt("idAlumno"));
                a.setNombre(rs.getString("nombre_alumno"));
                a.setApellido(rs.getString("alumno_apellido"));
                inscripcion.setAlumno(a);

                // Materia con datos completos (¡INCLUYENDO NOMBRE!)
                Materia m = new Materia();
                m.setIdMateria(rs.getInt("idMateria"));
                m.setNombre(rs.getString("nombre_materia")); // ✅ ¡Aquí está el nombre!
                m.setAño(rs.getInt("año"));
                m.setEstado(rs.getBoolean("estado_materia"));
                inscripcion.setMateria(m);

                inscripciones.add(inscripcion);
            }
            ps.close();
            System.out.println("Lista de inscripciones obtenida correctamente");
        } catch (SQLException ex) {
            System.err.println("Error al obtener las inscripciones: " + ex.getMessage());
            ex.printStackTrace();
        }
        return inscripciones;
    }
}
