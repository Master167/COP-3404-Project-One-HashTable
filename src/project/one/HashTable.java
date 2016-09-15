package project.one;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

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
        }
        return;
    }
    
}

class HashArray { 
    
    private DataItem[] data;
    private int size;
    private DataItem deletedRecord;
    
    public HashArray(String fileName) {
        this.size = 1024;
        this.deletedRecord = new DataItem(" ", 0);
        try {
            File file = new File(fileName);
            this.data = createArray(file);
        }
        catch (Exception e) {
            System.out.printf(e.toString());
        }
    }
    
    private DataItem[] createArray(File file) {
        DataItem[] temp = new DataItem[this.size];

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
      
        return temp;
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
        System.out.printf("%d was calculated%n", value);
        return value;
    }
    
    private void insertData(DataItem item) {
        int insertionIndex = this.hashFunction(item.getLabel(), this.size);
        boolean emptyArea = false;
        // Check for Collisions
        while (!emptyArea) {
            if (this.data[insertionIndex] != null) {
                emptyArea = true;
            }
            else {
                // Going to next cell
                System.out.printf("COLLISION ON INSERT%n");
                insertionIndex = (insertionIndex * insertionIndex) % this.size;
            }
        }
        this.data[insertionIndex] = item;
        System.out.printf("Inserting %s with value: %d at %d %n", item.getLabel(), item.getValue(), insertionIndex);
    }
    
    private void searchForData(String key) {
        System.out.printf("Seraching for %s%n", key);
    }
    
    private void writeToFile(String message) {
        
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
}// end Class DataItem