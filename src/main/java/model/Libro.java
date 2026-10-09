package model;

/*
 * Representa un libro de la biblioteca
 * Guarda sus datos principales y la categoría a la que pertenece
 */
public class Libro {

    // Atributos privados para proteger los datos del objeto (encapsulamiento)
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private String editorial;
    private int stock;
    private Categoria categoria;

    /*
     * Constructor: permite crear e inicializar un objeto Libro
     * con los datos recibidos como parámetros
     */
    public Libro(String titulo, String autor, String isbn,
                 String editorial, int stock, Categoria categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editorial = editorial;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Getters: permiten obtener los valores de los atributos privados
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getStock() {
        return stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }


    // Setters: permiten modificar los valores de los atributos privados
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}