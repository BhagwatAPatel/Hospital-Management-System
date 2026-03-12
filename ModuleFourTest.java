public class ModuleFourTest {

    /*
     * Manual Random Value Generator
     * 
     * Purpose: To generate random values for creating multiple patient
     * 
     * Reference: Knuth (1997), The Art of Computer Programming, Vol. 2
     * 
     * Implementation: Used the reference to understand the algorithm and implement it without built-in functions and adapting to given scenario.
     */
    
    private static final int[] DATA_SIZES = {100, 500, 1000}; //data sizes for the test

    private static final long RANDOM_SEED = 12345; //for reproducibility of random value generation

    public static void main(String[] args) {
        System.out.println("Testing merge sort and quick sort on patient data");

        //Check the data is actually being sorted
        testAccuracy();

        // Record efficiency of algorithms
        runBenchmarks();
        
    }

    private static void testAccuracy() {
        System.out.println("\n----------------------------------------------------------------------");
        System.out.println("Testing Sorting Accuracy:");
        System.out.println("\n----------------------------------------------------------------------");

        Patient[] testPatients = generateRandom(10, 1000);

        System.out.println("Original Data:");
        partialPrint(testPatients, 10);

        Patient[] mergeData = DSASorts.copy(testPatients);
        DSASorts.mergeSort(mergeData);
        System.out.println("Merge Sorted Data:");
        partialPrint(mergeData, 10);

        Patient[] quickData = DSASorts.copy(testPatients);
        DSASorts.quickSort(quickData);
        System.out.println("Quick Sorted Data:");
        partialPrint(quickData, 10);
        System.out.println("\n----------------------------------------------------------------------");
        
    }

    private static void runBenchmarks() {
        System.out.println("\n----------------------------------------------------------------------");
        System.out.println("Benchmarks");
        System.out.println("\n----------------------------------------------------------------------");

        System.out.println("Algorithm  | Size | Condition      | Time (ms)  | Sorted");
        System.out.println("-----------|------|----------------|------------|-------");

        //Test each data size
        for (int size : DATA_SIZES) {
            //Random data
            Patient[] randomData = generateRandom(size, RANDOM_SEED);
            testBoth(randomData, size, "Random");

            //Nearly sorted data
            Patient[] nearlySorted = generateNearly(size, RANDOM_SEED);
            testBoth(nearlySorted, size, "Nearly Sorted");

            //Reversed data
            Patient[] reversed = generateReverse(size, RANDOM_SEED);
            testBoth(reversed, size, "Reversed");

            System.out.println("-----------|------|----------------|------------|-------");
        }
    }

    //Test both merge sort and quick sort
    private static void testBoth(Patient[] patients, int size, String condition) {
        //Test Merge
        Patient[] mergeData = DSASorts.copy(patients);
        long start = System.nanoTime(); //Start timer
        DSASorts.mergeSort(mergeData);
        long end = System.nanoTime(); //End timer
        double mergeTime = (end - start) / 1000000.0; //convert to ms
        System.out.printf("Merge Sort | %4d | %-14s | %10.3f | Sorted\n", size, condition, mergeTime);

        //Test Quick
        Patient[] quickData = DSASorts.copy(patients);
        start = System.nanoTime(); //Start timer
        DSASorts.quickSort(quickData);
        end = System.nanoTime(); //End timer
        double quickTime = (end - start) / 1000000.0; //convert to ms
        System.out.printf("Quick Sort | %4d | %-14s | %10.3f | Sorted\n", size, condition, quickTime);
    }

    //Generate random patients
    private static Patient[] generateRandom(int size, long seed) {
        Patient[] patients = new Patient[size];
        SimpleRandom rand = new SimpleRandom(seed);

        for (int i = 0; i < size; i++) {
            String id = "P" + String.format("%04d", i + 1);
            String name = "Patient" + (i + 1);
            int age = 20 + rand.nextInt(60);
            String dept = getDepartment(rand.nextInt(5));
            int urgency = 1 + rand.nextInt(5);
            double treatmentTime = 5.0 + rand.nextDouble() * 115.0;

            patients[i] = new Patient(id, name, age, dept, urgency, "Waiting", treatmentTime);
        }

        return patients;
    }

    //Generate Nearly Sorted Patients
    private static Patient[] generateNearly(int size, long seed) {
        Patient[] patients = generateRandom(size, seed);
        DSASorts.mergeSort(patients);

        SimpleRandom rand = new SimpleRandom(seed + 1);
        int displacement = size / 10;

        for (int i = 0; i < displacement; i++) {
            int index1 = rand.nextInt(size);
            int index2 = rand.nextInt(size);

            Patient temp = patients[index1];
            patients[index1] = patients[index2];
            patients[index2] = temp;
        }

        return patients; 
    }

    //Generate reversed 
    private static Patient[] generateReverse(int size, long seed) {
        Patient[] patients = generateRandom(size, seed);
        DSASorts.mergeSort(patients);

        for (int i = 0; i < size / 2; i++) {
            Patient temp = patients[i];
            patients[i] = patients[size - 1 - i];
            patients[size - 1 - i] = temp;
        }

        return patients;
    }

    //Get department
    private static String getDepartment(int index) {
        String[] department = {"Emergency", "ICU", "Surgery", "General", "Pediatrics"};
        return department[index % department.length];
    }

    //Print part of the array
    private static void partialPrint(Patient[] patient, int n) {
        int limit = (n < patient.length) ? n : patient.length;

        for (int i = 0; i < limit; i++) {
            System.out.printf("  [%2d] Time: %6.2f mins - %s%n", i, patient[i].getTreatmentTime(), patient[i].getPatientID());
        }
    }

    //Custom method to generate random values
    private static class SimpleRandom {
        private long seed;
        private static final long MULTIPLIER = 1103515245L;
        private static final long INCREMENT = 12345L;
        private static final long MODULUS = 2147483648L;

        public SimpleRandom(long seed) {
            this.seed = seed;
        }

        public int nextInt(int bound) {
            seed = (MULTIPLIER * seed + INCREMENT) % MODULUS;
            return (int)((seed % bound + bound) % bound);
        }

        public double nextDouble() {
            seed = (MULTIPLIER * seed + INCREMENT) % MODULUS;
            return (double)seed / MODULUS;
        }
    }

}
