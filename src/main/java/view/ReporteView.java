package view;

import controller.LibroController;
import controller.PrestamoController;
import model.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReporteView extends JFrame {

    private LibroController libroController;
    private PrestamoController prestamoController;

    private JTable tablaReporte;
    private DefaultTableModel modeloTabla;

    private JButton btnMasPrestados;
    private JButton btnHistorialEstudiante;
    private JButton btnPrestamosActivos;

    public ReporteView(
            LibroController libroController,
            PrestamoController prestamoController) {

        this.libroController = libroController;
        this.prestamoController = prestamoController;

        // =========================================================
        // CONFIGURACIÓN
        // =========================================================
        setTitle("Biblioteca Escolar - Reportes");
        setSize(900, 550);
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
        // BOTONES
        // =========================================================
        JPanel panelBotones = new JPanel();

        btnMasPrestados =
                new JButton("Libros más prestados");

        btnHistorialEstudiante =
                new JButton("Historial por estudiante");

        btnPrestamosActivos =
                new JButton("Libros actualmente en préstamo");

        panelBotones.add(btnMasPrestados);
        panelBotones.add(btnHistorialEstudiante);
        panelBotones.add(btnPrestamosActivos);

        // =========================================================
        // TABLA
        // =========================================================
        modeloTabla = new DefaultTableModel();

        tablaReporte = new JTable(modeloTabla);

        JScrollPane scrollTabla =
                new JScrollPane(tablaReporte);

        panelPrincipal.add(
                panelBotones,
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
        btnMasPrestados.addActionListener(
                e -> mostrarLibrosMasPrestados()
        );

        btnHistorialEstudiante.addActionListener(
                e -> mostrarHistorialEstudiante()
        );

        btnPrestamosActivos.addActionListener(
                e -> mostrarPrestamosActivos()
        );

        // Mostrar el primer reporte al abrir
        mostrarLibrosMasPrestados();
    }

    // =========================================================
    // REPORTE: LIBROS MÁS PRESTADOS
    // =========================================================
    private void mostrarLibrosMasPrestados() {

        modeloTabla.setRowCount(0);

        modeloTabla.setColumnIdentifiers(
                new Object[]{
                        "Libro",
                        "Cantidad de préstamos"
                }
        );

        List<Prestamo> prestamos =
                prestamoController.listarPrestamos();

        Map<String, Integer> cantidadPorLibro =
                new LinkedHashMap<>();

        for (Prestamo prestamo : prestamos) {

            String titulo =
                    prestamo.getLibro().getTitulo();

            cantidadPorLibro.put(
                    titulo,
                    cantidadPorLibro.getOrDefault(titulo, 0) + 1
            );
        }

        cantidadPorLibro.entrySet()
                .stream()
                .sorted(
                        Map.Entry.<String, Integer>
                                        comparingByValue()
                                .reversed()
                )
                .forEach(entry ->
                        modeloTabla.addRow(
                                new Object[]{
                                        entry.getKey(),
                                        entry.getValue()
                                }
                        )
                );
    }

    // =========================================================
    // REPORTE: HISTORIAL POR ESTUDIANTE
    // =========================================================
    private void mostrarHistorialEstudiante() {

        List<Prestamo> prestamos =
                prestamoController.listarPrestamos();

        if (prestamos.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No existen préstamos registrados."
            );

            return;
        }

        Map<Integer, String> estudiantes =
                new LinkedHashMap<>();

        for (Prestamo prestamo : prestamos) {

            estudiantes.put(
                    prestamo.getEstudiante().getId(),
                    prestamo.getEstudiante().getNombre()
            );
        }

        Object seleccion = JOptionPane.showInputDialog(
                this,
                "Seleccione un estudiante:",
                "Historial por estudiante",
                JOptionPane.QUESTION_MESSAGE,
                null,
                estudiantes.values().toArray(),
                estudiantes.values().iterator().next()
        );

        if (seleccion == null) {
            return;
        }

        String nombreSeleccionado =
                seleccion.toString();

        modeloTabla.setRowCount(0);

        modeloTabla.setColumnIdentifiers(
                new Object[]{
                        "ID",
                        "Estudiante",
                        "Libro",
                        "Fecha préstamo",
                        "Fecha devolución",
                        "Estado"
                }
        );

        for (Prestamo prestamo : prestamos) {

            if (!prestamo.getEstudiante()
                    .getNombre()
                    .equals(nombreSeleccionado)) {

                continue;
            }

            modeloTabla.addRow(
                    new Object[]{
                            prestamo.getId(),
                            prestamo.getEstudiante().getNombre(),
                            prestamo.getLibro().getTitulo(),
                            prestamo.getFecha(),
                            prestamo.getFechaDevolucion(),
                            prestamo.isDevuelto()
                                    ? "Devuelto"
                                    : "Activo"
                    }
            );
        }
    }

    // =========================================================
    // REPORTE: LIBROS ACTUALMENTE EN PRÉSTAMO
    // =========================================================
    private void mostrarPrestamosActivos() {

        modeloTabla.setRowCount(0);

        modeloTabla.setColumnIdentifiers(
                new Object[]{
                        "ID",
                        "Estudiante",
                        "Libro",
                        "Fecha préstamo",
                        "Estado"
                }
        );

        List<Prestamo> prestamos =
                prestamoController.listarPrestamos();

        for (Prestamo prestamo : prestamos) {

            if (prestamo.isDevuelto()) {
                continue;
            }

            modeloTabla.addRow(
                    new Object[]{
                            prestamo.getId(),
                            prestamo.getEstudiante().getNombre(),
                            prestamo.getLibro().getTitulo(),
                            prestamo.getFecha(),
                            "En préstamo"
                    }
            );
        }
    }
}