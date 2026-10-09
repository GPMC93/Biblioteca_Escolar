package model;

/*
 * Representa a un estudiante de la biblioteca
 * Guarda sus datos para identificarlo y gestionar sus préstamos
 */
public class Estudiante {

    //Atributos Privados para proteger los datos del objeto
    private int id;
    private String nombre;
    private String rut;
    private String curso;
    private String correo;

    // Constructor sin ID: se usa para crear un estudiante nuevo
    // El ID será generado automáticamente por la base de datos
    public Estudiante(String nombre, String rut, String curso, String correo) {
        this.nombre = nombre;
        this.rut = rut;
        this.curso = curso;
        this.correo = correo;
    }

    // Constructor con ID: se usa al recuperar un estudiante existente de la BD
    public Estudiante(int id, String nombre, String rut, String curso, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.curso = curso;
        this.correo = correo;
    }

    //Getters: permiten obtener los valores de los atributos privados
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRut() {
        return rut;
    }

    public String getCurso() {
        return curso;
    }

    public String getCorreo() {
        return correo;
    }

    //Setters: permiten modificar los valores de los atributos privados
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
