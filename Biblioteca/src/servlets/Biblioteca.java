package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
    
    protected void leeFicheroLibros() throws FileNotFoundException {
    	File ficheroLibros = new File("/ficheros/libros.txt");
    	FileReader fr = new FileReader(ficheroLibros);
    	BufferedReader br = new BufferedReader(fr);
    	
    	ArrayList<String> libros = new ArrayList<String>();
    	
    }
    
    protected void procesaSolicitud(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    	
    	Boolean esAjax;
	    esAjax="XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")); // Cabecera X-Requested-With
	    if (esAjax) {
	        //Recoge el parámetro isbn
	        String usuario=request.getParameter("isbn");
	        Boolean isbnValido=false;
	        
	        if (isbnValido==false) {
	            System.out.println("Usuario no válido");
	        }
	    } else {
	        out.println("Este servlet solo se puede invocar vía Ajax");
	    }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
