package servlets;

public class Libro {
	private String isbn, titulo, autor;
	private int year;
	
	public Libro(String isbn, String titulo, String autor, int year) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
