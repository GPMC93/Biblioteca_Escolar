package dao;

import model.Rol;
import model.Usuario;
import model.UsuarioBibliotecario;
import model.UsuarioEstudiante;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void guardar(Usuario usuario) {

        String sql = """
                INSERT INTO usuarios
                (nombre, rut, correo, contraseña, rol)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getRut());
            statement.setString(3, usuario.getCorreo());
            statement.setString(4, usuario.getContrasena());
            statement.setString(
                    5,
                    usuario.getRol().name().toLowerCase()
            );

            statement.executeUpdate();

            ResultSet clavesGeneradas =
                    statement.getGeneratedKeys();

            if (clavesGeneradas.next()) {
                usuario.setId(
                        clavesGeneradas.getInt(1)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar usuario: "
                            + e.getMessage()
            );
        }
    }


    public List<Usuario> listar() {

        List<Usuario> usuarios =
                new ArrayList<>();

        String sql = """
                SELECT id, nombre, rut, correo, contraseña, rol
                FROM usuarios
                """;

        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql);

             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                Usuario usuario =
                        crearUsuarioDesdeResultado(
                                resultado
                        );

                usuarios.add(usuario);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar usuarios: "
                            + e.getMessage()
            );
        }

        return usuarios;
    }


    public Usuario buscarPorId(int idBuscado) {

        Usuario usuario = null;

        String sql = """
                SELECT id, nombre, rut, correo, contraseña, rol
                FROM usuarios
                WHERE id = ?
                """;

        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    idBuscado
            );

            ResultSet resultado =
                    statement.executeQuery();

            if (resultado.next()) {

                usuario =
                        crearUsuarioDesdeResultado(
                                resultado
                        );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar usuario: "
                            + e.getMessage()
            );
        }

        return usuario;
    }


    public void actualizar(Usuario usuario) {

        String sql = """
                UPDATE usuarios
                SET nombre = ?,
                    rut = ?,
                    correo = ?,
                    contraseña = ?,
                    rol = ?
                WHERE id = ?
                """;

        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(
                    1,
                    usuario.getNombre()
            );

            statement.setString(
                    2,
                    usuario.getRut()
            );

            statement.setString(
                    3,
                    usuario.getCorreo()
            );

            statement.setString(
                    4,
                    usuario.getContrasena()
            );

            statement.setString(
                    5,
                    usuario.getRol()
                            .name()
                            .toLowerCase()
            );

            statement.setInt(
                    6,
                    usuario.getId()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar usuario: "
                            + e.getMessage()
            );
        }
    }


    public void eliminar(int id) {

        String sql =
                "DELETE FROM usuarios WHERE id = ?";

        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    id
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar usuario: "
                            + e.getMessage()
            );
        }
    }


    public Usuario autenticar(
            String correo,
            String contrasena) {

        Usuario usuario = null;

        String sql = """
                SELECT id, nombre, rut, correo, contraseña, rol
                FROM usuarios
                WHERE correo = ?
                AND contraseña = ?
                """;

        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(
                    1,
                    correo
            );

            statement.setString(
                    2,
                    contrasena
            );

            ResultSet resultado =
                    statement.executeQuery();

            if (resultado.next()) {

                usuario =
                        crearUsuarioDesdeResultado(
                                resultado
                        );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al autenticar usuario: "
                            + e.getMessage()
            );
        }

        return usuario;
    }


    /*
     * Construye el tipo concreto de usuario según
     * el rol almacenado en la base de datos.
     *
     * Aquí se utiliza polimorfismo:
     * la variable es de tipo Usuario, pero puede
     * contener un UsuarioBibliotecario o un
     * UsuarioEstudiante.
     */
    private Usuario crearUsuarioDesdeResultado(
            ResultSet resultado)
            throws SQLException {

        int id =
                resultado.getInt("id");

        String nombre =
                resultado.getString("nombre");

        String rut =
                resultado.getString("rut");

        String correo =
                resultado.getString("correo");

        String contrasena =
                resultado.getString("contraseña");

        Rol rol =
                Rol.valueOf(
                        resultado
                                .getString("rol")
                                .toUpperCase()
                );

        if (rol == Rol.BIBLIOTECARIO) {

            return new UsuarioBibliotecario(
                    id,
                    nombre,
                    rut,
                    correo,
                    contrasena,
                    rol
            );

        } else {

            return new UsuarioEstudiante(
                    id,
                    nombre,
                    rut,
                    correo,
                    contrasena,
                    rol
            );
        }
    }
}