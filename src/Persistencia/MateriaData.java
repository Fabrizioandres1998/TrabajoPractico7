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

}
