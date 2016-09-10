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
    
    public HashArray(String fileName) {
        this.size = 1024;
        
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
                }
                else {
                    tempData = new DataItem(string[0]);
                }
                
                temp[hashFunction(tempData.getLabel(), this.size)] = tempData;
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
}

class ArrayIterator {
    private int currentIndex;
    private int lastIndex;
    
    private DataItem[] data;
    
    public ArrayIterator(DataItem[] array) {
        this.data = array;
        this.currentIndex = 0;
        this.lastIndex = array.length;
    }
    
    public ArrayIterator(DataItem[] array, int start) {
        this.data = array;
        this.currentIndex = start;
        this.lastIndex = array.length;
    }
    
    public DataItem getNext() {
        DataItem item;
        if (currentIndex + 1 != lastIndex) {
            item = data[++currentIndex];
        }
        else {
            item = data[0];
            currentIndex = 0;
        }
        
        return item;
    }
}

class DataItem {
    private String label;
    private int value;
    
    public DataItem(String label, int value) {
        this.label = label;
        this.value = value;
    }
    
    public DataItem(String label) {
        this.label = label;
        this.value = this.calculateValue(label);
    }
    
    public int getValue() {
        return this.value;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    private int calculateValue(String str) {
        int number = 0;
        
        for(char a : str.toCharArray()) {
            number += (int) a;
        }
        
        return number;
    }
}