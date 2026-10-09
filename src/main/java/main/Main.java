package main;

import controller.UsuarioController;
import dao.UsuarioDAO;
import view.LoginView;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        // Inicia la interfaz gráfica de Swing
        SwingUtilities.invokeLater(() -> {

            // DAO encargado de comunicarse con la BD
            UsuarioDAO usuarioDAO =
                    new UsuarioDAO();

            // Controller que conecta LoginView con UsuarioDAO
            UsuarioController usuarioController =
                    new UsuarioController(usuarioDAO);

            // Crear ventana de inicio de sesión
            LoginView loginView =
                    new LoginView(usuarioController);

            // Mostrar ventana
            loginView.setVisible(true);
        });
    }
}