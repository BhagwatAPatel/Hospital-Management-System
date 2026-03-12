//Doubly-linked and Double-ended linked list implementation

public class DSALinkedList {
    private class DSAListNode {
        public Object value; //Data stored in the node
        public DSAListNode next; //Pointer to the next node
        public DSAListNode prev; //Pointer to the previous node

        //Node constructor
        public DSAListNode(Object item) {
            value = item; //Set the value
            next = null; //Initial next pointer to null
            prev = null; //Initial prev pointer to null
        }
    }

    private DSAListNode head; //Pointer to the first node
    private DSAListNode tail; //Pointer to the last node 
    private int count; //Number of elements in the list

    //Default constructor 
    public DSALinkedList() {
        head = null; //initial head to null
        tail = null; //intial tail to null
        count = 0; //initial count to 0
    }

    //get the amount of elementx in list
    public int getCount() {
        return count;
    }

    //check if the list is empty 
    public boolean isEmpty() {
        boolean empty = (count == 0); //if there is nothing in the list (count == 0), list empty;
        return empty;
    }

    //add an element to the front of the list
    public void insertFirst(Object item) {
        DSAListNode newNode = new DSAListNode(item);

        if(isEmpty()) {
            head = newNode; //if the list is empty, set head to the new node
            tail = newNode; //set tail to the new node as well 
        }
        else {
            newNode.next = head; //set the new node's next to the current head
            head.prev = newNode; //set the current head's prev to the new node
            head = newNode; //set the head to the new node
        }
        count++; //increase the count by 1
    }

    //add an element to the end of the list
    public void insertLast(Object item) {
        DSAListNode newNode = new DSAListNode(item);

        if(isEmpty()) {
            head = newNode; //if the list is empty, set head to the new node
            tail = newNode; //set tail to the new node as well 
        }
        else {
            tail.next = newNode; //set the new node's to be after the current tail
            newNode.prev = tail; //set the new node's prev to the current tail
            tail = newNode; //set the tail to the new node
        }
        count++; //increase the count by 1
    }

    //remove the first element from the list
    public Object removeFirst() throws Exception {
        if(isEmpty()) {
            throw new Exception("List is empty!");
        }

        Object value = head.value; //get the value of the head

        if (head == tail) {
            head = null; //if there is only one element in list, set head to null
            tail = null; //set tail to null as well
        }
        else {
            head = head.next; //set head to the next node 
            head.prev = null; //set the new head's prev to null 
        }
        count--; //decrease the count by 1
        return value; //return the removed value 
    }

    //remove the last element from the list
    public Object removeLast() throws Exception {
        if(isEmpty()) {
            throw new Exception("List is empty!");
        }

        Object value = tail.value; //get the value of the head

        if (head == tail) {
            head = null; //if there is only one element in list, set head to null
            tail = null; //set tail to null as well
        }
        else {
            tail = tail.prev; //move tail to the previous node
            tail.next = null; //set the new tails next to null
        }
        count--; //decrease the count by 1
        return value; //return the removed value 
    }

    //peek at the first element without removing it 
    public Object peekFirst() throws Exception {
        if(isEmpty()) {
            throw new Exception("List is empty!");
        }

        Object value = head.value; //get the value of the head
        return value;
    }

    //peek at the Last element without removing it 
    public Object peekLast() throws Exception {
        if(isEmpty()) {
            throw new Exception("List is empty!");
        }
        
        Object value = tail.value; //get the value of the Tail
        return value;
    }

    //print the list 
    public void printList() {
        DSAListNode current = head; //start the printing from the head
        while(current != null) {
            System.out.println(current.value + "->"); //print the current node's value
            current = current.next; //move the current pointer to the next node 
        }
    }

    //get the value of a specific index
    public Object getAt(int index) throws Exception {
        if (index < 0 || index >= count) {
            throw new Exception("Indx is out of bounds!");
        }
        
        DSAListNode current = head; //start from the head 
        for (int i = 0; i < index; i++) {
            current = current.next; //move to the next node
        }

        return current.value; //return the value of the index.
    }

    //remove an element at a specific index 
    public Object removeAt(int index) throws Exception {
        if (index < 0 || index >= count) {
            throw new Exception("Indx is out of bounds!");
        }

        DSAListNode current = head; //start from the head

        if (index == 0) {
            current.value = removeFirst(); //remove the first element
        }
        else if (index == count -1) {
            current.value = removeLast(); //remove the last element
        }
        else {
            for (int i = 0; i < index; i++) {
                current = current.next; //move to the next node
            }

            //unlink the current node
            current.prev.next = current.next;
            current.next.prev = current.prev;
            count--; //decrease the count by 1
        }

        return current.value; //return the removed value
    }

    //set the value of a specific index
    public void setAt(int index, DSAGraphNode node) throws Exception {
        if (index < 0 || index >= count) {
            throw new Exception("Indx is out of bounds!");
        }

        DSAListNode current = head; //start from the head 
        for (int i = 0; i < index; i++) {
            current = current.next; //move to the next node
        }
        
        current.value = node; //set the value at the index to the new node
    }
}
