public class ModuleTwoTest {
    public static void main (String[] args) {
        DSAHashTable patientTable = new DSAHashTable(30); //create the hash table
        patientTable.setLoggingMode(true); //enable debugging logs

        //Insert 20 patients
        Patient[] patients = {
            //Urgency 5
            new Patient("P001", "A", 20, "Emergency", 5, "In Treatment", 10.0),
            new Patient("P002", "B", 21, "Emergency", 5, "In Treatment", 10.0),
            new Patient("P003", "C", 22, "Emergency", 5, "In Treatment", 10.0),
            new Patient("P004", "D", 23, "Emergency", 5, "In Treatment", 10.0),

            //Urgency 4
            new Patient("P005", "E", 20, "ICU", 4, "In Treatment", 10.0),
            new Patient("P006", "F", 21, "ICU", 4, "In Treatment", 10.0),
            new Patient("P007", "G", 22, "OT", 4, "Preparing of Surgery", 10.0),
            new Patient("P008", "H", 23, "Ward", 4, "Resting", 10.0),

            //Urgency 3
            new Patient("P009", "I", 30, "Radiology", 3, "Waiting", 10.0),
            new Patient("P010", "J", 31, "General", 3, "Check Up", 10.0),
            new Patient("P011", "K", 32, "Wards", 3, "Resting", 10.0),
            new Patient("P012", "L", 33, "General", 3, "In Treatment", 10.0),

            //Urgency 2
            new Patient("P013", "M", 20, "Ward", 2, "Resting", 10.0),
            new Patient("P014", "N", 77, "Ward", 2, "Resting", 10.0),
            new Patient("P015", "O", 22, "Ward", 2, "Resting", 10.0),
            new Patient("P016", "P", 100, "Ward", 2, "Resting", 10.0),

            //Urgency 1
            new Patient("P017", "Q", 20, "Ward", 1, "Resting", 10.0),
            new Patient("P018", "R", 149, "Ward", 1, "Resting", 10.0),
            new Patient("P019", "S", 0, "Ward", 1, "Resting", 10.0),
            new Patient("P020", "T", 23, "Ward", 1, "Resting", 10.0),
        };

        System.out.println ("Inserting: " + patients.length + " patients.\n");

        for (int i = 0; i < patients.length; i++) {
            try {
                boolean isInsert = patientTable.insert(patients[i]);
                if (!isInsert) {
                    System.out.println("Updated existing patient information instead of inserting new");
                }
            }
            catch (Exception e) {
                System.out.println("ERROR inserting patient: " + e.getMessage());
            }
        }

        System.out.println("\n----------------------------------------------------------------------");

        patientTable.display(true);

        System.out.println("\n----------------------------------------------------------------------");

        System.out.println("\nCollision Mapping\n");
        patientTable.displayCollisions();

        System.out.println("\n----------------------------------------------------------------------");

        //Testing Searching
        System.out.println("\nSuccessful Searches Testing: ");
        String[] searchSuc = {"P001", "P005", "P011", "P020"};
        
        for (String id : searchSuc) {
            Patient found = patientTable.search(id);
            if(found != null) {
                System.out.println("    " + found);
            }
            else {
                System.out.println("    ERROR: Expected to find patient " + id);
            }
        }

        System.out.println("\nUnsuccessful Searches Testing: ");
        String[] searchFail = {"P021", "P025", "P999", ""};
        
        for (String id : searchFail) {
            try {
                Patient found = patientTable.search(id);
                if(found != null) {
                    System.out.println("    Patient " + id + " not found (Supposed to happen)");
                }
                else {
                    System.out.println("    ERROR: Unexpected " + id);
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println ("Error: " + e.getMessage());
            }
        }

        System.out.println("\n----------------------------------------------------------------------");

        System.out.println("\nTesting Deletion");

        String[] toDelete = {"P005", "P010", "P015"};

        for (String id : toDelete) {
            //Initial Search
            Patient before = patientTable.search(id);
            System.out.println("\nBefore deleting: " + id + ": " + (before != null ? "EXISTS" : "NOT FOUND"));

            //Delete
            boolean deleted = patientTable.delete(id);
            System.out.println("\nDeleting: " + id + ": " + (deleted ? "SUCCESS" : "FAIL"));

            //Initial Search
            Patient after = patientTable.search(id);
            System.out.println("\nAfter deleting: " + id + ": " + (after != null ? "EXISTS" : "NOT FOUND"));
        }

        System.out.println("\n----------------------------------------------------------------------");

        System.out.println("\nTesting Duplicates/Collisions: Adding another P001");
        Patient duplicate = new Patient("P001", "Duplicate", 50, "Random", 3, "Waiting", 10.0);
        boolean isDuplicate = patientTable.insert(duplicate);

        if (!isDuplicate) {
            System.out.println("Duplicate detected and record UPDATED");
        }
        else {
            System.out.println("ERROR: Inserted in new slot");
        }

        //Verify 
        Patient updated = patientTable.search("P001");
        if (updated != null) {
            System.out.println("Current record: " + updated);
        }

        System.out.println("\n----------------------------------------------------------------------");
        System.out.println("\nTesting Invalid Urgency Level (Level 6)");
        try {
            Patient invalid1 = new Patient("P999", "Test", 30, "Test Dept", 6, "Testing", 10.0);
            System.out.println("Error should have been thrown for incorrect Urgency Level");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Correctly threw an error: " + e.getMessage());
        }

        System.out.println("\nTesting Invalid Name");
        try {
            Patient invalid2 = new Patient("P999", "", 30, "Test Dept", 5, "Testing", 10.0);
            System.out.println("Error should have been thrown for incorrect name");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Correctly threw an error: " + e.getMessage());
        }

        System.out.println("\nTesting Invalid age");
        try {
            Patient invalid3 = new Patient("P999", "Test", 200, "Test Dept", 5, "Testing", 10.0);
            System.out.println("Error should have been thrown for incorrect age");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Correctly threw an error: " + e.getMessage());
        }

        System.out.println("\nTesting Invalid ID");
        try {
            Patient invalid4 = new Patient("", "Test", 30, "Test Dept", 5, "Testing", 10.0);
            System.out.println("Error should have been thrown for incorrect ID");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Correctly threw an error: " + e.getMessage());
        }

        System.out.println("\n----------------------------------------------------------------------");
        System.out.println("\nAnalysing 100 Search Operations Time Complexity");
        long totalSearchTime = 0;
        int searchIter = 100;

        for (int i = 0; i < searchIter; i++) {
            String searchID = "P" + String.format("%03d", (i % 20) + 1);
            long start = System.nanoTime();
            Patient p = patientTable.search(searchID);
            long end = System.nanoTime();
            totalSearchTime += (end - start);
        }

        System.out.println("\n----------------------------------------------------------------------");
        System.out.println("Average Search time: " + (totalSearchTime / searchIter) + " nanoseconds");

        System.out.println("\nAnalysing 50 Insertion Operations Time Complexity");
        long totalInsertTime = 0;
        int insertIter = 50;

        for (int i = 0; i < insertIter; i++) {
            String newID = "P" + String.format("%03d", 100 + i);
            Patient newPatient = new Patient(newID, "New Patient " + i, 40, "General", 1, "Waiting", 10.0);

            long start = System.nanoTime();
            patientTable.insert(newPatient);
            long end = System.nanoTime();
            totalInsertTime += (end - start);
        }

        System.out.println("Average Insertion time: " + (totalInsertTime / insertIter) + " nanoseconds");

        System.out.println("\n----------------------------------------------------------------------");
        System.out.println("\nAnalysing 50 Deletion Operations Time Complexity");
        long totalDeleteTime = 0;
        int deleteIter = 50;

        for (int i = 0; i < deleteIter; i++) {
            String newDelete = "P" + String.format("%03d", 100 + i);
            long start = System.nanoTime();
            patientTable.delete(newDelete);
            long end = System.nanoTime();
            totalInsertTime += (end - start);
        }

        System.out.println("Average deletion time: " + (totalDeleteTime / deleteIter) + " nanoseconds");

        System.out.println("\n----------------------------------------------------------------------");
        patientTable.display(false);

        System.out.println("\n...All testing completed...");
    }
}
