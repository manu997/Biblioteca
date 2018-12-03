package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Biblioteca")
public class Biblioteca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Biblioteca() {
        super();
    }
    
    ArrayList<String> libros = new ArrayList<String>();
    
    public void leeFicheroLibros() throws IOException {
    	String libro;
    	
    	File ficheroLibros = new File("ficheros/libros.txt");
    	FileReader fr = new FileReader(ficheroLibros);
    	BufferedReader br = new BufferedReader(fr);
    	
    	while((libro = br.readLine()) != null) {
            libros.add(libro);
        }
    	br.close();
    }

    protected void procesaSolicitud(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    	
    	Boolean esAjax;
	    esAjax="XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")); // Cabecera X-Requested-With
	    if (esAjax) {
	        //Recoge el parámetro isbn
	        String isbn = request.getParameter("isbn");
	        boolean isbnValido = false;
	        //String[] libroSeleccionado = {""};
	        String libro = "";
	        
        	for(int i=0; i<libros.size(); i++) {
        		String[] datosLibro = libros.get(i).split(";");
        		if(datosLibro[0].equals(isbn)) {
        			isbnValido = true;
        			libro = datosLibro[1];
        			break;
        		}
        	}
        	if(isbnValido == false) {
        		out.println("ISBN no válido.");
        	} else {
        		/*out.println("ISBN: " + libroSeleccionado[0]);
        		out.println("Título: " + libroSeleccionado[1]);
        		out.println("Autor: " + libroSeleccionado[2]);
        		out.println("Año: " + libroSeleccionado[3]);*/
        		out.println("ISBN válido.");
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
