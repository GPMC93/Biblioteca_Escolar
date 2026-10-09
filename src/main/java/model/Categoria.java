package model;

/*
 * Representa una categoría de los libros de la biblioteca.
 * Permite identificar y clasificar los libros según su categoría.
 */
public class Categoria {

    // Atributos privados para proteger los datos del objeto
    private int id;
    private String nombre;

    // Constructor sin ID: se usa para crear una categoría nueva
    // El ID será generado automáticamente por la base de datos
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Constructor con ID: se usa al recuperar una categoría existente de la BD
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    // Getters: permiten obtener los valores de los atributos privados
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters: permiten modificar los valores de los atributos privados
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
