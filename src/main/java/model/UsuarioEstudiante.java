package model;

public class UsuarioEstudiante extends Usuario {

    public UsuarioEstudiante(String nombre, String rut, String correo,
                             String contrasena, Rol rol) {
        super(nombre, rut, correo, contrasena, rol);
    }

    public UsuarioEstudiante(int id, String nombre, String rut, String correo,
                             String contrasena, Rol rol) {
        super(id, nombre, rut, correo, contrasena, rol);
    }

    @Override
    public boolean puedeGestionarLibros() {
        return false;
    }

    @Override
    public boolean puedeGestionarEstudiantes() {
        return false;
    }
}