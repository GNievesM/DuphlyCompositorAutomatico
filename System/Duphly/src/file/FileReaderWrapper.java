package file;

import java.io.*;

public class FileReaderWrapper {

    String line = "";
    BufferedReader input;

    public FileReaderWrapper(String fileName) {
        try {
            input = new BufferedReader(new FileReader(fileName));
        } 
        catch (FileNotFoundException e) {
            System.out.println("The file does not exist");
        }
    }

    public boolean hasMoreLines() {
        try {
            line = input.readLine();
        } 
        catch (IOException e) {
            line = null;
        }
        return (line != null);
    }

    public String getLine() {
        return line;
    }

    public boolean close() {
        boolean success = true;
        try {
            input.close();
        } 
        catch (Exception e) {
            System.out.println("Error closing the file");
            success = false;
        }
        return success;
    }
}
