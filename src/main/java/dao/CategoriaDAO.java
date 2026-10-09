package dao;

import model.Categoria;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * DAO de Categoria
 * Se encarga de las operaciones CRUD de Categoria con la base de datos
 *
 * CRUD:
 * CREATE → guardar()
 * READ   → listar() / buscarPorId()
 * UPDATE → actualizar()
 * DELETE → eliminar()
 */
public class CategoriaDAO {

    // =========================================================
    // CREATE - GUARDAR
    // Recibe una Categoria y la guarda en la BD
    // =========================================================
    public void guardar(Categoria categoria) {

        String sql = "INSERT INTO categorias (nombre) VALUES (?)";

        // try-with-resources - abre y cierra automáticamente la conexión
        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // setString - coloca un String en el ?
            statement.setString(1, categoria.getNombre());

            // executeUpdate - ejecuta INSERT, UPDATE o DELETE
            statement.executeUpdate();

        } catch (SQLException e) {

            // SQLException - error relacionado con la BD
            System.out.println("Error al guardar la categoría: " + e.getMessage());
        }
    }

    // =========================================================
    // READ - LISTAR
    // Obtiene TODAS las categorías de la BD
    // =========================================================
    public List<Categoria> listar() {

        // ArrayList - lista donde guardaremos las categorías encontradas
        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT id, nombre FROM categorias";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // executeQuery - ejecuta SELECT y devuelve un ResultSet
            ResultSet resultado = statement.executeQuery();

            // while - recorre todas las filas encontradas
            while (resultado.next()) {

                // getInt / getString → obtienen datos de la fila actual
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");

                // Convierte la fila de la BD en un objeto Categoria
                Categoria categoria = new Categoria(id, nombre);

                // add - agrega el objeto a la lista
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar las categorías: " + e.getMessage());
        }

        // Devuelve la lista completa
        return categorias;
    }

    // =========================================================
    // READ - BUSCAR POR s filas ID
    // Recibe un ID y devuelve UNA Categoria
    // Si no existe, devuelve null
    // =========================================================
    public Categoria buscarPorId(int id) {

        Categoria categoria = null;

        String sql = "SELECT id, nombre FROM categorias WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // setInt - coloca el ID en el ?
            statement.setInt(1, id);

            // SELECT - executeQuery()
            ResultSet resultado = statement.executeQuery();

            // if - buscamos una sola categoría
            if (resultado.next()) {

                String nombre = resultado.getString("nombre");

                // Convierte la fila encontrada en un objeto Categoria
                categoria = new Categoria(id, nombre);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar la categoría: " + e.getMessage());
        }

        // Devuelve la categoría encontrada o null
        return categoria;
    }

    // =========================================================
    // UPDATE - ACTUALIZAR
    // Modifica el nombre de una categoría existente
    // =========================================================
    public void actualizar(Categoria categoria) {

        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Primer ? - nuevo nombre
            statement.setString(1, categoria.getNombre());

            // Segundo ? - ID de la categoría que queremos modificar
            statement.setInt(2, categoria.getId());

            // UPDATE - executeUpdate()
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar la categoría: " + e.getMessage());
        }
    }

    // =========================================================
    // DELETE - ELIMINAR
    // Elimina una categoría utilizando su ID
    // =========================================================
    public void eliminar(int id) {

        String sql = "DELETE FROM categorias WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Primer ? - ID de la categoría que queremos eliminar
            statement.setInt(1, id);

            // DELETE - executeUpdate()
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar la categoría: " + e.getMessage());
        }
    }
}