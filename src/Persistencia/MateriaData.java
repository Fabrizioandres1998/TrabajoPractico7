package Persistencia;

import Modelo.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Conexion;

public class MateriaData {

    private Connection conex = null;

    public MateriaData(Conexion c) {
        this.conex = c.buscarConexion();
    }

    public void guardarMateria(Materia m) {
        String query = "INSERT INTO materia(nombre, año, estado) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conex.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getAño());
            ps.setBoolean(3, m.getEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                m.setIdMateria(rs.getInt(1));
            } else {
                System.out.println("No se pudo obtener el ID");
            }
            ps.close();
        } catch (SQLException ex) {
            System.err.println("Error al guardar la materia: " + ex.getMessage());
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.err.println("Error: Algún dato de la materia es nulo.");
            ex.printStackTrace();
        }
    }

    public void actualizarMateria(Materia m) {
        String query = "UPDATE materia SET nombre = ?, año = ?, estado = ? WHERE idMateria = ?";

        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getAño());
            ps.setBoolean(3, m.getEstado());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            System.err.println("Error al actualizar la materia: " + ex.getMessage());
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.err.println("Error: Algún dato de la materia es nulo.");
            ex.printStackTrace();
        }
    }

    public void bajafisicaMateria(Materia m) {
        String query = "DELETE FROM materia WHERE idMateria = ?";

        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ps.setInt(1, m.getIdMateria());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("Error al eliminar la materia: " + ex.getMessage());
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.err.println("Error: Algún dato de la materia es nulo.");
            ex.printStackTrace();
        }
    }

    public void bajalogicaMateria(Materia m) {
        String query = "UPDATE alumno SET estado = false WHERE idAlumno = ?";

        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ps.setInt(1, m.getIdMateria());
            ps.executeUpdate();
            ps.close();
            System.out.println("Baja logica realizada correctamente.");
        } catch (SQLException ex) {
            System.err.println("Error al realizar la baja logica: " + ex.getMessage());
        }
    }
    
        public void altalogicaMateria(Materia m) {
        String query = "UPDATE alumno SET estado = true WHERE idAlumno = ?";

        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ps.setInt(1, m.getIdMateria());
            ps.executeUpdate();
            ps.close();
            System.out.println("Alta logica realizada correctamente.");
        } catch (SQLException ex) {
            System.err.println("Error al realizar la alta logica: " + ex.getMessage());
        }
    }

    public void buscarMateria(int idMateria) {
        Materia m = null;
        String query = "SELECT * FROM materia WHERE idMateria = ?";

        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ps.setInt(1, idMateria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                m = new Materia();
                m.setIdMateria(rs.getInt("idMateria"));
                m.setNombre(rs.getString(("nombre")));
                m.setAño(rs.getInt("año"));
                m.setEstado(rs.getBoolean("estado"));
            }
            ps.close();

        } catch (Exception e) {
        }
    }
    
        public List<Materia> obtenerTodasLasMaterias() {
        List<Materia> materias = new ArrayList<>();
        String query = "SELECT * FROM materia";

        try {
            PreparedStatement ps = conex.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAño(rs.getInt("año"));
                materia.setEstado(rs.getBoolean("estado"));
                materias.add(materia);
            }
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return materias;
    }
}
