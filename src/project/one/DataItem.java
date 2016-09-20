package project.one;

public class DataItem {
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
    
    /**
     * Returns True if o is a DataItem with the same label
     * @param o Object
     * @return boolean
     */
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