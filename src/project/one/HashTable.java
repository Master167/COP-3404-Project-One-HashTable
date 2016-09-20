package project.one;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * COP 3404 Project One - Create a Symbol table that reads a file and fills the 
 * table based on the input file contents
 * 
 * @author Michael Frederick (n00725913)
 */
public class HashTable {

    /**
     * main()
     * @param args the command line arguments with the first being the filename
     */
    public static void main(String[] args) {
        try {
            if (args[0] != null) {
                HashArray hasher = new HashArray(args[0]);
            }
        }
        catch (Exception e) {
            System.out.printf(e.toString());
            e.printStackTrace();
        }
    }
    
}