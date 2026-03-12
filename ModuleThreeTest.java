public class ModuleThreeTest {
    public static void main(String[] args) {
        DSAHeap patientQueue = new DSAHeap(20);
        
        //Insert 20 patients
        Patient[] patients = {
            new Patient("P001", "A", 20, "Emergency", 4, "In Treatment", 10.0),
            new Patient("P002", "B", 21, "Emergency", 1, "In Treatment", 70.0),
            new Patient("P003", "C", 22, "Emergency", 1, "In Treatment", 50.0),
            new Patient("P004", "D", 23, "Emergency", 4, "In Treatment", 10.0),

            new Patient("P005", "E", 20, "ICU", 4, "In Treatment", 15.0),
            new Patient("P006", "F", 21, "ICU", 3, "In Treatment", 30.0),
            new Patient("P007", "G", 22, "OT", 1, "Preparing of Surgery", 90.0),
            new Patient("P008", "H", 23, "Ward", 5, "Resting", 5.0),

            new Patient("P009", "I", 30, "Radiology", 5, "Waiting", 5.0),
            new Patient("P010", "J", 31, "General", 5, "Check Up", 7.0),
            new Patient("P011", "K", 32, "Wards", 5, "Resting", 9.0),
            new Patient("P012", "L", 33, "General", 2, "In Treatment", 35.0),

            new Patient("P013", "M", 20, "Ward", 2, "Resting", 40.0),
            new Patient("P014", "N", 77, "Ward", 5, "Resting", 5.0),
            new Patient("P015", "O", 22, "Ward", 5, "Resting", 5.0),
            new Patient("P016", "P", 100, "Ward", 5, "Resting", 5.0),

            new Patient("P017", "Q", 20, "Ward", 5, "Resting",5.0 ),
            new Patient("P018", "R", 149, "Ward", 5, "Resting", 5.0),
            new Patient("P019", "S", 0, "Ward", 5, "Resting", 5.0),
            new Patient("P020", "T", 23, "Ward", 5, "Resting", 5.0),
        };

        //iterate through the patients array and add into the heap
        for (int i = 0; i < patients.length; i++) {
            System.out.println("Inserting: " + patients[i]);
            patientQueue.insertPatient(patients[i]);
        }

        //Display the queue 
        patientQueue.displayHeap();

        System.out.println("\n----------------------------------------------------------------------");
        //Get the priority of top patient 
        try {
            int topPriority = patientQueue.extractPriority();
            System.out.println("Top Priority: " + topPriority);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n----------------------------------------------------------------------");

        //Remove a 10 patients 
        for (int i = 0; i <= 10; i++) {
            patientQueue.removePatient();
            System.out.println("Removed Patient [" + i + "]");
        }

        System.out.println("\n----------------------------------------------------------------------");

        //Display updated queue after removing 
        System.out.println("\nAFTER REMOVAL\n");
        patientQueue.displayHeap();

        System.out.println("\n----------------------------------------------------------------------");
        //Get the priority of top patient 
        try {
            int topPriority = patientQueue.extractPriority();
            System.out.println("Top Priority: " + topPriority);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }
}
