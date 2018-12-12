package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clases.Libro;

@WebServlet("/Biblioteca")
public class Biblioteca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Biblioteca() {
        super();
    }
    
    ArrayList<Libro> libros = new ArrayList<Libro>();
    
    public void leeFicheroLibros() throws IOException {
    	if(libros.size() == 0) {
    		String directorioDestino ="ficheros/libros.txt"; // se crea directamente en el proyecto
	    	String lineaFichero;
	    	
	    	FileReader fr = new FileReader(this.getServletContext().getRealPath("/") +  directorioDestino);
	    	BufferedReader br = new BufferedReader(fr);
	    	
	    	while((lineaFichero = br.readLine()) != null) {
	    		String[] datosLibro = lineaFichero.split(";");
	    		Libro libro = new Libro(datosLibro[0], datosLibro[1], datosLibro[2], datosLibro[3]);
	    		libros.add(libro);
	        }
	    	br.close();
    	}
    }
    
    protected void procesaSolicitud(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	leeFicheroLibros();
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
    	Boolean esAjax;
	    esAjax="XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")); // Cabecera X-Requested-With
	    if (esAjax) {
	        //Recoge el parámetro isbn
	        String isbn = request.getParameter("isbn");
	        String verTodos = request.getParameter("verTodos");
	        String eliminarLibro = request.getParameter("eliminar");
	        boolean eliminado = false;
	        
        	if(verTodos == null) {
        		if (eliminarLibro != null){
        			for(int i=0; i<libros.size(); i++) {
                		if(eliminarLibro.equals(libros.get(i).getIsbn())) {
                			libros.remove(i);
                			eliminado = true;
                		}
                	}
        			if(eliminado == true) {
        				out.println("Libro eliminado.");
        			} else {
        				out.println("ISBN no encontrado.");
        			}
        		}
        		boolean isbnValido = false;
    	        Libro libroValido = new Libro();
    	        
            	for(int i=0; i<libros.size(); i++) {
            		if(isbn.equals(libros.get(i).getIsbn())) {
            			isbnValido = true;
            			libroValido = libros.get(i);
            			break;
            		}
            	}
	        	if(isbnValido == false) {
	        		out.println("<font color='red'>ISBN no encontrado.</font>");
	        	} else {
		        		out.println("<hr>");
		        		out.println("ISBN: " + libroValido.getIsbn() + "<br>");
		        		out.println("Título: " + libroValido.getTitulo() + "<br>");
		        		out.println("Autor: " + libroValido.getAutor() + "<br>");
		        		out.println("Año: " + libroValido.getYear());
	        		}
        	} else {
        		for(int i = 0; i < libros.size(); i++) {
        			out.println("<hr>");
	        		out.println("ISBN: " + libros.get(i).getIsbn() + "<br>");
	        		out.println("Título: " + libros.get(i).getTitulo() + "<br>");
	        		out.println("Autor: " + libros.get(i).getAutor() + "<br>");
	        		out.println("Año: " + libros.get(i).getYear() + "<br>");
        		}
        	}
	    } else {
	        out.println("Este servlet solo se puede invocar vía Ajax");
	    }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		procesaSolicitud(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		procesaSolicitud(request, response);
	}
}