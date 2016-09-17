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
 * table based on file contents
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

class HashArray { 
    
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
            e.printStackTrace();
        }
    }
    
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
    
    private int collisionResolver(int index, int collisions) {
        return ((index * index) + collisions) % this.size;
    }
    
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
    
    private void writeToFile(String message) {
        try (
                FileWriter fileWriter = new FileWriter(this.outputFilename, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter outputWriter = new PrintWriter(bufferedWriter);
            ) {
            outputWriter.printf(message);
            System.out.printf(message);
        }
        catch (IOException ex) {
            System.out.printf("%s%n", ex.getMessage());
            ex.getStackTrace();
        }
    }
}//end Class HashTable

class DataItem {
    private String label;
    private int value;
    
    public DataItem(String label, int value) {
        this.label = label;
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof DataItem) {
            DataItem x = (DataItem) o;
            return x.getLabel().equals(this.label);
        }
        else {
            return false;
        }
    }
}// end Class DataItem