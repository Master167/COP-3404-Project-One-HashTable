package project.one;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Builds and contains a DataItem table based on the input file given
 * @author Michael Frederick (n00725913)
 */
public class HashArray { 
    
    private DataItem[] data;
    private int size;
    private DataItem deletedRecord;
    private String outputFilename;
    
    public HashArray(String fileName) {
        this.size = 100;
        this.deletedRecord = new DataItem(" ", 0);
        this.outputFilename = "output.txt";

        try {
            File file = new File(fileName);
            this.data = createArray(file);
        }
        catch (Exception e) {
            System.out.printf(e.toString());
        }
    }
    
    /**
     * Creates the DataItem array based on the input file contents
     * @param file
     * @return 
     */
    private DataItem[] createArray(File file) {
        this.data = new DataItem[this.size];

        try {
            Scanner fileReader = new Scanner(file);
            DataItem tempData;
            int i = 0;
            while (fileReader.hasNextLine()) {
                String[] string = fileReader.nextLine().split(" ");
                if (string.length == 2) {
                    tempData = new DataItem(string[0], Integer.parseInt(string[1]));
                    insertData(tempData);
                }
                else {
                    searchForData(string[0]);
                }
            }
        }
        catch (FileNotFoundException ex) {
            System.out.printf(file.getName() + " was not found.%n");
        }
      
        return data;
    }
    
    /**
     * Hashes the string by each character value and Horner's Polynomial
     * @param str
     * @param maxSize
     * @return int
     */
    private int hashFunction(String str, int maxSize) {
        int value;
        char[] charArray = str.toCharArray();
        int temp;
        value = charArray[0];
        for (int i = 1; i < charArray.length; i++) {
            temp = charArray[i];
            value = (value * 26 + temp) % maxSize;
        }
        return value;
    }
    
    /**
     * How I resolve a data collision
     * @param index
     * @param collisions
     * @return Next Index to be checked
     */
    private int collisionResolver(int index, int collisions) {
        return ((index * index) + collisions) % this.size;
    }
    
    /**
     * Inserts the DataItem into the array, if item with the same label does not exist
     * @param item 
     */
    private void insertData(DataItem item) {
        int insertionIndex = this.hashFunction(item.getLabel(), this.size);
        int collisions = 0;
        boolean searching = true;
        
        while (searching) {
            if (this.data[insertionIndex] == null) {
                this.data[insertionIndex] = item;
                writeToFile("Inserting '" + item.getLabel() + "' with value: " + item.getValue() + " at index: " + insertionIndex + " with " + collisions + "\n");
                searching = false;
            }
            else if (item.equals(this.data[insertionIndex])) {
                writeToFile("ERROR: '" + item.getLabel() + "' already exists at index: " + insertionIndex + "\n");
                searching = false;
            }
            else {
                // Going to next cell
                collisions++;
                insertionIndex = this.collisionResolver(insertionIndex, collisions);
            }
        }
        
    }

    /**
     * Searches the Data array for the DataItem with the same string
     * @param key 
     */
    private void searchForData(String key) {
        int insertionIndex = this.hashFunction(key, this.size);
        int jumps = 0;
        boolean searching = true;
        
        while (searching) {
            if (this.data[insertionIndex] == null || this.data[insertionIndex].equals(deletedRecord)) {
                writeToFile("ERROR: '" + key + "' not found\n");
                searching = false;
            }
            else if (this.data[insertionIndex].getLabel().equals(key)) {
                writeToFile("'" + key + "' with value: " + this.data[insertionIndex].getValue() + " found at index: " + insertionIndex + "\n");
                searching = false;
            }
            else {
                jumps++;
                insertionIndex = this.collisionResolver(insertionIndex, jumps);
            }
        }
    }
    
    /**
     * Outputs the message to the Output file
     * @param message 
     */
    private void writeToFile(String message) {
        try (
                FileWriter fileWriter = new FileWriter(this.outputFilename, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter outputWriter = new PrintWriter(bufferedWriter);
            ) {
            outputWriter.printf(message);
            //System.out.printf(message);
        }
        catch (IOException ex) {
            System.out.printf("%s%n", ex.getMessage());
        }
    }
}//end Class HashTable