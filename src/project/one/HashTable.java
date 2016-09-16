package project.one;
import java.io.File;
import java.io.FileNotFoundException;
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
        return;
    }
    
}

class HashArray { 
    
    private DataItem[] data;
    private int size;
    private DataItem deletedRecord;
    
    public HashArray(String fileName) {
        this.size = 100;
        this.deletedRecord = new DataItem(" ", 0);
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
        value = value;
        return value;
    }
    
    private void insertData(DataItem item) {
        int insertionIndex = this.hashFunction(item.getLabel(), this.size);
        int collisions = 0;
        boolean searching = true;
        
        while (searching) {
            if (this.data[insertionIndex] == null) {
                this.data[insertionIndex] = item;
                System.out.printf("Inserting '%s' with value: %d at index: %d with %d collisions %n", item.getLabel(), item.getValue(), insertionIndex, collisions);
                searching = false;
            }
            else if (item.equals(this.data[insertionIndex])) {
                System.out.printf("ERROR: '%s' already exists at index %d %n", item.getLabel(), insertionIndex);
                searching = false;
            }
            else {
                // Going to next cell
                collisions++;
                insertionIndex = ((insertionIndex * insertionIndex) + collisions) % this.size;
            }
        }
        
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
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof DataItem) {
            DataItem x = (DataItem) o;
            if (x.getLabel().equals(this.label)){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}// end Class DataItem