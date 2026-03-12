public class DSAStack {
    private Object[] stack;
    private int count; //Number of elements in the stack
    private int capacity; //The maximum number of elements the stack can hold

    //Default Constructor 
    public DSAStack() {
        this.capacity = 100; //the default capacity
        this.stack = new Object[capacity];
        this.count = 0; //to show that the stack is initiall empty    
    }

    //Constructor with a specific capacity 
    public DSAStack(int maxCapacity) {
        this.capacity = maxCapacity; //the default capacity
        this.stack = new Object[maxCapacity];
        this.count = 0; //to show that the stack is initiall empty 
    }

    //get the amount of elementx in stack
    public int getCount() {
        return count;
    }

    //check if the stack is empty 
    public boolean isEmpty() {
        boolean empty = (count == 0); //if there is nothing in the stack (count == 0), stack empty;
        return empty;
    }

    //check if the stack is full
    public boolean isFull() {
        boolean full = (count == capacity); //if the count == capacity, all stack slots are full.
        return full;
    }

    //add element to the stack
    public void push(Object item) {
        if (isFull()) {
            throw new IllegalStateException("Stack is full!");
        } 
        else {
            stack[count] = item; //Add item to the top of the stack
            count++; //Add 1 to the count as an item is added 
        }
    }

    //remove element from the stack
    public Object pop() {
        Object topItem = top(); //get the top item 
        stack[count - 1] = null; //remove the top item from the stack
        count--; //decrease the count by 1
        return topItem; 
    }

    //get the top element without removing it.
    public Object top() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }
        else {
            Object topItem = stack[count -1]; //get the top item
            return topItem;
        }
    }

    public void clear() {
        while (!isEmpty()) {
            pop();
        } //iterate through the stack removing all items. 
    }
}

