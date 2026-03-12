public class DSAHeap {
    private DSAHeapEntry[] heap;
    private int count;

    //constructor
    public DSAHeap (int size) {
        heap = new DSAHeapEntry[size]; //create the array to hold all entries
        count = 0; //count the number of entries
    }

    //Basic Getter methods for this class
    public int getCount() {
        return count;
    }

    public boolean getEmpty() {
        return count == 0;
    }

    //Add a new patient to the heap array
    public void insertPatient(Patient patient) {
        if (count >= heap.length) {
            throw new IllegalArgumentException("Heap is full");
        }

        if (patient == null) {
            throw new IllegalArgumentException("Patient is Empty");
        }


        int priority = patient.calcPriority(); //get the priority based on the patients details 

        //Add the patient to the heap in sorted order
        heap[count] = new DSAHeapEntry(priority, patient);
        trickleUp(count); //start from the bottom of the heap and place elements in correct position
        count++;
    }

    //Remove a patient from the heap array
    public Patient removePatient() {
        if (count == 0) {
            throw new IllegalArgumentException("Heap is Empty");
        }


        DSAHeapEntry parent = heap[0]; //get the first element or element with highest priority
        count--;
        heap[0] = heap[count]; //get patient with lowest priority
        heap[count] = null;
        trickleDown(0); //build the heap top to bottom

        return (Patient)parent.getValue(); //return the patient 
    }

    //View the patient at the top of the Heap
    public Patient peekPatient() {
        if (count == 0) {
            throw new IllegalArgumentException("Heap is Empty");
        }
        return (Patient)heap[0].getValue(); //patient at the top of heap should have the highest priority
    }

    public int extractPriority() {
        if (count == 0) {
            throw new IllegalArgumentException("Heap is Empty");
        }
        return heap[0].getPriority(); 
    }

    //Display the contents of the heap
    public void displayHeap() {
        if (count == 0) {
            System.out.println("No patients in queue...");
        }
        else {
            System.out.println("-----Patient Queue-----");
            System.out.println("Total Patient in Queue: " + count);
            System.out.println("\n----------------------------------------------------------------------");

            //Cycle through the heap array and print contents
            for (int i = 0; i < count; i++) {
                Patient patient = (Patient)heap[i].getValue();
                System.out.println("Patient number: " + i + " | " + patient); //uses the toString to print the patient
            }
            System.out.println("\n----------------------------------------------------------------------");
        }
    }

    //Add heap entries in order from the bottom-up
    private void trickleUp(int index) {
        int parentIndex = (index - 1) / 2; 

        // Loop through until all values are in correct order 
        while (index > 0 && heap[index].getPriority() > heap[parentIndex].getPriority()) {
            DSAHeapEntry temp = heap[parentIndex];
            heap[parentIndex] = heap[index];
            heap[index] = temp;

            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    //Add heap entries in order from top-bottom
    private void trickleDown(int index) {
        int leftChild = index * 2 + 1;
        int rightChild = leftChild + 1;

        boolean loop = true;

        //Loop through untill all values in correct order
        while (loop && leftChild < count) {
            loop = false;
            int greaterIndex = leftChild;

            //check to see if right is greater then left 
            if (rightChild < count) {
                if (heap[leftChild].getPriority() < heap[rightChild].getPriority()) {
                    greaterIndex = rightChild;
                }
            }

            //If greater node is larger that current, swap 
            if (heap[greaterIndex].getPriority() > heap[index].getPriority()) {
                DSAHeapEntry temp = heap[greaterIndex];
                heap[greaterIndex] = heap[index];
                heap[index] = temp;

                //reset values
                loop = true;
                index = greaterIndex;
                leftChild = index * 2 + 1;
                rightChild = leftChild + 1;
            }
        }
    }

    //clear the heap 
    public void clear() {
        for (int i = 0; i < count; i++) {
            heap[i] = null;
        }
        count = 0;
    }
}