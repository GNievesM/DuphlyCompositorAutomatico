package file;
import java.io.*;

/*
 * Create a class named FileRecording that allows:
 *   - Creating a file with the given name
 *   - Writing a new line
 *   - Closing the file
 */
public class FileRecording {

    BufferedWriter out;
    
    public FileRecording(String fileName){
        
        try {
            out = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            System.out.println("Unable to create file.");
        }
    }
    
    public boolean writeLine(String line){
        boolean success = true;
        try {
            out.write(line);
            out.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to the file.");
            success = false;
        }
        return success;
    }
    
    public boolean close(){
        boolean success = true;
        try {
            out.flush();
            out.close();
            
        } catch (IOException e) {
            System.out.println("Error closing the file.");
            success = false;
        }
        return success;
    }
}