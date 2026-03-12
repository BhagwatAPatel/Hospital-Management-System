public class DSAHeapEntry {
    private int priority;
    private Object value;

    //Constructor
    public DSAHeapEntry(int priority, Object value) {
        this.priority = priority;
        this.value = value;
    }

    //Getters 
    public int getPriority() {
        return priority;
    }

    public Object getValue() {
        return value;
    }

    //setters 
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
