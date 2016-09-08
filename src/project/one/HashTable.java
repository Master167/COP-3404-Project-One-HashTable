package project.one;
import java.util.ArrayList;
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
        // TODO code application logic here
    }
    
}

class HashArray { 
    
    public DataItem[] data;
    
    public HashArray() {
        
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
    
    // Create a getNext() method, 
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