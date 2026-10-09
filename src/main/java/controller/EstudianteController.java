
package controller;

import dao.EstudianteDAO;
import model.Estudiante;

import java.util.List;

public class EstudianteController {

    // El Controller utiliza el DAO para acceder a los datos
    private EstudianteDAO estudianteDAO;

    // Recibe el DAO que utilizará
    public EstudianteController(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    // CREATE - Guardar un estudiante
    public boolean guardarEstudiante(Estudiante estudiante) {
        return estudianteDAO.guardar(estudiante);
    }

    // READ - Listar todos los estudiantes
    public List<Estudiante> listarEstudiantes() {
        return estudianteDAO.listar();
    }

    // READ - Buscar un estudiante por ID
    public Estudiante buscarEstudiantePorId(int id) {
        return estudianteDAO.buscarPorId(id);
    }

    // UPDATE - Actualizar un estudiante
    public boolean actualizarEstudiante(Estudiante estudiante) {
        return estudianteDAO.actualizar(estudiante);
    }

    // DELETE - Eliminar un estudiante
    public boolean eliminarEstudiante(int id) {
        return estudianteDAO.eliminar(id);
    }
}
