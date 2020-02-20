package archivo;

import java.io.*;

public class ArchivoLectura {

	String linea = "";
	BufferedReader entrada;

	public ArchivoLectura(String unNombre) {
		try {
			entrada = new BufferedReader(new FileReader(unNombre));
		} 
		catch (FileNotFoundException e) {
			System.out.println("El archivo no existe");
			
		}
	}

	public boolean hayMasLineas() {
		try {
			linea = entrada.readLine();
		} 
		catch (IOException e) {
			linea = null;			
		}
		return (linea != null);
	}

	public String linea() {
		return linea;
	}

	public boolean cerrar() {
		boolean ok = true;
		try {
			entrada.close();
		} 
		catch (Exception e) {
			System.out.println("Error al cerrar el archivo");
			ok = false;
			
		}
		return ok;
	}

	
	
}
