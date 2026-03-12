public class Patient {
    private String name;
    private int age;
    private String patientID; 
    private String department;
    private int urgencyLevel;
    private String treatmentStatus;
    private double treatmentTime; 

    //Constructor for patient
    public Patient(String patientID, String name, int age, String department, int urgencyLevel, String treatmentStatus) {
        //Validate ID
        if (patientID == null || patientID.trim().isEmpty()) {
            throw new IllegalArgumentException("PatientID cannot be empty");
        }
        //Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        //Validate Age
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be 0-150");
        }
        //Validate Department
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        //Validate Urgency Level
        if (urgencyLevel < 1 || urgencyLevel > 5) {
            throw new IllegalArgumentException("Urgency must be between 1-5");
        }
        //Validate Treatment Status
        if (treatmentStatus == null || treatmentStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Treatment Status cannot be empty");
        }

        this.patientID = patientID.trim();
        this.name = name.trim();
        this.age = age;
        this.department = department.trim();
        this.urgencyLevel = urgencyLevel;
        this.treatmentStatus = treatmentStatus.trim();
        this.treatmentTime = 0.0; //Incase the treatment time is known or not required.
    }

    //Constructor for patient with treatment time
    public Patient(String patientID, String name, int age, String department, int urgencyLevel, String treatmentStatus, double treatmentTime) {
        //Validate ID
        if (patientID == null || patientID.trim().isEmpty()) {
            throw new IllegalArgumentException("PatientID cannot be empty");
        }
        //Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        //Validate Age
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be 0-150");
        }
        //Validate Department
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        //Validate Urgency Level
        if (urgencyLevel < 1 || urgencyLevel > 5) {
            throw new IllegalArgumentException("Urgency must be between 1-5");
        }
        //Validate Treatment Status
        if (treatmentStatus == null || treatmentStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Treatment Status cannot be empty");
        }

        if (treatmentTime <= 0) {
            throw new IllegalArgumentException("Treatment time cannot be less than 0");
        }

        this.patientID = patientID.trim();
        this.name = name.trim();
        this.age = age;
        this.department = department.trim();
        this.urgencyLevel = urgencyLevel;
        this.treatmentStatus = treatmentStatus.trim();
        this.treatmentTime = treatmentTime;
    }

    //Getters for the class
    public String getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public String getTreatmentStatus() {
        return treatmentStatus;
    }

    public double getTreatmentTime() {
        return treatmentTime;
    }

    //Setters
    public void setPatientID(String patientID) {
        //Validate ID
        if (patientID == null || patientID.trim().isEmpty()) {
            throw new IllegalArgumentException("PatientID cannot be empty");
        }
        this.patientID = patientID.trim();
    }

    public void setName(String name) {
        //Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setAge(int age) {
        //Validate Age
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be 0-150");
        }
        this.age = age;
    }

    public void setDepartment(String department) {
        //Validate Department
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        this.department = department.trim();
    }

    public void setUrgency(int urgency) {
        //Validate Urgency Level
        if (urgencyLevel < 1 || urgencyLevel > 5) {
            throw new IllegalArgumentException("Urgency must be between 1-5");
        }
        this.urgencyLevel = urgency;
    }

    public void setTreatmentStatus(String treatmentStatus) {
        //Validate Treatment Status
        if (treatmentStatus == null || treatmentStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Treatment Status cannot be empty");
        }
        this.treatmentStatus = treatmentStatus.trim();
    }

    public void setTreatmentTime(double treatmentTime) {
        //Validating treatment Time
        if (treatmentTime <= 0) {
            throw new IllegalArgumentException("Treatment time cannot be less than 0");
        }
        this.treatmentTime = treatmentTime;
    }

    //Calculate the priority of a patient using the formula: Priority = (6 - U) + 1000/T
    public int calcPriority() {
        //Validating treatment Time
        if (treatmentTime <= 0) {
            throw new IllegalArgumentException("Treatment time must be greater than 0 to get priority");
        }
        int urgencyComp = 6 - urgencyLevel;
        int timeComp = (int)(1000.0 / treatmentTime);
        return urgencyComp + timeComp;
    }

    //toString to display multiple patients 
    public String toString() {
        String result = "PatientID: " + patientID +" [Name: " + name + ", Age: " + age + ", Department: " + department + ", Urgency: " + urgencyLevel + ", Status: " + treatmentStatus;
        if (treatmentTime >= 0) {
            result += ", Time: " + treatmentTime + " mins, Priority: " + calcPriority();
        }
        result += "]";

        return result;
    }

    //formatted toString for individual patient printing
    public String toFormattedString() {
        String result = "";
        result = result + "Patient ID: " + patientID + "\n";
        result = result + "Patient Name: " + name + "\n";
        result = result + "Patient Age: " + age + "\n";
        result = result + "Department: " + department + "\n";
        result = result + "Patient Urgency Level: " + urgencyLevel + "\n";
        result = result + "Patient Treatment Status: " + treatmentStatus;
        if (treatmentTime >= 0) {
            result += "\nTreatment Time: " + treatmentTime + " mins";
            result += "\nPriority: " + calcPriority();
        }
        return result;
    }
}
