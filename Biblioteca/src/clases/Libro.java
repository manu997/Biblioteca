package clases;

public class Libro {
	private String isbn, titulo, autor, year;
	
	public Libro(String isbn, String titulo, String autor, String year) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.year = year;
	}
	
	public Libro() {}

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}