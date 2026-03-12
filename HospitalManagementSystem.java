import java.util.InputMismatchException;
import java.util.Scanner;

public class HospitalManagementSystem {
    private DSAGraph hospitalLayout;
    private DSAHashTable patientRecords;
    private DSAHeap emergencyQueue;

    public static Scanner sc = new Scanner(System.in);

    public HospitalManagementSystem() {
        hospitalLayout = new DSAGraph();
        patientRecords = new DSAHashTable(30);
        emergencyQueue = new DSAHeap(50);

        patientRecords.setLoggingMode(false); //Turn off debugging
    }

    public static void main(String[] args) {
        HospitalManagementSystem system = new HospitalManagementSystem();

        System.out.println("\n----------------------------------------------------------------------");
        System.out.println("HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("----------------------------------------------------------------------\n");

        system.runDemo();
    }

    private void runDemo() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = sc.nextInt();

            switch (choice) {
                case 1: 
                    setupSampleData();
                    break;
                case 2:
                    demonstrateModule1();
                    break;
                case 3:
                    demonstrateModule2();
                    break;
                case 4: 
                    demonstrateModule3();
                    break;
                case 5: 
                    demonstrateModule4();
                    break;
                case 0:
                    running = false;
                    System.out.println("\nClosing Program...");
                    break;
                default:
                    System.out.println("Invalid Choice!");      
            }
        }
    }

    private void displayMenu() {
        System.out.println("1. Initialise Hospital with data");
        System.out.println("2. Edit Hospital Layout");
        System.out.println("3. Manage Patient Database");
        System.out.println("4. Manage Emergency Queue");
        System.out.println("5. Sort Patient Data");
        System.out.println("0. Exit");   
    }

    //Set up sample data
    private void setupSampleData() {
        System.out.println("\nLoading default values...\n");
        System.out.println("\n----------------------------------------------------------------------");
        try {
            //Add 8 departments 
            hospitalLayout.addVertex("Emergency", "Emergency Department");
            hospitalLayout.addVertex("ICU", "Intensive Care Unit");
            hospitalLayout.addVertex("Pharmacy", "Pharmacy Department");
            hospitalLayout.addVertex("Radiology", "Radiology Department");
            hospitalLayout.addVertex("Laboratory", "Laboratory Department");
            hospitalLayout.addVertex("OT", "Operating Theatres");
            hospitalLayout.addVertex("Wards", "Patient Wards");
            hospitalLayout.addVertex("Outpatient", "Outpatient Department");

            //Add corridors 
            hospitalLayout.addEdge("Emergency", "ICU", 3.5);
            hospitalLayout.addEdge("Emergency", "Radiology", 5.0);
            hospitalLayout.addEdge("ICU", "Pharmacy", 2.5);
            hospitalLayout.addEdge("ICU", "OT", 4.0);
            hospitalLayout.addEdge("Pharmacy", "Emergency", 3.0);
            hospitalLayout.addEdge("Pharmacy", "Laboratory", 2.0);
            hospitalLayout.addEdge("Radiology", "Laboratory", 4.5);
            hospitalLayout.addEdge("Laboratory", "OT", 3.0);
            hospitalLayout.addEdge("OT", "Wards", 2.5);
            hospitalLayout.addEdge("Wards", "Outpatient", 3.5);
            hospitalLayout.addEdge("Radiology", "Outpatient", 6.0);
            hospitalLayout.addEdge("Laboratory", "Wards", 3.5);

            System.out.println("Number of Departments added: " + hospitalLayout.getVertexCount());
            System.out.println("Number of Corridors added: " + hospitalLayout.getEdgeCount() + "\n");
            //Displaying floorplan as list
            System.out.println("-----Hospital Floor Plan-----");
            hospitalLayout.displayAsList();
            System.out.println("\n----------------------------------------------------------------------\n");

            
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

            for (int i = 0; i < patients.length; i++) {
                patientRecords.insert(patients[i]);

                //Add patients to the emergency queue
                emergencyQueue.insertPatient(patients[i]);
            }

            System.out.println("Number of Patients added: " + patientRecords.getCount());
            patientRecords.display(true);
            System.out.println("\n----------------------------------------------------------------------\n");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    //Module 1 menu
    public void demonstrateModule1() {
        int subChoice;

        try {
            do {
                System.out.println("\n----------------------------------------------------------------------");
                System.out.println("EDIT HOSPITAL LAYOUT:");
                System.out.println("----------------------------------------------------------------------\n");
                System.out.println("1. Add Department");
                System.out.println("2. Add Corridor");
                System.out.println("3. Remove Department");
                System.out.println("4. Remove Corridor");
                System.out.println("5. Preform BFS");
                System.out.println("6. Preform DFS");
                System.out.println("7. Find Shortest Path to a Department");
                System.out.println("8. Display");
                System.out.println("0. Return to Main Menu");
                System.out.println("----------------------------------------------------------------------\n");

                subChoice = sc.nextInt();
                sc.nextLine();
                switch (subChoice) {
                    case 1: 
                        System.out.println("Enter the name of the Department: ");
                        String department = sc.nextLine();
                        hospitalLayout.addVertex(department, department);
                        break;
                    case 2:
                        System.out.println("Enter the 'From' Department: ");
                        String fromDept = sc.nextLine();
                        System.out.println("Enter the 'To' Department: ");
                        String toDept = sc.nextLine();
                        System.out.println("Enter the distance: ");
                        double distance = sc.nextDouble();
                        hospitalLayout.addEdge(fromDept, toDept, distance);
                        break;
                    case 3:
                        System.out.println("Enter the name of the Department to remove: ");
                        String removeDept = sc.nextLine();
                        hospitalLayout.removeVertex(removeDept);
                        break;
                    case 4: 
                        System.out.println("Enter the 'From' Department: ");
                        String fromRemove = sc.nextLine();
                        System.out.println("Enter the 'To' Department: ");
                        String toRemove = sc.nextLine();
                        hospitalLayout.removeEdge(fromRemove, toRemove);
                        break;
                    case 5: 
                        hospitalLayout.bfs();
                        break;
                    case 6: 
                        hospitalLayout.dfs();
                        break;
                    case 7:
                        System.out.println("Enter the 'From' Department: ");
                        String searchFrom = sc.nextLine();
                        System.out.println("Enter the 'To' Department: ");
                        String searchTo = sc.nextLine();
                        hospitalLayout.dijkstras(searchFrom, searchTo);
                        break;
                    case 8:
                        hospitalLayout.displayAsList();
                        break;
                    case 0:
                        System.out.println("\nReturning to Menu...");
                        break;
                    default:
                        System.out.println("Invalid Choice!");      
                }
            } while (subChoice != 0);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException ey) {
            System.out.println("Error: Input of wrong type entered");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    //Module 2 menu
    public void demonstrateModule2() {
        int subChoice;

        try {
            do {
                System.out.println("\n----------------------------------------------------------------------");
                System.out.println("PATIENT DATABASE MANAGEMENT:");
                System.out.println("----------------------------------------------------------------------\n");
                System.out.println("1. Add Patient");
                System.out.println("2. Remove Patient");
                System.out.println("3. Search for Patient");
                System.out.println("4. Display Database");
                System.out.println("5. Display Collisions");
                System.out.println("0. Return to Main Menu");
                System.out.println("----------------------------------------------------------------------\n");

                subChoice = sc.nextInt();
                sc.nextLine();
                switch (subChoice) {
                    case 1: 
                        System.out.println("Enter the patient ID: ");
                        String patientID = sc.nextLine();
                        System.out.println("Enter the patient name: ");
                        String name = sc.nextLine();
                        System.out.println("Enter the patient age: ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter the department the patient is admitted to: ");
                        String department = sc.nextLine();
                        System.out.println("Enter the patient's urgency level: ");
                        int urgency = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter the patient's status: ");
                        String status = sc.nextLine();
                        System.out.println("Enter the expected treatment time: ");
                        double time = sc.nextDouble();
                        sc.nextLine();
                        Patient patient = new Patient(patientID, name, age, department, urgency, status, time);
                        patientRecords.insert(patient);
                        break;
                    case 2:
                        System.out.println("Enter the patient ID of the Patient to delete: ");
                        String delID = sc.nextLine();
                        boolean deleted = patientRecords.delete(delID);
                        System.out.println("\nDeleting: " + delID + ": " + (deleted ? "SUCCESS" : "FAIL"));
                        break;
                    case 3:
                        System.out.println("Enter the patient ID of the Patient to search: ");
                        String searchID = sc.nextLine();
                        Patient found = patientRecords.search(searchID);
                        if(found != null) {
                            System.out.println("\nFound: " + found);
                            System.out.println("\nWould you like to Edit? ");
                            System.out.println("1. Name");
                            System.out.println("2. Age");
                            System.out.println("3. Department");
                            System.out.println("4. Urgency");
                            System.out.println("5. Status");
                            System.out.println("6. Treatment Time");
                            System.out.println("0. No update");

                            int updateChoice = sc.nextInt();
                            sc.nextLine();

                            switch (updateChoice) {
                                case 1: 
                                    System.out.println("Enter the new name: ");
                                    String newName = sc.nextLine();
                                    found.setName(newName);
                                    patientRecords.insert(found);
                                    break;
                                case 2: 
                                    System.out.println("Enter the new age: ");
                                    int newAge = sc.nextInt();
                                    sc.nextLine();
                                    found.setAge(newAge);
                                    patientRecords.insert(found);
                                    break;
                                case 3: 
                                    System.out.println("Enter the new department: ");
                                    String newDept = sc.nextLine();
                                    found.setDepartment(newDept);
                                    patientRecords.insert(found);
                                    break;
                                case 4: 
                                    System.out.println("Enter the new urgency: ");
                                    int newUrgency = sc.nextInt();
                                    sc.nextLine();
                                    found.setUrgency(newUrgency);
                                    patientRecords.insert(found);
                                    break;
                                case 5: 
                                    System.out.println("Enter the new status: ");
                                    String newStatus = sc.nextLine();
                                    found.setTreatmentStatus(newStatus);
                                    patientRecords.insert(found);
                                    break;
                                case 6: 
                                    System.out.println("Enter the new treatment time: ");
                                    double newTime = sc.nextDouble();
                                    sc.nextLine();
                                    found.setTreatmentTime(newTime);
                                    patientRecords.insert(found);
                                    break;
                                case 0: 
                                    System.out.println("No update was made...");
                                    break;
                                default:
                                    System.out.println("Invalid Choice!");
                                
                            }
                        }
                        else {
                            System.out.println("ERROR: Expected to find patient " + searchID);
                        }
                        break;
                    case 4: 
                        patientRecords.display(true);
                        break;
                    case 5: 
                        patientRecords.displayCollisions();
                        break;
                    case 0:
                        System.out.println("\nReturning to Menu...");
                        break;
                    default:
                        System.out.println("Invalid Choice!");      
                }
            } while (subChoice != 0);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException ey) {
            System.out.println("Error: Input of wrong type entered");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }


    }

    //Module 3 menu
    public void demonstrateModule3() {
        int subChoice;

        try {
            do {
                System.out.println("\n----------------------------------------------------------------------");
                System.out.println("EMERGENCY QUEUE:");
                System.out.println("----------------------------------------------------------------------\n");
                System.out.println("1. Download Patient Data from Patient Database");
                System.out.println("2. Add Patient to queue");
                System.out.println("3. Remove Patient");
                System.out.println("4. Get Top Priority");
                System.out.println("5. Display Queue");
                System.out.println("0. Return to Main Menu");
                System.out.println("----------------------------------------------------------------------\n");

                subChoice = sc.nextInt();
                sc.nextLine();
                switch (subChoice) {
                    case 1: 
                        if (patientRecords != null) {
                            Patient[] patients = patientRecords.getPatientsList();
                            for (int i = 0; i < patients.length; i++) {
                                emergencyQueue.insertPatient(patients[i]);
                            }
                        } else {
                            System.out.println("Database is empty!");
                        }
                        break;
                    case 2: 
                        System.out.println("Enter the patient ID: ");
                        String patientID = sc.nextLine();
                        System.out.println("Enter the patient name: ");
                        String name = sc.nextLine();
                        System.out.println("Enter the patient age: ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter the department the patient is admitted to: ");
                        String department = sc.nextLine();
                        System.out.println("Enter the patient's urgency level: ");
                        int urgency = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter the patient's status: ");
                        String status = sc.nextLine();
                        System.out.println("Enter the expected treatment time: ");
                        double time = sc.nextDouble();
                        sc.nextLine();
                        Patient patient = new Patient(patientID, name, age, department, urgency, status, time);
                        patientRecords.insert(patient);
                        emergencyQueue.insertPatient(patient);
                        break;
                    case 3:
                        System.out.println("Removing patient first in queue...");
                        emergencyQueue.removePatient();
                        System.out.println("Removed Patient");
                        break;
                    case 4:
                        int priority = emergencyQueue.extractPriority();
                        System.out.println("The top priority is: " + priority);
                        break;
                    case 5: 
                        emergencyQueue.displayHeap();
                        break;
                    case 0:
                        System.out.println("\nReturning to Menu...");
                        break;
                    default:
                        System.out.println("Invalid Choice!");      
                }
            } while (subChoice != 0);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException ey) {
            System.out.println("Error: Input of wrong type entered");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }


    }

    //Module 4 menu
    public void demonstrateModule4() {
        int subChoice;

        try {
            do {
                System.out.println("\n----------------------------------------------------------------------");
                System.out.println("SORT PATIENT DATA:");
                System.out.println("----------------------------------------------------------------------\n");
                System.out.println("1. Merge Sort Data (Recommended)");
                System.out.println("2. Quick Sort Data");
                System.out.println("0. Return to Main Menu");
                System.out.println("----------------------------------------------------------------------\n");

                subChoice = sc.nextInt();
                sc.nextLine();
                switch (subChoice) {
                    case 1: 
                        if (patientRecords != null) {
                            Patient[] patients = patientRecords.getPatientsList();
                            Patient[] mergeData = DSASorts.copy(patients);
                            mergeData = DSASorts.mergeSort(mergeData);
                            for (int i = 0; i < mergeData.length; i++) {
                                System.out.println(mergeData[i]);
                            }
                        } else {
                            System.out.println("Database is empty!");
                        }
                        break;
                    case 2: 
                        if (patientRecords != null) {
                            Patient[] patients = patientRecords.getPatientsList();
                            Patient[] quickData = DSASorts.copy(patients);
                            quickData = DSASorts.quickSort(quickData);;
                            for (int i = 0; i < quickData.length; i++) {
                                System.out.println(quickData[i]);
                            }

                        } else {
                            System.out.println("Database is empty!");
                        }
                        break;
                    case 0:
                        System.out.println("\nReturning to Menu...");
                        break;
                    default:
                        System.out.println("Invalid Choice!");      
                }
            } while (subChoice != 0);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException ey) {
            System.out.println("Error: Input of wrong type entered");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }


    }
}
