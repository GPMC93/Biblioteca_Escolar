package view;

import controller.CategoriaController;
import controller.EstudianteController;
import controller.LibroController;
import controller.PrestamoController;

import dao.CategoriaDAO;
import dao.EstudianteDAO;
import dao.LibroDAO;
import dao.PrestamoDAO;

import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {

    private JButton btnLibros;
    private JButton btnEstudiantes;
    private JButton btnPrestamos;
    private JButton btnReportes;
    private JButton btnCerrarSesion;

    private Usuario usuario;

    public MenuPrincipalView(Usuario usuario) {

        this.usuario = usuario;

        setTitle("Biblioteca Escolar - Menú Principal");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 20));
        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        40,
                        25,
                        40
                )
        );

        JLabel lblBienvenida =
                new JLabel(
                        "Bienvenido/a, "
                                + usuario.getNombre(),
                        SwingConstants.CENTER
                );

        JLabel lblRol =
                new JLabel(
                        "Rol: "
                                + usuario.getRol(),
                        SwingConstants.CENTER
                );

        JPanel panelUsuario =
                new JPanel(
                        new GridLayout(2, 1)
                );

        panelUsuario.add(lblBienvenida);
        panelUsuario.add(lblRol);

        btnLibros =
                new JButton(
                        "Gestión de Libros"
                );

        btnEstudiantes =
                new JButton(
                        "Gestión de Estudiantes"
                );

        btnPrestamos =
                new JButton(
                        "Préstamos / Devoluciones"
                );

        btnReportes =
                new JButton(
                        "Reportes"
                );

        btnCerrarSesion =
                new JButton(
                        "Cerrar sesión"
                );

        JPanel panelBotones =
                new JPanel();

        panelBotones.setLayout(
                new GridLayout(
                        5,
                        1,
                        10,
                        10
                )
        );

        panelBotones.add(btnLibros);
        panelBotones.add(btnEstudiantes);
        panelBotones.add(btnPrestamos);
        panelBotones.add(btnReportes);
        panelBotones.add(btnCerrarSesion);

        /*
         * Los permisos dependen del tipo concreto
         * de Usuario.
         *
         * UsuarioBibliotecario devuelve true.
         * UsuarioEstudiante devuelve false.
         *
         * Esto aplica polimorfismo porque trabajamos
         * con una referencia Usuario y el comportamiento
         * depende del objeto concreto.
         */
        btnLibros.setEnabled(
                usuario.puedeGestionarLibros()
        );

        btnEstudiantes.setEnabled(
                usuario.puedeGestionarEstudiantes()
        );

        panel.add(
                panelUsuario,
                BorderLayout.NORTH
        );

        panel.add(
                panelBotones,
                BorderLayout.CENTER
        );

        add(panel);

        btnLibros.addActionListener(
                e -> abrirLibros()
        );

        btnEstudiantes.addActionListener(
                e -> abrirEstudiantes()
        );

        btnPrestamos.addActionListener(
                e -> abrirPrestamos()
        );

        btnReportes.addActionListener(
                e -> abrirReportes()
        );

        btnCerrarSesion.addActionListener(
                e -> dispose()
        );
    }


    private void abrirLibros() {

        LibroDAO libroDAO =
                new LibroDAO();

        CategoriaDAO categoriaDAO =
                new CategoriaDAO();

        LibroController libroController =
                new LibroController(
                        libroDAO
                );

        CategoriaController categoriaController =
                new CategoriaController(
                        categoriaDAO
                );

        LibroView libroView =
                new LibroView(
                        libroController,
                        categoriaController
                );

        libroView.setVisible(true);
    }


    private void abrirEstudiantes() {

        EstudianteDAO estudianteDAO =
                new EstudianteDAO();

        EstudianteController estudianteController =
                new EstudianteController(
                        estudianteDAO
                );

        EstudianteView estudianteView =
                new EstudianteView(
                        estudianteController
                );

        estudianteView.setVisible(true);
    }


    private void abrirPrestamos() {

        PrestamoDAO prestamoDAO =
                new PrestamoDAO();

        EstudianteDAO estudianteDAO =
                new EstudianteDAO();

        LibroDAO libroDAO =
                new LibroDAO();

        PrestamoController prestamoController =
                new PrestamoController(
                        prestamoDAO
                );

        EstudianteController estudianteController =
                new EstudianteController(
                        estudianteDAO
                );

        LibroController libroController =
                new LibroController(
                        libroDAO
                );

        PrestamoView prestamoView =
                new PrestamoView(
                        prestamoController,
                        estudianteController,
                        libroController
                );

        prestamoView.setVisible(true);
    }


    private void abrirReportes() {

        LibroDAO libroDAO =
                new LibroDAO();

        PrestamoDAO prestamoDAO =
                new PrestamoDAO();

        LibroController libroController =
                new LibroController(
                        libroDAO
                );

        PrestamoController prestamoController =
                new PrestamoController(
                        prestamoDAO
                );

        ReporteView reporteView =
                new ReporteView(
                        libroController,
                        prestamoController
                );

        reporteView.setVisible(true);
    }
}