package view;

import controller.EstudianteController;
import controller.LibroController;
import controller.PrestamoController;
import model.Estudiante;
import model.Libro;
import model.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PrestamoView extends JFrame {

    private JComboBox<String> cmbEstudiante;
    private JComboBox<String> cmbLibro;

    private JButton btnRegistrar;
    private JButton btnDevolver;
    private JButton btnActualizar;

    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;

    private PrestamoController prestamoController;
    private EstudianteController estudianteController;
    private LibroController libroController;

    private List<Estudiante> estudiantes;
    private List<Libro> libros;

    private int idPrestamoSeleccionado = -1;

    public PrestamoView(
            PrestamoController prestamoController,
            EstudianteController estudianteController,
            LibroController libroController) {

        this.prestamoController = prestamoController;
        this.estudianteController = estudianteController;
        this.libroController = libroController;

        // =========================================================
        // CONFIGURACIÓN
        // =========================================================
        setTitle("Biblioteca Escolar - Préstamos y Devoluciones");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================================================
        // PANEL PRINCIPAL
        // =========================================================
        JPanel panelPrincipal =
                new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // =========================================================
        // FORMULARIO
        // =========================================================
        JPanel panelFormulario =
                new JPanel(new GridLayout(2, 2, 10, 10));

        cmbEstudiante = new JComboBox<>();
        cmbLibro = new JComboBox<>();

        panelFormulario.add(new JLabel("Estudiante:"));
        panelFormulario.add(cmbEstudiante);

        panelFormulario.add(new JLabel("Libro:"));
        panelFormulario.add(cmbLibro);

        // =========================================================
        // BOTONES
        // =========================================================
        JPanel panelBotones = new JPanel();

        btnRegistrar =
                new JButton("Registrar préstamo");

        btnDevolver =
                new JButton("Registrar devolución");

        btnActualizar =
                new JButton("Actualizar tabla");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnDevolver);
        panelBotones.add(btnActualizar);

        JPanel panelSuperior =
                new JPanel(new BorderLayout(10, 10));

        panelSuperior.add(
                panelFormulario,
                BorderLayout.CENTER
        );

        panelSuperior.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        // =========================================================
        // TABLA
        // =========================================================
        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Estudiante",
                        "Libro",
                        "Fecha préstamo",
                        "Fecha devolución",
                        "Devuelto"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        tablaPrestamos =
                new JTable(modeloTabla);

        JScrollPane scrollTabla =
                new JScrollPane(tablaPrestamos);

        panelPrincipal.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                scrollTabla,
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        // =========================================================
        // EVENTOS
        // =========================================================
        btnRegistrar.addActionListener(
                e -> registrarPrestamo()
        );

        btnDevolver.addActionListener(
                e -> registrarDevolucion()
        );

        btnActualizar.addActionListener(
                e -> actualizarDatos()
        );

        tablaPrestamos
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int fila =
                                tablaPrestamos.getSelectedRow();

                        if (fila != -1) {

                            idPrestamoSeleccionado =
                                    (int) modeloTabla.getValueAt(
                                            fila,
                                            0
                                    );
                        }
                    }
                });

        // =========================================================
        // DATOS INICIALES
        // =========================================================
        cargarEstudiantes();
        cargarLibros();
        cargarPrestamos();
    }

    // =========================================================
    // CARGAR ESTUDIANTES
    // =========================================================
    private void cargarEstudiantes() {

        estudiantes =
                estudianteController.listarEstudiantes();

        cmbEstudiante.removeAllItems();

        for (Estudiante estudiante : estudiantes) {

            cmbEstudiante.addItem(
                    estudiante.getNombre()
                            + " - "
                            + estudiante.getRut()
            );
        }
    }

    // =========================================================
    // CARGAR LIBROS
    // =========================================================
    private void cargarLibros() {

        libros =
                libroController.listarLibros();

        cmbLibro.removeAllItems();

        for (Libro libro : libros) {

            cmbLibro.addItem(
                    libro.getTitulo()
                            + " - Stock: "
                            + libro.getStock()
            );
        }
    }

    // =========================================================
    // CARGAR PRÉSTAMOS
    // =========================================================
    private void cargarPrestamos() {

        modeloTabla.setRowCount(0);

        List<Prestamo> prestamos =
                prestamoController.listarPrestamos();

        for (Prestamo prestamo : prestamos) {

            modeloTabla.addRow(
                    new Object[]{
                            prestamo.getId(),
                            prestamo.getEstudiante().getNombre(),
                            prestamo.getLibro().getTitulo(),
                            prestamo.getFecha(),
                            prestamo.getFechaDevolucion(),
                            prestamo.isDevuelto()
                                    ? "Sí"
                                    : "No"
                    }
            );
        }
    }

    // =========================================================
    // REGISTRAR PRÉSTAMO EN SEGUNDO PLANO
    // =========================================================
    private void registrarPrestamo() {

        int indiceEstudiante =
                cmbEstudiante.getSelectedIndex();

        int indiceLibro =
                cmbLibro.getSelectedIndex();

        if (indiceEstudiante == -1
                || indiceLibro == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar estudiante y libro."
            );

            return;
        }

        Estudiante estudiante =
                estudiantes.get(indiceEstudiante);

        Libro libro =
                libros.get(indiceLibro);

        Prestamo prestamo =
                new Prestamo(
                        LocalDate.now(),
                        null,
                        estudiante,
                        libro
                );

        // Evitar doble clic mientras trabaja
        btnRegistrar.setEnabled(false);

        SwingWorker<Boolean, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Boolean doInBackground() {

                        // Se ejecuta en un hilo independiente
                        return prestamoController
                                .registrarPrestamo(prestamo);
                    }

                    @Override
                    protected void done() {

                        try {

                            boolean registrado = get();

                            if (registrado) {

                                JOptionPane.showMessageDialog(
                                        PrestamoView.this,
                                        "Préstamo registrado correctamente."
                                );

                                actualizarDatos();

                            } else {

                                JOptionPane.showMessageDialog(
                                        PrestamoView.this,
                                        "No hay stock disponible para este libro.",
                                        "Préstamo no realizado",
                                        JOptionPane.WARNING_MESSAGE
                                );
                            }

                        } catch (Exception e) {

                            JOptionPane.showMessageDialog(
                                    PrestamoView.this,
                                    "Error al registrar el préstamo: "
                                            + e.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                        } finally {

                            btnRegistrar.setEnabled(true);
                        }
                    }
                };

        worker.execute();
    }

    // =========================================================
    // REGISTRAR DEVOLUCIÓN EN SEGUNDO PLANO
    // =========================================================
    private void registrarDevolucion() {

        if (idPrestamoSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un préstamo."
            );

            return;
        }

        int idPrestamo =
                idPrestamoSeleccionado;

        btnDevolver.setEnabled(false);

        SwingWorker<Boolean, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Boolean doInBackground() {

                        // Se ejecuta en un hilo independiente
                        return prestamoController
                                .registrarDevolucion(idPrestamo);
                    }

                    @Override
                    protected void done() {

                        try {

                            boolean devuelto = get();

                            if (devuelto) {

                                JOptionPane.showMessageDialog(
                                        PrestamoView.this,
                                        "Devolución registrada correctamente."
                                );

                                actualizarDatos();

                            } else {

                                JOptionPane.showMessageDialog(
                                        PrestamoView.this,
                                        "El préstamo no existe o ya fue devuelto.",
                                        "Devolución no realizada",
                                        JOptionPane.WARNING_MESSAGE
                                );
                            }

                        } catch (Exception e) {

                            JOptionPane.showMessageDialog(
                                    PrestamoView.this,
                                    "Error al registrar la devolución: "
                                            + e.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                        } finally {

                            btnDevolver.setEnabled(true);
                        }
                    }
                };

        worker.execute();
    }

    // =========================================================
    // ACTUALIZAR TODA LA INFORMACIÓN
    // =========================================================
    private void actualizarDatos() {

        cargarLibros();
        cargarPrestamos();

        idPrestamoSeleccionado = -1;

        tablaPrestamos.clearSelection();
    }
}