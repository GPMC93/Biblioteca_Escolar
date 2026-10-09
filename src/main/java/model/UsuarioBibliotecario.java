package model;

public class UsuarioBibliotecario extends Usuario {

    public UsuarioBibliotecario(String nombre, String rut, String correo,
                                String contrasena, Rol rol) {
        super(nombre, rut, correo, contrasena, rol);
    }

    public UsuarioBibliotecario(int id, String nombre, String rut, String correo,
                                String contrasena, Rol rol) {
        super(id, nombre, rut, correo, contrasena, rol);
    }

    @Override
    public boolean puedeGestionarLibros() {
        return true;
    }

    @Override
    public boolean puedeGestionarEstudiantes() {
        return true;
    }
}