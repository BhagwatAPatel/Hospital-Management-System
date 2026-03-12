public class DSAQueue {
    private Object[] queue; 
    private int count; //Number of elements in the queue
    private int capacity; //The maximum number of elements the queue can hold

    //Default Constructor 
    public DSAQueue() {
        this.capacity = 100; //the default capacity
        this.queue = new Object[capacity];
        this.count = 0; //to show that the queue is initiall empty    
    }

    //Constructor with a specific capacity 
    public DSAQueue(int maxCapacity) {
        this.capacity = maxCapacity; //the default capacity
        this.queue = new Object[maxCapacity];
        this.count = 0; //to show that the queue is initiall empty 
    }

    //get the amount of elements in queue
    public int getCount() {
        return count;
    }

    //check if the queue is empty 
    public boolean isEmpty() {
        boolean empty = (count == 0); //if there is nothing in the queue (count == 0), queue empty;
        return empty;
    }

    //check if the queue is full
    public boolean isFull() {
        boolean full = (count == capacity); //if the count == capacity, all queue slots are full.
        return full;
    }

    public void enqueue(Object item) {
        if(isFull()) {
            throw new IllegalStateException("Queue is full!");
        }
        else {
            queue[count] = item; //Add item to the end of the queue
            count++; //Add 1 to the count as an item is added
        }
    }

    public Object dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty!");
        }

        Object frontItem = queue[0]; //get the front item;
    
        // Shift all element to the left by one
        for (int i = 1; i < count; i++) {
            queue[i - 1] = queue[i];
        }

        queue[count - 1] = null; //remove the last item
        count--; //decrease the count by 1
        return frontItem;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty!");
        }
        else{
            Object frontItem = queue[0]; //get the front
            return frontItem;
        }
    }

    public void clear() {
        //iterate through queue, removing all items
        while (!isEmpty()) {
            dequeue();
        }
    }
}