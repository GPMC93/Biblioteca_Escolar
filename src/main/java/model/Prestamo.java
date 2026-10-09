package model;

import java.time.LocalDate;

/*
 * Representa un préstamo realizado por un estudiante
 * Relaciona al estudiante con el libro prestado y sus fechas
 */
public class Prestamo {

    // Atributos privados para proteger los datos del objeto
    private int id;
    private LocalDate fecha;
    private LocalDate fechaDevolucion;
    private Estudiante estudiante;
    private Libro libro;
    private boolean devuelto;

    // Constructor sin ID: se usa para crear un préstamo nuevo
    // El ID será generado automáticamente por la base de datos
    // Un préstamo nuevo comienza como no devuelto
    public Prestamo(LocalDate fecha, LocalDate fechaDevolucion,
                    Estudiante estudiante, Libro libro) {

        this.fecha = fecha;
        this.fechaDevolucion = fechaDevolucion;
        this.estudiante = estudiante;
        this.libro = libro;
        this.devuelto = false;
    }

    // Constructor con ID: se usa al recuperar un préstamo existente de la BD
    // En este caso devuelto puede ser true o false según lo almacenado
    public Prestamo(int id, LocalDate fecha, LocalDate fechaDevolucion,
                    Estudiante estudiante, Libro libro, boolean devuelto) {

        this.id = id;
        this.fecha = fecha;
        this.fechaDevolucion = fechaDevolucion;
        this.estudiante = estudiante;
        this.libro = libro;
        this.devuelto = devuelto;
    }

    // Getters: permiten obtener los valores de los atributos privados
    public int getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Libro getLibro() {
        return libro;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    // Setters: permiten modificar los valores de los atributos privados
    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }
}