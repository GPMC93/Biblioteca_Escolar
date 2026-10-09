
package controller;

import dao.PrestamoDAO;
import model.Prestamo;

import java.util.List;

public class PrestamoController {

    private PrestamoDAO prestamoDAO;

    public PrestamoController(PrestamoDAO prestamoDAO) {
        this.prestamoDAO = prestamoDAO;
    }

    // =========================================================
    // CRUD BÁSICO
    // =========================================================

    // Guarda un préstamo mediante el DAO
    public void guardarPrestamo(Prestamo prestamo) {
        prestamoDAO.guardar(prestamo);
    }

    // Obtiene todos los préstamos registrados
    public List<Prestamo> listarPrestamos() {
        return prestamoDAO.listar();
    }

    // Busca un préstamo utilizando su ID
    public Prestamo buscarPrestamoPorId(int id) {
        return prestamoDAO.buscarPorId(id);
    }

    // Actualiza los datos de un préstamo
    public void actualizarPrestamo(Prestamo prestamo) {
        prestamoDAO.actualizar(prestamo);
    }

    // Elimina un préstamo utilizando su ID
    public void eliminarPrestamo(int id) {
        prestamoDAO.eliminar(id);
    }

    // =========================================================
    // REGISTRAR PRÉSTAMO + DESCONTAR STOCK
    // synchronized controla el acceso simultáneo a este metodo
    // =========================================================
    public synchronized boolean registrarPrestamo(Prestamo prestamo) {

        // El DAO registra el préstamo y descuenta stock
        // Ambas operaciones utilizan una misma transacción SQL
        return prestamoDAO.registrarPrestamoConStock(prestamo);
    }

    // =========================================================
    // REGISTRAR DEVOLUCIÓN + DEVOLVER STOCK
    // synchronized controla el acceso simultáneo a este método
    // =========================================================
    public synchronized boolean registrarDevolucion(int idPrestamo) {

        // El DAO registra la devolución y recupera el stock
        // Ambas operaciones utilizan una misma transacción SQL
        return prestamoDAO.registrarDevolucionConStock(idPrestamo);
    }
}
