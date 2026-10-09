
package view;

import controller.EstudianteController;
import model.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstudianteView extends JFrame {

    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCurso;
    private JTextField txtCorreo;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;

    private EstudianteController estudianteController;

    private int idEstudianteSeleccionado = -1;

    public EstudianteView(EstudianteController estudianteController) {

        this.estudianteController = estudianteController;

        // =========================================================
        // CONFIGURACIÓN DE LA VENTANA
        // =========================================================
        setTitle("Biblioteca Escolar - Gestión de Estudiantes");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================================================
        // PANEL PRINCIPAL
        // =========================================================
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // =========================================================
        // FORMULARIO
        // =========================================================
        JPanel panelFormulario =
                new JPanel(new GridLayout(4, 2, 10, 10));

        txtNombre = new JTextField();
        txtRut = new JTextField();
        txtCurso = new JTextField();
        txtCorreo = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("RUT:"));
        panelFormulario.add(txtRut);

        panelFormulario.add(new JLabel("Curso:"));
        panelFormulario.add(txtCurso);

        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);

        // =========================================================
        // BOTONES
        // =========================================================
        JPanel panelBotones = new JPanel();

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));

        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        // =========================================================
        // TABLA
        // =========================================================
        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Nombre",
                        "RUT",
                        "Curso",
                        "Correo"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEstudiantes = new JTable(modeloTabla);

        JScrollPane scrollTabla =
                new JScrollPane(tablaEstudiantes);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        add(panelPrincipal);

        // =========================================================
        // EVENTOS
        // =========================================================
        btnGuardar.addActionListener(e -> guardarEstudiante());

        btnActualizar.addActionListener(e -> actualizarEstudiante());

        btnEliminar.addActionListener(e -> eliminarEstudiante());

        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tablaEstudiantes.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        seleccionarEstudiante();
                    }
                });

        cargarEstudiantes();
    }

    // =========================================================
    // CARGAR ESTUDIANTES
    // =========================================================
    private void cargarEstudiantes() {

        modeloTabla.setRowCount(0);

        List<Estudiante> estudiantes =
                estudianteController.listarEstudiantes();

        for (Estudiante estudiante : estudiantes) {

            modeloTabla.addRow(
                    new Object[]{
                            estudiante.getId(),
                            estudiante.getNombre(),
                            estudiante.getRut(),
                            estudiante.getCurso(),
                            estudiante.getCorreo()
                    }
            );
        }
    }

    // =========================================================
    // GUARDAR
    // =========================================================
    private void guardarEstudiante() {

        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String curso = txtCurso.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty()
                || rut.isEmpty()
                || curso.isEmpty()
                || correo.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos."
            );

            return;
        }

        Estudiante estudiante =
                new Estudiante(
                        nombre,
                        rut,
                        curso,
                        correo
                );

        // Mostrar confirmación solamente si MySQL registra al estudiante
        boolean guardado =
                estudianteController.guardarEstudiante(estudiante);

        if (guardado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante guardado correctamente."
            );

            cargarEstudiantes();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo guardar el estudiante.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // SELECCIONAR
    // =========================================================
    private void seleccionarEstudiante() {

        int fila = tablaEstudiantes.getSelectedRow();

        if (fila == -1) {
            return;
        }

        idEstudianteSeleccionado =
                (int) modeloTabla.getValueAt(fila, 0);

        Estudiante estudiante =
                estudianteController.buscarEstudiantePorId(
                        idEstudianteSeleccionado
                );

        if (estudiante == null) {
            return;
        }

        txtNombre.setText(estudiante.getNombre());
        txtRut.setText(estudiante.getRut());
        txtCurso.setText(estudiante.getCurso());
        txtCorreo.setText(estudiante.getCorreo());
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================
    private void actualizarEstudiante() {

        if (idEstudianteSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un estudiante."
            );

            return;
        }

        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String curso = txtCurso.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty()
                || rut.isEmpty()
                || curso.isEmpty()
                || correo.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos."
            );

            return;
        }

        Estudiante estudiante =
                new Estudiante(
                        idEstudianteSeleccionado,
                        nombre,
                        rut,
                        curso,
                        correo
                );

        // Comprobar el resultado antes de confirmar la actualización
        boolean actualizado =
                estudianteController.actualizarEstudiante(estudiante);

        if (actualizado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante actualizado correctamente."
            );

            cargarEstudiantes();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el estudiante.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ELIMINAR
    // =========================================================
    private void eliminarEstudiante() {

        if (idEstudianteSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un estudiante."
            );

            return;
        }

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de eliminar el estudiante?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (respuesta == JOptionPane.YES_OPTION) {

            // Comprobar si MySQL eliminó realmente el registro
            boolean eliminado =
                    estudianteController.eliminarEstudiante(
                            idEstudianteSeleccionado
                    );

            if (eliminado) {

                cargarEstudiantes();
                limpiarFormulario();

                JOptionPane.showMessageDialog(
                        this,
                        "Estudiante eliminado correctamente."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar el estudiante.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // =========================================================
    // LIMPIAR
    // =========================================================
    private void limpiarFormulario() {

        txtNombre.setText("");
        txtRut.setText("");
        txtCurso.setText("");
        txtCorreo.setText("");

        tablaEstudiantes.clearSelection();

        idEstudianteSeleccionado = -1;
    }
}
