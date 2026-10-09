
package dao;

import model.Estudiante;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    // CREATE - GUARDAR
    public boolean guardar(Estudiante estudiante) {

        String sql = "INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, estudiante.getNombre());
            statement.setString(2, estudiante.getRut());
            statement.setString(3, estudiante.getCurso());
            statement.setString(4, estudiante.getCorreo());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar el estudiante: " + e.getMessage());
            return false;
        }
    }

    // READ - LISTAR
    public List<Estudiante> listar() {

        List<Estudiante> estudiantes = new ArrayList<>();

        String sql = "SELECT id, nombre, rut, curso, correo FROM estudiantes";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String rut = resultado.getString("rut");
                String curso = resultado.getString("curso");
                String correo = resultado.getString("correo");

                Estudiante estudiante =
                        new Estudiante(id, nombre, rut, curso, correo);

                estudiantes.add(estudiante);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los estudiantes: " + e.getMessage());
        }

        return estudiantes;
    }

    // READ - BUSCAR POR ID
    public Estudiante buscarPorId(int id) {

        Estudiante estudiante = null;

        String sql = "SELECT id, nombre, rut, curso, correo FROM estudiantes WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {

                    String nombre = resultado.getString("nombre");
                    String rut = resultado.getString("rut");
                    String curso = resultado.getString("curso");
                    String correo = resultado.getString("correo");

                    estudiante = new Estudiante(id, nombre, rut, curso, correo);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el estudiante: " + e.getMessage());
        }

        return estudiante;
    }

    // UPDATE - ACTUALIZAR
    public boolean actualizar(Estudiante estudiante) {

        String sql = "UPDATE estudiantes SET nombre = ?, rut = ?, curso = ?, correo = ? WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, estudiante.getNombre());
            statement.setString(2, estudiante.getRut());
            statement.setString(3, estudiante.getCurso());
            statement.setString(4, estudiante.getCorreo());
            statement.setInt(5, estudiante.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el estudiante: " + e.getMessage());
            return false;
        }
    }

    // DELETE - ELIMINAR
    public boolean eliminar(int id) {

        String sql = "DELETE FROM estudiantes WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el estudiante: " + e.getMessage());
            return false;
        }
    }
}
