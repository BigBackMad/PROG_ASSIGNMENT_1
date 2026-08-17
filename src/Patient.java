public class Patient {

    private String patientID;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory category;

    // Empty constructor - still used for Outpatient/Emergency via setters
    public Patient() {
    }

    // Example Getter: Retrieves the Patient ID
    public String getPatientID() {
        return patientID;
    }

    public String  getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Example Setter: Updates the Patient ID
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    // Example Getter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        } else {
            this.age = 0; // Default or error value
        }
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    // Getter and Setter for the enum category
    public PatientCategory getCategory() {
        return category;
    }

    public void setCategory(PatientCategory category) {
        this.category = category;
    }

    // Parameterized constructor - called by Inpatient via super()
    public Patient(String patientID, String firstName, String lastName, int age,
                   String gender, String medicalCondition, PatientCategory category) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    public void displayDetails() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Medical Condition: " + medicalCondition);
        System.out.println("Category: " + category); // Using the enum value
    }

}

