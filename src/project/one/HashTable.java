package project.one;

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
            if (args.length > 0 && args[0] != null) {
                HashArray hasher = new HashArray(args[0]);
            }
            else {
                System.out.printf("No Input File Given%n");
            }
        }
        catch (Exception e) {
            System.out.printf(e.toString());
        }
    }
    
}