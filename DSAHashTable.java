public class DSAHashTable {
    //Private class for Hash Entries
    private class HashEntry {
        private String patientID; //Acts as the key
        private Patient patient; //The value to store
        private int state; 

        //Default Constructor for HashEntry
        public HashEntry() {
            patientID = "";
            patient = null;
            state = 0; // to indicate never used
        }

        //Constructor with specific values
        public HashEntry(String inPatientID, Patient inPatient) {
            patientID = inPatientID;
            patient = inPatient;
            state = 1; // to inidicate used
        }

        //getters
        public String getPatientID() {
            return patientID;
        }

        public Patient getPatient() {
            return patient;
        }

        public int getState() {
            return state;
        }

        //setters
        public void setPatientID(String inPatientID) {
            patientID = inPatientID;
        }

        public void setPatient(Patient inPatient) {
            patient = inPatient;
        }

        public void setState(int inState) {
            state = inState;
        }
    }

    //Class fields for HashTable
    private HashEntry[] hashArr;
    private int count;
    private int collisionCount; //track collisions
    private int probeCount; //track probes
    private int resizeCount; //track resizing
    private boolean loggingMode; //detailed logging switch/control

    //Constants for Hash functions
    private static final int MAX_STEP = 5;
    private static final double UPPER_LOAD_FACTOR = 0.7; //if capacity exceeds this, resize
    private static final double LOWER_LOAD_FACTOR = 0.2; //if capacity below this, shrink

    //Default constructor 
    public DSAHashTable() {
        this(20); 
    }

    //Constructor with user chosen sizing
    public DSAHashTable(int size) {
        int actualSize = nextPrime(size);
        hashArr = new HashEntry[actualSize];
        count = 0;
        collisionCount = 0;
        probeCount = 0;
        resizeCount = 0;
        loggingMode = false;

        //initialise all entries 
        for (int i = 0; i < actualSize; i++) {
            hashArr[i] = new HashEntry();
        }

        System.out.println("Created a hash table for patients with the capacity: " + actualSize);
    }

    //Control logging mode on/off
    public void setLoggingMode(boolean controlSwitch) {
        this.loggingMode = controlSwitch;
    }

    //Insert or Update patient record
    public boolean insert(Patient patient) {
        boolean result = true; 

        if (patient == null) {
            throw new IllegalArgumentException("Cannot insert null patient");
        }

        String patientID = patient.getPatientID();

        //check to see if resize is needed
        if (getLoadFactor() > UPPER_LOAD_FACTOR) {
            if(loggingMode) {
                System.out.println("\nLoad Factor: " + String.format("%.2f", getLoadFactor()) + " exceeds threshold."); 
            }
            resize(hashArr.length * 2);
        }

        int existingIndex = find(patientID); //wrote custom find() later

        // -1 meaning the index was previously used
        if (existingIndex != -1) {
            //Patient ID exists, update entry
            if(loggingMode) {
                System.out.println("\nUpdating ID: " + patientID + " as it already exists at " + existingIndex); 
            }
            hashArr[existingIndex].setPatient(patient);
            result = false; //false is to indicate an update was made
        }
        else {
            //Patient doesn't exist in system, add new entry
            int hashIndex = hash(patientID); //wrote custom hash() later 
            int origIndex = hashIndex;
            int stepSize = stepHash(patientID);
            boolean inserted = false;
            int probeSeq = 0;

            if (loggingMode) {
                System.out.println("\nInserting patient: " + patientID);
                System.out.println("Primary Hash index: " + hashIndex);
                System.out.println("Step Size: " + stepSize);
            }

            //Probe for an empty spot
            while (!inserted) {
                if (hashArr[hashIndex].getState() == 0 || hashArr[hashIndex].getState() == -1) {
                    //Found an available spot
                    hashArr[hashIndex].setPatientID(patientID);
                    hashArr[hashIndex].setPatient(patient);
                    hashArr[hashIndex].setState(1);
                    count++;
                    inserted = true;

                    if (loggingMode) {
                        if (probeSeq > 0) {
                            System.out.println("\nCollision resolved after " + probeSeq + " probe(s). Inserted at: " + hashIndex);
                        }
                        else {
                            System.out.println("\nNo collisions. Inserted at: " + hashIndex);
                        }
                    }
                }
                else {
                    //Double hashing for collision handling
                    collisionCount++;
                    probeSeq++;
                    probeCount++;

                    if (loggingMode) {
                        System.out.println("\nProbe " + probeSeq + ": Index " + hashIndex + " occupied by patient " + hashArr[hashIndex].getPatientID());
                    }

                    hashIndex = (hashIndex + stepSize) % hashArr.length;

                    if (hashIndex == origIndex) {
                        //just in case load factor is not managed properly
                        throw new RuntimeException("Hash table is full");
                    }
                }
            }
        }

        return result; //if true than an insert was made
    }

    //Search method to find a specific patient (this is a validation method)
    public Patient search(String patientID) {
        if (patientID == null || patientID.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be empty");
        }

        int hashIndex = find(patientID);
        Patient result;

        if (loggingMode) {
            System.out.println("\n Searched: " + patientID);
        }

        if (hashIndex == -1) {
            if (loggingMode) {
                System.out.println("\n Search unsuccessful");
            }
            result = null;
        }
        else {
            if (loggingMode) {
                System.out.println("\n Search found patient at: " + hashIndex);
            }
            result = hashArr[hashIndex].getPatient();
        }

        return result;
    }

    //Delete a patient record
    public boolean delete(String patientID) {
        if (patientID == null || patientID.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be empty");
        }

        int hashIndex = find(patientID);
        boolean result = true; //initialise to found

        if (hashIndex == -1) {
            if (loggingMode) {
                System.out.println("\n Delete unsuccessful");
            }
            result = false; //to indicate not found
        }
        else {
            if(loggingMode) {
                System.out.println("\n deleted patient " + patientID + " at: " + hashIndex);
            }

            hashArr[hashIndex].setState(-1); //mark as previously used
            count--;

            //resize the array to be smaller if below lower load factor
            if (count > 10 && getLoadFactor() < LOWER_LOAD_FACTOR) {
                if(loggingMode) {
                    System.out.println("\nLoad Factor: " + String.format("%.2f", getLoadFactor()) + " below threshold."); 
                }
                resize(hashArr.length / 2);
            }

        }

        return result;
    }

    //Check if patient exists
    public boolean hasPatient(String patientID) {
        return find(patientID) != -1;
    }

    //find the index at which a patient is at.
    private int find(String patientID) {
        int hashIndex = hash(patientID); 
        int origIndex = hashIndex;
        int stepSize = stepHash(patientID);
        boolean found = false;
        boolean terminate = false; 
        int probeSeq = 0;

        while (!found && !terminate) {
            probeCount++; //track all probes

            if (hashArr[hashIndex].getState() == 0) {
                // 0 means never used, hence no patient exists
                terminate = true;
            }
            else if (hashArr[hashIndex].getState() == 1 && hashArr[hashIndex].getPatientID().equals(patientID)) {
                // patient found
                found = true;
            }
            else {
                //continue searching
                probeSeq++;
                hashIndex = (hashIndex + stepSize) % hashArr.length;

                if(hashIndex == origIndex) {
                    //Wrapped around completely
                    terminate = true;
                }
            }
        }

        return found ? hashIndex : -1; //if found return the index otherwise return -1
    }

    //Primary hash functions
    private int hash(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key is null/empty");
        }
        int hashIndex = 0;

        for (int i = 0; i < key.length(); i++) {
            hashIndex = (31 * hashIndex) + key.charAt(i);
        }

        hashIndex = hashIndex % hashArr.length;
        if (hashIndex < 0) {
            hashIndex = hashIndex + hashArr.length;
        }

        return hashIndex;
    }

    //Secondary hash function for double hashing
    private int stepHash(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key is null/empty");
        }
        int hashStep = 0;
        
        for (int i = 0; i < key.length(); i++) {
            hashStep += key.charAt(i);
        }
        hashStep = MAX_STEP - (hashStep % MAX_STEP);
        return hashStep;
    }

    //Resize the hash array
    private void resize(int newSize) {
        HashEntry[] oldArr = hashArr; //creating an array of the same characteristics as the main array 
        int oldCap = oldArr.length; //get the capacity of the old array

        //Create new hash array
        int actualSize = nextPrime(newSize); //round to the next prime number
        hashArr = new HashEntry[actualSize];
        count = 0;

        //Initialise the new Array
        for (int i = 0; i < actualSize; i++) {
            hashArr[i] = new HashEntry();
        }

        //Insert all entries into new array
        boolean oldLoggingMode = loggingMode;
        loggingMode = false; //temporaily disable logging 

        for (int i = 0; i < oldArr.length; i++) {
            if (oldArr[i].getState() == 1) {
                insert(oldArr[i].getPatient());
            }
        }

        //reactivate logging 
        loggingMode = oldLoggingMode;
        resizeCount++;

        System.out.println("\nResize hash from " + oldCap + " to " + actualSize);
    }

    //Find the next prime number 
    private int nextPrime(int startVal) {
        if(startVal < 2) {
            startVal = 2;
        }
        int primeVal;

        if (startVal % 2 == 0) {
            primeVal = startVal - 1;
        }
        else {
            primeVal = startVal;
        }

        boolean isPrime = false;

        while (!isPrime) {
            primeVal = primeVal + 2;
            int i = 3;
            isPrime = true;
            int rootVal = sqrt(primeVal);

            while (i <= rootVal && isPrime) {
                if (primeVal % i == 0) {
                    isPrime = false;
                }
                else {
                    i = i + 2;
                }
            }
        }

        return primeVal;
    }

    //Manual method to get the squareroot
    private int sqrt(int n) {
        int result = 0;
        if ( n == 0 || n == 1) {
            result = n;
        }
        else {
            int start = 1;
            int end = n;

            while (start <= end) {
                int mid = start + (end - start) / 2;

                if (mid <= n / mid) {
                    start = mid + 1;
                    result = mid;
                }
                else {
                    end = mid - 1;
                }
            }
        }

        return result;
    }

    //Getters for statistical values
    public double getLoadFactor() {
        return (double) count / hashArr.length;
    }

    public int getCount() {
        return count;
    }

    public int getCapacity() {
        return hashArr.length;
    }

    public int getCollisionCount() {
        return collisionCount;
    }

    public int getProbeCount() {
        return probeCount;
    }

    public int getResizeCount() {
        return resizeCount;
    }

    //Return the patients in the hash table
    public Patient[] getPatientsList() {
        Patient[] patients = new Patient[count];
        int index = 0;
        for (int i = 0; i < hashArr.length; i++) {
            if (hashArr[i].getState() == 1) {
                patients[index++] = hashArr[i].getPatient();
            }
        }
        return patients;
    }

    //Display (True = display data and performance, False = performance only)
    public void display(boolean controlSwitch) {
        System.out.println("\n**Hash Table Contents**\n");
        System.out.println("Capacity: " + hashArr.length);
        System.out.println("Count: " + count);
        System.out.println("Load Factor: " + String.format("%.2f", getLoadFactor()));
        System.out.println("Collisions: " + collisionCount);
        System.out.println("Probes: " + probeCount);
        System.out.println("Resizes: " + resizeCount);

        //Show the data in the hash
        if (controlSwitch) {
            System.out.println("|-----------------------------------|");
            for (int i = 0; i < hashArr.length; i++) {
                if (hashArr[i].getState() == 1) {
                    System.out.println("[" + i + "] " + hashArr[i].getPatient());
                }
            }
        }
    }

    //Show all collisions
    public void displayCollisions() {
        System.out.println("\n**Collision Display**\n");

        for (int i = 0; i < hashArr.length; i++) {
            if (hashArr[i].getState() == 1) {
                String patientID = hashArr[i].getPatientID();
                int primaryHash = hash(patientID); //Calculate the location where the patient was supposed to be
                System.out.println("[" + i + "] Patient " + patientID); //where the patient is currently

                if (i != primaryHash) {
                    System.out.println("Amount of Collisions: " + primaryHash); //If the patient is not where it supposed to be, print the location it was actually placed.
                }
                System.out.println();
            }
            else if (hashArr[i].getState() == -1) {
                System.out.println("[" + i + "] deleted");
            }
        }
    }
}

