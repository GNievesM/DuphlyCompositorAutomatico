/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileSaving;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author gasto_000
 */
public class FileSaving {
    BufferedWriter out;
	
	public FileSaving (String FileName){
		
		try {
			out = new BufferedWriter (new FileWriter(FileName));
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
	public boolean close(){
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
