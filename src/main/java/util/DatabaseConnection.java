
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;

    private static final String URL =
            "jdbc:mysql://localhost:3306/biblioteca";

    private static final String USER = "root";

    // La contraseña se obtiene desde una variable de entorno
    // para evitar publicarla dentro del código fuente
    private static final String PASSWORD =
            System.getenv("BIBLIOTECA_DB_PASSWORD");

    // Constructor privado
    // Impide crear instancias desde otras clases
    private DatabaseConnection() {
    }

    // Patrón Singleton
    // Devuelve siempre la misma instancia de DatabaseConnection
    public static DatabaseConnection getInstance() {

        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    // Establece la conexión JDBC con MySQL
    public Connection obtenerConexion() throws SQLException {

        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException(
                    "No está configurada la variable BIBLIOTECA_DB_PASSWORD"
            );
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    // Conserva las llamadas existentes desde los DAO
    public static Connection conectar() throws SQLException {

        return getInstance().obtenerConexion();
    }
}
