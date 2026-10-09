package view;

import controller.CategoriaController;
import controller.LibroController;
import model.Categoria;
import model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibroView extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtIsbn;
    private JTextField txtEditorial;
    private JTextField txtStock;

    private JComboBox<String> cmbCategoria;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    private LibroController libroController;
    private CategoriaController categoriaController;

    private List<Categoria> categorias;

    // Guarda el ID del libro seleccionado
    private int idLibroSeleccionado = -1;

    public LibroView(
            LibroController libroController,
            CategoriaController categoriaController) {

        this.libroController = libroController;
        this.categoriaController = categoriaController;

        // =========================================================
        // CONFIGURACIÓN DE LA VENTANA
        // =========================================================
        setTitle("Biblioteca Escolar - Gestión de Libros");
        setSize(900, 600);
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
        JPanel panelFormulario = new JPanel(
                new GridLayout(6, 2, 10, 10)
        );

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtIsbn = new JTextField();
        txtEditorial = new JTextField();
        txtStock = new JTextField();

        cmbCategoria = new JComboBox<>();

        panelFormulario.add(new JLabel("Título:"));
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("ISBN:"));
        panelFormulario.add(txtIsbn);

        panelFormulario.add(new JLabel("Editorial:"));
        panelFormulario.add(txtEditorial);

        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);

        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(cmbCategoria);

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

        panelSuperior.add(
                panelFormulario,
                BorderLayout.CENTER
        );

        panelSuperior.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        // =========================================================
        // TABLA DE LIBROS
        // =========================================================
        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Título",
                        "Autor",
                        "ISBN",
                        "Editorial",
                        "Stock",
                        "Categoría"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaLibros = new JTable(modeloTabla);

        JScrollPane scrollTabla =
                new JScrollPane(tablaLibros);

        // =========================================================
        // AGREGAR TODO A LA VENTANA
        // =========================================================
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
        btnGuardar.addActionListener(
                e -> guardarLibro()
        );

        btnActualizar.addActionListener(
                e -> actualizarLibro()
        );

        btnEliminar.addActionListener(
                e -> eliminarLibro()
        );

        btnLimpiar.addActionListener(
                e -> limpiarFormulario()
        );

        tablaLibros.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        seleccionarLibro();
                    }
                });

        // =========================================================
        // CARGAR DATOS INICIALES
        // =========================================================
        cargarCategorias();
        cargarLibros();
    }

    // =========================================================
    // CARGAR CATEGORÍAS EN EL COMBOBOX
    // =========================================================
    private void cargarCategorias() {

        categorias =
                categoriaController.listarCategorias();

        cmbCategoria.removeAllItems();

        for (Categoria categoria : categorias) {
            cmbCategoria.addItem(
                    categoria.getNombre()
            );
        }
    }

    // =========================================================
    // CARGAR LIBROS EN LA TABLA
    // =========================================================
    private void cargarLibros() {

        modeloTabla.setRowCount(0);

        List<Libro> libros =
                libroController.listarLibros();

        for (Libro libro : libros) {

            modeloTabla.addRow(
                    new Object[]{
                            libro.getId(),
                            libro.getTitulo(),
                            libro.getAutor(),
                            libro.getIsbn(),
                            libro.getEditorial(),
                            libro.getStock(),
                            libro.getCategoria().getNombre()
                    }
            );
        }
    }

    // =========================================================
    // GUARDAR LIBRO
    // =========================================================
    private void guardarLibro() {

        try {

            String titulo =
                    txtTitulo.getText().trim();

            String autor =
                    txtAutor.getText().trim();

            String isbn =
                    txtIsbn.getText().trim();

            String editorial =
                    txtEditorial.getText().trim();

            int stock =
                    Integer.parseInt(
                            txtStock.getText().trim()
                    );

            int indiceCategoria =
                    cmbCategoria.getSelectedIndex();

            if (titulo.isEmpty()
                    || autor.isEmpty()
                    || isbn.isEmpty()
                    || editorial.isEmpty()
                    || indiceCategoria == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe completar todos los campos."
                );

                return;
            }

            Categoria categoria =
                    categorias.get(indiceCategoria);

            Libro libro = new Libro(
                    titulo,
                    autor,
                    isbn,
                    editorial,
                    stock,
                    categoria
            );

            // Confirmar el registro solo si MySQL insertó el libro
            boolean guardado =
                    libroController.guardarLibro(libro);

            if (guardado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro guardado correctamente."
                );

                cargarLibros();
                limpiarFormulario();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo guardar el libro.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe ser un número entero.",
                    "Dato inválido",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // SELECCIONAR LIBRO DESDE LA TABLA
    // =========================================================
    private void seleccionarLibro() {

        int fila =
                tablaLibros.getSelectedRow();

        if (fila == -1) {
            return;
        }

        idLibroSeleccionado =
                (int) modeloTabla.getValueAt(fila, 0);

        Libro libro =
                libroController.buscarLibroPorId(
                        idLibroSeleccionado
                );

        if (libro == null) {
            return;
        }

        txtTitulo.setText(libro.getTitulo());
        txtAutor.setText(libro.getAutor());
        txtIsbn.setText(libro.getIsbn());
        txtEditorial.setText(libro.getEditorial());

        txtStock.setText(
                String.valueOf(libro.getStock())
        );

        for (int i = 0; i < categorias.size(); i++) {

            if (categorias.get(i).getId()
                    == libro.getCategoria().getId()) {

                cmbCategoria.setSelectedIndex(i);
                break;
            }
        }
    }

    // =========================================================
    // ACTUALIZAR LIBRO
    // =========================================================
    private void actualizarLibro() {

        if (idLibroSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un libro."
            );

            return;
        }

        try {

            String titulo =
                    txtTitulo.getText().trim();

            String autor =
                    txtAutor.getText().trim();

            String isbn =
                    txtIsbn.getText().trim();

            String editorial =
                    txtEditorial.getText().trim();

            int stock =
                    Integer.parseInt(
                            txtStock.getText().trim()
                    );

            int indiceCategoria =
                    cmbCategoria.getSelectedIndex();

            if (titulo.isEmpty()
                    || autor.isEmpty()
                    || isbn.isEmpty()
                    || editorial.isEmpty()
                    || indiceCategoria == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe completar todos los campos."
                );

                return;
            }

            Categoria categoria =
                    categorias.get(indiceCategoria);

            Libro libro = new Libro(
                    titulo,
                    autor,
                    isbn,
                    editorial,
                    stock,
                    categoria
            );

            libro.setId(idLibroSeleccionado);

            // Confirmar la actualización solo si MySQL modificó el registro
            boolean actualizado =
                    libroController.actualizarLibro(libro);

            if (actualizado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro actualizado correctamente."
                );

                cargarLibros();
                limpiarFormulario();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo actualizar el libro.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe ser un número entero.",
                    "Dato inválido",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ELIMINAR LIBRO
    // =========================================================
    private void eliminarLibro() {

        if (idLibroSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un libro."
            );

            return;
        }

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de eliminar el libro?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (respuesta == JOptionPane.YES_OPTION) {

            // Confirmar la eliminación solo si MySQL eliminó el registro
            boolean eliminado =
                    libroController.eliminarLibro(
                            idLibroSeleccionado
                    );

            if (eliminado) {

                cargarLibros();
                limpiarFormulario();

                JOptionPane.showMessageDialog(
                        this,
                        "Libro eliminado correctamente."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar el libro.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // =========================================================
    // LIMPIAR FORMULARIO
    // =========================================================
    private void limpiarFormulario() {

        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEditorial.setText("");
        txtStock.setText("");

        if (cmbCategoria.getItemCount() > 0) {
            cmbCategoria.setSelectedIndex(0);
        }

        tablaLibros.clearSelection();

        idLibroSeleccionado = -1;
    }
}
