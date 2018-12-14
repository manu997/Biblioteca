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
    
    // La modificacion, eliminacion o adicion de un libro no es permanente, solo perdura durante la sesion actual.
    // Si se refresca la pagina volveran a estar los cuatro libros originales.
    
    public void leeFicheroLibros() throws IOException {
    	if(libros.size() == 0) {
    		String directorioDestino ="ficheros/libros.txt";
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
	    esAjax="XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	    if (esAjax) {
	        String isbn = request.getParameter("isbn");
	        String verTodos = request.getParameter("verTodos");
	        String eliminarLibro = request.getParameter("eliminar");
	        String menuAgregar = request.getParameter("menuAgregar");
	        String agregarISBN = request.getParameter("agregarISBN");
	        String agregarTitulo = request.getParameter("agregarTitulo");
	        String agregarAutor = request.getParameter("agregarAutor");
	        String agregarYear = request.getParameter("agregarYear");
	        String menuModificar1 = request.getParameter("menuModificar");
	        String modificarISBN = request.getParameter("modificarISBN");
	        String modificarTitulo = request.getParameter("modificarTitulo");
	        String modificarAutor = request.getParameter("modificarAutor");
	        String modificarYear = request.getParameter("modificarYear");
	        String modificarLibro1 = request.getParameter("modificarLibro");	        
	        
        	if(isbn != null) {
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
        	} else if(verTodos != null){
        		for(int i = 0; i < libros.size(); i++) {
        			out.println("<hr>");
	        		out.println("ISBN: " + libros.get(i).getIsbn() + "<br>");
	        		out.println("Título: " + libros.get(i).getTitulo() + "<br>");
	        		out.println("Autor: " + libros.get(i).getAutor() + "<br>");
	        		out.println("Año: " + libros.get(i).getYear() + "<br>");
	        		out.println("<button class='btn btn-outline-success my-2 my-sm-0' type='button' id='"+i+"' value='" + libros.get(i).getIsbn() + "'onClick='menuModificar(this.id)'>Modificar libro</button>");
        		}
        	} else if(eliminarLibro != null) {
        			boolean eliminado = false;
	    			for(int i=0; i<libros.size(); i++) {
	            		if(eliminarLibro.equals(libros.get(i).getIsbn())) {
	            			libros.remove(i);
	            			eliminado = true;
	            			out.println("<font color='green'>Libro eliminado.</font>");
	            		}
	            	}
	    			if(eliminado != true) {
	    				out.println("<font color='red'>ISBN no encontrado.</font>");
	    			}
    		} else if(menuAgregar != null){
    			out.println("<hr>");
    			out.println("AÑADIR LIBRO");
        		out.println("<hr>");
        		out.println("ISBN: <input class='form-control mr-sm-2' type='text' id='agregarISBN'/><br>");
        		out.println("Título: <input class='form-control mr-sm-2' type='text' id='agregarTitulo'/><br>");
        		out.println("Autor: <input class='form-control mr-sm-2' type='text' id='agregarAutor'/><br>");
        		out.println("Año: <input class='form-control mr-sm-2' type='text' id='agregarYear'/><br>");
        		out.println("<button class='btn btn-outline-success my-2 my-sm-0' type='button' id='agregar' onClick='agregarLibro()'>Añadir libro</button>");
        	} else if(agregarISBN != null && agregarTitulo != null && agregarAutor != null && agregarYear != null) {
        		boolean libroAgregado = true;
        		for(int i=0; i<libros.size(); i++) {
        			if(libros.get(i).getIsbn().equals(agregarISBN)) {
        				out.println("<font color='red'>Ya hay un libro con ese ISBN en la biblioteca.</font>");
        				libroAgregado = false;
        			}
        		}
        		if(libroAgregado == true) {
        			if(agregarISBN != "" && agregarTitulo != "" && agregarAutor != "" && agregarYear != "") {
            			libros.add(new Libro(agregarISBN, agregarTitulo, agregarAutor, agregarYear));
                		out.println("<font color='green'>Libro añadido.</font>");
        			} else {
        				out.println("<font color='red'>Debes rellenar todos los campos para añadir un libro.</font>");
        			}
        		}
        	} else if(menuModificar1 != null) {
        		out.println("<hr>");
        		out.println("MODIFICAR LIBRO");
        		out.println("<hr>");
        		out.println("ISBN: <input class='form-control mr-sm-2' type='text' id='modificarISBN'/><br>");
        		out.println("Título: <input class='form-control mr-sm-2' type='text' id='modificarTitulo'/><br>");
        		out.println("Autor: <input class='form-control mr-sm-2' type='text' id='modificarAutor'/><br>");
        		out.println("Año: <input class='form-control mr-sm-2' type='text' id='modificarYear'/><br>");
        		out.println("<button class='btn btn-outline-success my-2 my-sm-0' type='button' id='modificarLibroValue' value='"+menuModificar1+"' onClick='modificarLibro()'>Modificar libro</button>");
        	} else if(modificarISBN != null) {
        		boolean libroModificado = true;
        		for(int i=0; i<libros.size(); i++) {
        			if(libros.get(i).getIsbn().equals(modificarISBN)) {
        				out.println("<font color='red'>Ya hay un libro con ese ISBN en la biblioteca.</font>");
        				libroModificado = false;
        			}
        		}
        		if(libroModificado == true) {
        			if(modificarISBN == "") {
        				out.println("<font color='red'>Debes introducir al menos el ISBN.</font>");
        			} else {
        					int modificarLibro = Integer.parseInt(modificarLibro1);
    						libros.get(modificarLibro).setIsbn(modificarISBN);
            				if(modificarTitulo != "") {
                				libros.get(modificarLibro).setTitulo(modificarTitulo);
                			}
            				if(modificarAutor != "") {
                				libros.get(modificarLibro).setAutor(modificarAutor);
                			}
            				if(modificarYear != "") {
                				libros.get(modificarLibro).setYear(modificarYear);
                			}
    				out.println("<font color='green'>Libro modificado.</font>");
        				}
        			}
    			} else {
    				out.println("<font color='red'>Debes introducir al menos el ISBN.</font>");
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