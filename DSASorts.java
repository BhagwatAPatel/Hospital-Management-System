public class DSASorts {
   
    //MERGE SORT METHODS

    public static Patient[] mergeSort(Patient[] patients) {
        boolean shouldSort = (patients != null && patients.length > 1);
        
        //Base case
        if (shouldSort) {
            //Recursion call
            mergeSortRec(patients, 0, patients.length - 1); 
        }
        return patients;
    }


    private static void mergeSortRec(Patient[] patients, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int midIndex = leftIndex + (rightIndex - leftIndex) / 2; //Calculate the middle index

            mergeSortRec(patients, leftIndex, midIndex); //sort the left half 

            mergeSortRec(patients, midIndex + 1, rightIndex); //sort the right half

            merge(patients, leftIndex, midIndex, rightIndex); //merge halves
        }
    }

    private static void merge(Patient[] patients, int leftIndex, int midIndex, int rightIndex) {
        //Calculate the size of each subArray
        int sizeLeft = midIndex - leftIndex + 1;
        int sizeRight = rightIndex - midIndex;

        //Create temporary arrays
        Patient[] leftTemp = new Patient[sizeLeft];
        Patient[] rightTemp = new Patient[sizeRight];

        //Copy data to the temporary arrays
        for (int i = 0; i < sizeLeft; i++) {
            leftTemp[i] = patients[leftIndex + i];
        }

        for (int j = 0; j < sizeRight; j++) {
            rightTemp[j] = patients[midIndex + 1 + j];
        }

        //Index for loops to combine the subarrays into one main array
        int i = 0; //Index for left
        int j = 0; //Index for right
        int k = leftIndex; //Index for main

        //Compare elements from both arrays and place in smaller the smaller value into the main array 
        while (i < sizeLeft && j < sizeRight) {
            //Compare treatment time and merge in ascending order
            if (leftTemp[i].getTreatmentTime() <= rightTemp[j].getTreatmentTime()) {
                patients[k] = leftTemp[i]; //place left into the main
                i++;
            }
            else {
                patients[k] = rightTemp[j]; //place right into the main
                j++;
            }
            k++;
        }

        //Copy any left over elements 
        while (i < sizeLeft) {
            patients[k] = leftTemp[i];
            i++;
            k++;
        }

        while (j < sizeRight) {
            patients[k] = rightTemp[j];
            j++;
            k++;
        }
    }


    //QUICK SORT: MEDIAN OF THREE PIVOT
    public static Patient[] quickSort(Patient[] patients) {
        boolean shouldSort = (patients != null && patients.length > 1);

        //Base case 
        if (shouldSort) {
            quickSortRec(patients, 0, patients.length - 1); //Recursion call
        }

        return patients;
    }

    private static void quickSortRec(Patient[] patients, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            //Partition the array to get the final pivoting position
            int pivotIndex = partition(patients, leftIndex, rightIndex);

            //Recursion sort element before pivot
            quickSortRec(patients, leftIndex, pivotIndex - 1);

            //Recursion sort element after pivot
            quickSortRec(patients, pivotIndex + 1, rightIndex);
        }
    }

    //Method to split the array up for quickSort
    private static int partition(Patient[] patients, int leftIndex, int rightIndex) {
        //Use the median of three strategy to get the pivot
        int midIndex = leftIndex + (rightIndex - leftIndex) / 2;
        int pivotIndex = medianOfThree(patients, leftIndex, midIndex, rightIndex);

        //Move pivot to the end 
        swap(patients, pivotIndex, rightIndex);

        //Get the treatment time
        double value = patients[rightIndex].getTreatmentTime();

        int i = leftIndex -1; //tracks boundaries between small and large elements

        //Iterate through array and swap elements
        for (int j = leftIndex; j < rightIndex; j++) {
            //Check if element should be in left part of the array
            if(patients[j].getTreatmentTime() <= value) {
                i++; //Increment left halve
                swap(patients, i, j); //Move element into left halve
            }
        }

        //place pivot in final position
        swap(patients, i + 1, rightIndex);

        return i + 1; //return pivot position
    }

    //get the median of the three
    private static int medianOfThree(Patient[] patients, int a, int b, int c) {
        //get the treatment times
        double val1 = patients[a].getTreatmentTime();
        double val2 = patients[b].getTreatmentTime();
        double val3 = patients[c].getTreatmentTime();
        int median;

        //Check which is the median
        if ((val1 <= val2 && val2 <= val3) || (val3 <= val2 && val2 <= val1)) {
            median = b;
        }
        else if ((val2 <= val1 && val1 <= val3) || (val3 <= val1 && val1 <= val2)) {
            median = a;
        }
        else {
            median = c;
        }

        return median;
    }

    //Swap method
    private static void swap(Patient[] patients, int i, int j) {
        Patient temp = patients[i];
        patients[i] = patients[j];
        patients[j] = temp;
    }

    //Copy patient data to test multpile sorting methods
    public static Patient[] copy(Patient[] original) {
        if (original == null) {
            throw new IllegalArgumentException("Patients list is empty");
        }

        Patient[] copy = new Patient[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i];
        }

        return copy;
    }

    //Display method to ensure the array is sorted
    public static void displaySorted(Patient[] patient, String label) {
        System.out.println("\n" + label);
        System.out.println("------------------------------------------------");
        for (int i = 0; i < patient.length; i++) {
            System.out.println("[" + i + "] " + patient[i].getTreatmentTime() + " mins - " + patient[i].getPatientID());
        }
        System.out.println("------------------------------------------------");
    }

}
