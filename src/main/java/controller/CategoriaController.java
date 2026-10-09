package controller;

import dao.CategoriaDAO;
import model.Categoria;

import java.util.List;

public class CategoriaController {

    // El Controller utiliza el DAO para acceder a los datos
    private CategoriaDAO categoriaDAO;

    // Recibe el DAO que utilizará
    public CategoriaController(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    // CREATE - Guardar una categoría
    public void guardarCategoria(Categoria categoria) {
        categoriaDAO.guardar(categoria);
    }

    // READ - Listar todas las categorías
    public List<Categoria> listarCategorias() {
        return categoriaDAO.listar();
    }

    // READ - Buscar una categoría por ID
    public Categoria buscarCategoriaPorId(int id) {
        return categoriaDAO.buscarPorId(id);
    }

    // UPDATE - Actualizar una categoría
    public void actualizarCategoria(Categoria categoria) {
        categoriaDAO.actualizar(categoria);
    }

    // DELETE - Eliminar una categoría
    public void eliminarCategoria(int id) {
        categoriaDAO.eliminar(id);
    }
}