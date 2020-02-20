package archivo;
import java.io.*;

/*
 * Crear una clase de nombre ArchivoGrabaci�n que permita:
 *	crear el archivo de nombre dado
 *	grabar una nueva l�nea
 *  cerrar el archivo
 * 
 */
public class ArchivoGrabacion {

	BufferedWriter out;
	
	public ArchivoGrabacion (String unNombre){
		
		try {
			out = new BufferedWriter (new FileWriter(unNombre));
		} catch (IOException e) {
			System.out.println("No se puede Crear Archivo");
		}
	}
	public boolean grabarLinea(String linea){
		boolean ok = true;
		try {
			out.write(linea);
			out.newLine();
		} catch (IOException e) {
			System.out.println("Error de escritura");
			ok=false;
		}
		return ok;
		
	}
	public boolean cerrar(){
		boolean ok = true;
		try {
			out.flush();
			out.close();
			
		} catch (IOException e) {
			System.out.println("Error de al cerrar archivo");
			ok = false;
		}
		return ok;
	}
	
}
