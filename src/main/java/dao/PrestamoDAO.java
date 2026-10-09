package dao;

import model.Prestamo;
import model.Estudiante;
import model.Libro;
import model.Categoria;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    // =========================================================
    // CREATE - GUARDAR
    // Recibe un préstamo y lo guarda en la BD
    // =========================================================
    public void guardar(Prestamo prestamo) {

        String sql = "INSERT INTO prestamos (fecha_prestamo, fecha_devolucion, devuelto, id_estudiante, id_libro) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Guardamos la fecha del préstamo
            statement.setDate(1, java.sql.Date.valueOf(prestamo.getFecha()));

            // La fecha de devolución puede ser NULL
            if (prestamo.getFechaDevolucion() != null) {
                statement.setDate(2, java.sql.Date.valueOf(prestamo.getFechaDevolucion()));
            } else {
                statement.setNull(2, java.sql.Types.DATE);
            }

            // Guardamos el estado del préstamo
            statement.setBoolean(3, prestamo.isDevuelto());

            // Obtenemos los ID del estudiante y del libro para guardar las FK
            statement.setInt(4, prestamo.getEstudiante().getId());
            statement.setInt(5, prestamo.getLibro().getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar el préstamo: " + e.getMessage());
        }
    }

    // =========================================================
    // READ - LISTAR
    // Obtiene todos los préstamos de la BD
    // =========================================================
    public List<Prestamo> listar() {

        List<Prestamo> prestamos = new ArrayList<>();

        String sql = """
                SELECT p.id AS id_prestamo,
                       p.fecha_prestamo,
                       p.fecha_devolucion,
                       p.devuelto,
                       e.id AS id_estudiante,
                       e.nombre AS nombre_estudiante,
                       e.rut AS rut_estudiante,
                       e.curso,
                       e.correo AS correo_estudiante,
                       l.id AS id_libro,
                       l.titulo,
                       l.autor,
                       l.isbn,
                       l.editorial,
                       l.stock,
                       c.id AS id_categoria,
                       c.nombre AS nombre_categoria
                FROM prestamos p
                JOIN estudiantes e ON p.id_estudiante = e.id
                JOIN libros l ON p.id_libro = l.id
                JOIN categorias c ON l.id_categoria = c.id
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                // Datos del préstamo
                int idPrestamo = resultado.getInt("id_prestamo");

                java.time.LocalDate fecha =
                        resultado.getDate("fecha_prestamo").toLocalDate();

                java.sql.Date fechaDevolucionSQL =
                        resultado.getDate("fecha_devolucion");

                java.time.LocalDate fechaDevolucion = null;

                if (fechaDevolucionSQL != null) {
                    fechaDevolucion = fechaDevolucionSQL.toLocalDate();
                }

                boolean devuelto = resultado.getBoolean("devuelto");

                // Datos del estudiante
                int idEstudiante = resultado.getInt("id_estudiante");
                String nombreEstudiante = resultado.getString("nombre_estudiante");
                String rutEstudiante = resultado.getString("rut_estudiante");
                String curso = resultado.getString("curso");
                String correoEstudiante = resultado.getString("correo_estudiante");

                Estudiante estudiante = new Estudiante(
                        idEstudiante,
                        nombreEstudiante,
                        rutEstudiante,
                        curso,
                        correoEstudiante
                );

                // Datos de la categoría
                int idCategoria = resultado.getInt("id_categoria");
                String nombreCategoria = resultado.getString("nombre_categoria");

                Categoria categoria =
                        new Categoria(idCategoria, nombreCategoria);

                // Datos del libro
                int idLibro = resultado.getInt("id_libro");
                String titulo = resultado.getString("titulo");
                String autor = resultado.getString("autor");
                String isbn = resultado.getString("isbn");
                String editorial = resultado.getString("editorial");
                int stock = resultado.getInt("stock");

                Libro libro =
                        new Libro(titulo, autor, isbn, editorial, stock, categoria);

                libro.setId(idLibro);

                // Creamos el préstamo completo
                Prestamo prestamo = new Prestamo(
                        idPrestamo,
                        fecha,
                        fechaDevolucion,
                        estudiante,
                        libro,
                        devuelto
                );

                prestamos.add(prestamo);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los préstamos: " + e.getMessage());
        }

        return prestamos;
    }

    // =========================================================
    // READ - BUSCAR POR ID
    // Busca un préstamo utilizando su ID
    // Si no existe, devuelve null
    // =========================================================
    public Prestamo buscarPorId(int id) {

        Prestamo prestamo = null;

        String sql = """
                SELECT p.id AS id_prestamo,
                       p.fecha_prestamo,
                       p.fecha_devolucion,
                       p.devuelto,
                       e.id AS id_estudiante,
                       e.nombre AS nombre_estudiante,
                       e.rut AS rut_estudiante,
                       e.curso,
                       e.correo AS correo_estudiante,
                       l.id AS id_libro,
                       l.titulo,
                       l.autor,
                       l.isbn,
                       l.editorial,
                       l.stock,
                       c.id AS id_categoria,
                       c.nombre AS nombre_categoria
                FROM prestamos p
                JOIN estudiantes e ON p.id_estudiante = e.id
                JOIN libros l ON p.id_libro = l.id
                JOIN categorias c ON l.id_categoria = c.id
                WHERE p.id = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {

                    // Datos del préstamo
                    int idPrestamo = resultado.getInt("id_prestamo");

                    java.time.LocalDate fecha =
                            resultado.getDate("fecha_prestamo").toLocalDate();

                    java.sql.Date fechaDevolucionSQL =
                            resultado.getDate("fecha_devolucion");

                    java.time.LocalDate fechaDevolucion = null;

                    if (fechaDevolucionSQL != null) {
                        fechaDevolucion = fechaDevolucionSQL.toLocalDate();
                    }

                    boolean devuelto = resultado.getBoolean("devuelto");

                    // Datos del estudiante
                    int idEstudiante = resultado.getInt("id_estudiante");
                    String nombreEstudiante = resultado.getString("nombre_estudiante");
                    String rutEstudiante = resultado.getString("rut_estudiante");
                    String curso = resultado.getString("curso");
                    String correoEstudiante = resultado.getString("correo_estudiante");

                    Estudiante estudiante = new Estudiante(
                            idEstudiante,
                            nombreEstudiante,
                            rutEstudiante,
                            curso,
                            correoEstudiante
                    );

                    // Datos de la categoría
                    int idCategoria = resultado.getInt("id_categoria");
                    String nombreCategoria = resultado.getString("nombre_categoria");

                    Categoria categoria =
                            new Categoria(idCategoria, nombreCategoria);

                    // Datos del libro
                    int idLibro = resultado.getInt("id_libro");
                    String titulo = resultado.getString("titulo");
                    String autor = resultado.getString("autor");
                    String isbn = resultado.getString("isbn");
                    String editorial = resultado.getString("editorial");
                    int stock = resultado.getInt("stock");

                    Libro libro =
                            new Libro(titulo, autor, isbn, editorial, stock, categoria);

                    libro.setId(idLibro);

                    // Creamos el préstamo encontrado
                    prestamo = new Prestamo(
                            idPrestamo,
                            fecha,
                            fechaDevolucion,
                            estudiante,
                            libro,
                            devuelto
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el préstamo: " + e.getMessage());
        }

        return prestamo;
    }

    // =========================================================
    // UPDATE - ACTUALIZAR
    // Modifica los datos de un préstamo existente
    // =========================================================
    public void actualizar(Prestamo prestamo) {

        String sql = "UPDATE prestamos SET fecha_prestamo = ?, fecha_devolucion = ?, devuelto = ?, id_estudiante = ?, id_libro = ? WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(prestamo.getFecha()));

            if (prestamo.getFechaDevolucion() != null) {
                statement.setDate(2, java.sql.Date.valueOf(prestamo.getFechaDevolucion()));
            } else {
                statement.setNull(2, java.sql.Types.DATE);
            }

            statement.setBoolean(3, prestamo.isDevuelto());
            statement.setInt(4, prestamo.getEstudiante().getId());
            statement.setInt(5, prestamo.getLibro().getId());

            // El último ? indica qué préstamo modificar
            statement.setInt(6, prestamo.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar el préstamo: " + e.getMessage());
        }
    }

    // =========================================================
    // DELETE - ELIMINAR
    // Elimina un préstamo utilizando su ID
    // =========================================================
    public void eliminar(int id) {

        String sql = "DELETE FROM prestamos WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar el préstamo: " + e.getMessage());
        }
    }

    // =========================================================
    // REGISTRAR PRÉSTAMO CON TRANSACCIÓN
    // Guarda el préstamo y descuenta stock en una misma conexión
    // =========================================================
    public boolean registrarPrestamoConStock(Prestamo prestamo) {

        String consultarStock =
                "SELECT stock FROM libros WHERE id = ? FOR UPDATE";

        String insertarPrestamo =
                "INSERT INTO prestamos (fecha_prestamo, fecha_devolucion, devuelto, id_estudiante, id_libro) VALUES (?, ?, ?, ?, ?)";

        String descontarStock =
                "UPDATE libros SET stock = stock - 1 WHERE id = ? AND stock > 0";

        try (Connection conexion = DatabaseConnection.conectar()) {

            // Desactivamos el guardado automático para controlar la transacción
            conexion.setAutoCommit(false);

            try {

                int idLibro = prestamo.getLibro().getId();

                // Bloqueamos la fila del libro mientras comprobamos el stock
                try (PreparedStatement consulta =
                             conexion.prepareStatement(consultarStock)) {

                    consulta.setInt(1, idLibro);

                    try (ResultSet resultado = consulta.executeQuery()) {

                        if (!resultado.next()
                                || resultado.getInt("stock") <= 0) {

                            conexion.rollback();
                            return false;
                        }
                    }
                }

                // Registramos el préstamo en la misma conexión
                try (PreparedStatement insertar =
                             conexion.prepareStatement(insertarPrestamo)) {

                    insertar.setDate(
                            1,
                            java.sql.Date.valueOf(prestamo.getFecha())
                    );

                    if (prestamo.getFechaDevolucion() == null) {
                        insertar.setNull(2, java.sql.Types.DATE);
                    } else {
                        insertar.setDate(
                                2,
                                java.sql.Date.valueOf(
                                        prestamo.getFechaDevolucion()
                                )
                        );
                    }

                    insertar.setBoolean(3, prestamo.isDevuelto());
                    insertar.setInt(4, prestamo.getEstudiante().getId());
                    insertar.setInt(5, idLibro);

                    if (insertar.executeUpdate() != 1) {
                        conexion.rollback();
                        return false;
                    }
                }

                // Descontamos una unidad del stock
                try (PreparedStatement actualizar =
                             conexion.prepareStatement(descontarStock)) {

                    actualizar.setInt(1, idLibro);

                    if (actualizar.executeUpdate() != 1) {
                        conexion.rollback();
                        return false;
                    }
                }

                // Confirmamos las dos operaciones juntas
                conexion.commit();
                return true;

            } catch (SQLException e) {

                // Revertimos ambas operaciones si alguna falla
                conexion.rollback();

                System.out.println(
                        "Error al registrar el préstamo: " + e.getMessage()
                );

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexión al registrar el préstamo: " + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // REGISTRAR DEVOLUCIÓN CON TRANSACCIÓN
    // Marca el préstamo como devuelto y recupera el stock
    // =========================================================
    public boolean registrarDevolucionConStock(int idPrestamo) {

        String consultarPrestamo =
                "SELECT id_libro, devuelto FROM prestamos WHERE id = ? FOR UPDATE";

        String actualizarPrestamo =
                "UPDATE prestamos SET fecha_devolucion = CURRENT_DATE(), devuelto = TRUE WHERE id = ? AND devuelto = FALSE";

        String recuperarStock =
                "UPDATE libros SET stock = stock + 1 WHERE id = ?";

        try (Connection conexion = DatabaseConnection.conectar()) {

            // Desactivamos el guardado automático
            conexion.setAutoCommit(false);

            try {

                int idLibro;

                // Bloqueamos el préstamo para impedir devoluciones duplicadas
                try (PreparedStatement consulta =
                             conexion.prepareStatement(consultarPrestamo)) {

                    consulta.setInt(1, idPrestamo);

                    try (ResultSet resultado = consulta.executeQuery()) {

                        if (!resultado.next()
                                || resultado.getBoolean("devuelto")) {

                            conexion.rollback();
                            return false;
                        }

                        idLibro = resultado.getInt("id_libro");
                    }
                }

                // Registramos la devolución
                try (PreparedStatement actualizar =
                             conexion.prepareStatement(actualizarPrestamo)) {

                    actualizar.setInt(1, idPrestamo);

                    if (actualizar.executeUpdate() != 1) {
                        conexion.rollback();
                        return false;
                    }
                }

                // Recuperamos una unidad del stock
                try (PreparedStatement actualizar =
                             conexion.prepareStatement(recuperarStock)) {

                    actualizar.setInt(1, idLibro);

                    if (actualizar.executeUpdate() != 1) {
                        conexion.rollback();
                        return false;
                    }
                }

                // Confirmamos ambas operaciones
                conexion.commit();
                return true;

            } catch (SQLException e) {

                // Revertimos ambas operaciones si alguna falla
                conexion.rollback();

                System.out.println(
                        "Error al registrar la devolución: " + e.getMessage()
                );

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexión al registrar la devolución: " + e.getMessage()
            );

            return false;
        }
    }
}
