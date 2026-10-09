package dao;

import model.Categoria;
import model.Libro;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // =========================================================
    // CREATE - GUARDAR
    // Recibe un libro y lo guarda en la BD
    // =========================================================
    public boolean guardar(Libro libro) {

        String sql = "INSERT INTO libros (titulo, autor, isbn, editorial, stock, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";

        // try-with-resources - abre y cierra automáticamente la conexión
        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // setString / setInt - colocan los datos en los ?
            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getIsbn());
            statement.setString(4, libro.getEditorial());
            statement.setInt(5, libro.getStock());

            // Obtenemos el ID de la categoría para guardar la FK
            statement.setInt(6, libro.getCategoria().getId());

            // executeUpdate - devuelve la cantidad de filas insertadas
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            // Informamos que la operación no se completó
            System.out.println("Error al guardar el libro: " + e.getMessage());
            return false;
        }
    }

    // =========================================================
    // READ - LISTAR
    // Obtiene todos los libros de la BD junto con su categoría
    // =========================================================
    public List<Libro> listar() {

        // ArrayList - lista donde guardaremos los libros
        List<Libro> libros = new ArrayList<>();

        String sql = """
                SELECT l.id, l.titulo, l.autor, l.isbn, l.editorial, l.stock,
                       c.id AS id_categoria, c.nombre AS nombre_categoria
                FROM libros l
                JOIN categorias c ON l.id_categoria = c.id
                """;

        // try-with-resources - abre y cierra automáticamente la conexión
        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // executeQuery - ejecuta SELECT y devuelve un ResultSet
            ResultSet resultado = statement.executeQuery();

            // while - recorre todas las filas obtenidas
            while (resultado.next()) {

                // getInt / getString - obtienen datos de la fila actual
                int id = resultado.getInt("id");
                String titulo = resultado.getString("titulo");
                String autor = resultado.getString("autor");
                String isbn = resultado.getString("isbn");
                String editorial = resultado.getString("editorial");
                int stock = resultado.getInt("stock");

                // Datos de Categoria obtenidos gracias al JOIN
                int idCategoria = resultado.getInt("id_categoria");
                String nombreCategoria = resultado.getString("nombre_categoria");

                // Creamos un objeto Categoria
                Categoria categoria =
                        new Categoria(idCategoria, nombreCategoria);

                // Convertimos la fila de la BD en un objeto Libro
                Libro libro =
                        new Libro(titulo, autor, isbn, editorial, stock, categoria);

                // Asignamos al Libro el ID que viene de la BD
                libro.setId(id);

                // add - agrega el objeto Libro a la lista
                libros.add(libro);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los libros: " + e.getMessage());
        }

        // Devuelve la lista completa
        return libros;
    }

    // =========================================================
    // READ - BUSCAR POR ID
    // Recibe un ID y devuelve un libro
    // Si no existe, devuelve null
    // =========================================================
    public Libro buscarPorId(int id) {

        Libro libro = null;

        String sql = """
                SELECT l.id, l.titulo, l.autor, l.isbn, l.editorial, l.stock,
                       c.id AS id_categoria, c.nombre AS nombre_categoria
                FROM libros l
                JOIN categorias c ON l.id_categoria = c.id
                WHERE l.id = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Colocamos el ID que queremos buscar en el ?
            statement.setInt(1, id);

            // SELECT - executeQuery()
            ResultSet resultado = statement.executeQuery();

            // if - buscamos un solo libro
            if (resultado.next()) {

                int idLibro = resultado.getInt("id");
                String titulo = resultado.getString("titulo");
                String autor = resultado.getString("autor");
                String isbn = resultado.getString("isbn");
                String editorial = resultado.getString("editorial");
                int stock = resultado.getInt("stock");

                // Datos de Categoria obtenidos gracias al JOIN
                int idCategoria = resultado.getInt("id_categoria");
                String nombreCategoria = resultado.getString("nombre_categoria");

                // Creamos el objeto Categoria
                Categoria categoria =
                        new Categoria(idCategoria, nombreCategoria);

                // Creamos el objeto Libro
                libro =
                        new Libro(titulo, autor, isbn, editorial, stock, categoria);

                // Asignamos el ID obtenido desde la BD
                libro.setId(idLibro);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el libro: " + e.getMessage());
        }

        // Devuelve el libro encontrado o null si no existe
        return libro;
    }

    // =========================================================
    // UPDATE - ACTUALIZAR
    // Modifica los datos de un libro existente
    // =========================================================
    public boolean actualizar(Libro libro) {

        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, editorial = ?, stock = ?, id_categoria = ? WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Los primeros 6 ? son los nuevos datos
            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getIsbn());
            statement.setString(4, libro.getEditorial());
            statement.setInt(5, libro.getStock());
            statement.setInt(6, libro.getCategoria().getId());

            // El séptimo ? indica qué libro modificar
            statement.setInt(7, libro.getId());

            // executeUpdate - devuelve la cantidad de filas afectadas
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el libro: " + e.getMessage());
            return false;
        }
    }

    // =========================================================
    // DELETE - ELIMINAR
    // Elimina un libro utilizando su ID
    // =========================================================
    public boolean eliminar(int id) {

        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Primer ? - ID del libro que queremos eliminar
            statement.setInt(1, id);

            // executeUpdate - devuelve la cantidad de filas eliminadas
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el libro: " + e.getMessage());
            return false;
        }
    }
}
