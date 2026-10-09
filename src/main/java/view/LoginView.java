package view;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    private UsuarioController usuarioController;

    public LoginView(UsuarioController usuarioController) {

        this.usuarioController = usuarioController;

        // Configuración de la ventana
        setTitle("Biblioteca Escolar - Inicio de Sesión");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        // Componentes
        JLabel lblCorreo = new JLabel("Correo:");
        JLabel lblContrasena = new JLabel("Contraseña:");

        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();
        btnIngresar = new JButton("Iniciar sesión");

        // Agregar componentes al panel
        panel.add(lblCorreo);
        panel.add(txtCorreo);

        panel.add(lblContrasena);
        panel.add(txtContrasena);

        panel.add(new JLabel());
        panel.add(btnIngresar);

        // Agregar panel a la ventana
        add(panel);

        // Acción del botón
        btnIngresar.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {

        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        // Validar campos vacíos
        if (correo.isEmpty() || contrasena.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar correo y contraseña.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Pedir al Controller que autentique al usuario
        Usuario usuario =
                usuarioController.autenticarUsuario(correo, contrasena);

        // Comprobar resultado
        if (usuario != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido/a " + usuario.getNombre(),
                    "Inicio de sesión exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Cerrar Login
            dispose();

            // Abrir menú principal y entregar el usuario autenticado
            MenuPrincipalView menuPrincipal =
                    new MenuPrincipalView(usuario);

            menuPrincipal.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Correo o contraseña incorrectos.",
                    "Error de autenticación",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}