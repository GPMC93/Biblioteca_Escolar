package controller;

import dao.LibroDAO;
import model.Libro;

import java.util.List;

public class LibroController {

    // DAO encargado de realizar las operaciones con la BD
    private LibroDAO libroDAO;

    // Recibe el DAO que utilizará el controlador
    public LibroController(LibroDAO libroDAO) {
        this.libroDAO = libroDAO;
    }

    // CREATE - Guardar un libro y devolver el resultado
    public boolean guardarLibro(Libro libro) {
        return libroDAO.guardar(libro);
    }

    // READ - Obtener todos los libros registrados
    public List<Libro> listarLibros() {
        return libroDAO.listar();
    }

    // READ - Buscar un libro por su ID
    public Libro buscarLibroPorId(int id) {
        return libroDAO.buscarPorId(id);
    }

    // UPDATE - Actualizar un libro y devolver el resultado
    public boolean actualizarLibro(Libro libro) {
        return libroDAO.actualizar(libro);
    }

    // DELETE - Eliminar un libro y devolver el resultado
    public boolean eliminarLibro(int id) {
        return libroDAO.eliminar(id);
    }
}
