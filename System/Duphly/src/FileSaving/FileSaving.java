package FileSaving;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaving {
    BufferedWriter out;
    
    public FileSaving(String fileName) {
        try {
            out = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            System.out.println("Unable to Create File");
        }
    }
    
    public boolean writeLine(String line) {
        boolean success = true;
        try {
            out.write(line);
            out.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            success = false;
        }
        return success;
    }
    
    public boolean close() {
        boolean success = true;
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("Error closing the file");
            success = false;
        }
        return success;
    }
}
