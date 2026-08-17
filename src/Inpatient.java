public class Inpatient extends Patient {

    private int wardNumber;
    private String bedNumber;

    public Inpatient(String patientID, String firstName, String lastName, int age,
                     String gender, String medicalCondition, PatientCategory category) {
        super(patientID, firstName, lastName, age, gender, medicalCondition, category);
        this.wardNumber = 0;      // not yet assigned to a ward
        this.bedNumber = "";      // not yet assigned to a bed
    }

    public void setWardNumber(int wardNumber) {
        this.wardNumber = wardNumber;
    }

    public int getWardNumber() {
        return wardNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    @Override
    public void displayDetails() {
        super.displayDetails(); // prints all the normal Patient info first
        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: " + bedNumber);
    }

}


