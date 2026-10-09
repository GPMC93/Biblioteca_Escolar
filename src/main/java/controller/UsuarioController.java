package controller;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.List;

public class UsuarioController {

    // El Controller utiliza el DAO para acceder a los datos
    private UsuarioDAO usuarioDAO;

    // Recibe el DAO que utilizará
    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // CREATE - Guardar un usuario
    public void guardarUsuario(Usuario usuario) {
        usuarioDAO.guardar(usuario);
    }

    // READ - Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listar();
    }

    // READ - Buscar un usuario por ID
    public Usuario buscarUsuarioPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    // UPDATE - Actualizar un usuario
    public void actualizarUsuario(Usuario usuario) {
        usuarioDAO.actualizar(usuario);
    }

    // DELETE - Eliminar un usuario
    public void eliminarUsuario(int id) {
        usuarioDAO.eliminar(id);
    }

    // AUTENTICAR - Comprueba correo y contraseña
    public Usuario autenticarUsuario(String correo, String contrasena) {
        return usuarioDAO.autenticar(correo, contrasena);
    }
}