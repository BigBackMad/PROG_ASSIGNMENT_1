import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) {

         Scanner sc = new Scanner(System.in);
         ArrayList<Patient> patientList = new ArrayList<Patient>();


        final int QUIT = 0;
         int userChoice;

         do{
             System.out.println(" 1. Register a new patient.\n" +
                     " 2. Search for a patient using their Patient ID.\n" +
                     " 3. Update an existing patient's details.\n" +
                     " 4. Delete a patient.\n" +
                     " 5. Display all registered patients. \n"+
                     " 0. Exit. \n");
             System.out.print(" Enter your choice--> ");
             userChoice = sc.nextInt();

         } while (userChoice!=QUIT);

        switch (userChoice) {
            case 1:
                // Logic for "Register Patient"
                //  prompt for ID, name, age, etc.
                System.out.println("\n--- Register New Patient ---");

                // 1. Capture Patient ID [1]
                System.out.print("Enter 6-digit Patient ID >> ");
                String id = sc.nextLine();

                // 2. Capture Names [1]
                System.out.print("Enter First Name >> ");
                String firstName = sc.nextLine();

                System.out.print("Enter Last Name >> ");
                String lastName = sc.nextLine();

                // 3. Capture Age [1]
                System.out.print("Enter Age >> ");
                int age = sc.nextInt();
                sc.nextLine();

                // 4. Capture Gender and Condition [1]
                System.out.print("Enter Gender >> ");
                String gender = sc.nextLine();

                System.out.print("Enter Medical Condition >> ");
                String condition = sc.nextLine();

                // 5. Select Category (Enum)
                System.out.println("Select Category: 1. Inpatient, 2. Outpatient, 3. Emergency");
                System.out.print("Enter Choice >> ");
                int catChoice = sc.nextInt();
                sc.nextLine();

                System.out.println("Patient data successfully captured.");

                Patient newPatient = new Patient();

                if (catChoice == 1) {
                    newPatient.setCategory(PatientCategory.INPATIENT);
                } else if (catChoice == 2) {
                    newPatient.setCategory(PatientCategory.OUTPATIENT);
                } else {
                    newPatient.setCategory(PatientCategory.EMERGENCY);
                }

                newPatient.setPatientID(id);
                newPatient.setFirstName(firstName);
                newPatient.setLastName(lastName);
                newPatient.setAge(age);
                newPatient.setGender(gender);
                newPatient.setMedicalCondition(condition);

                patientList.add(newPatient);
                System.out.println("Registration complete. Total patients: " + patientList.size());

                break;

            case 2:
                // Logic for "Search Patient"
                System.out.println("\n--- Search for Patient ---");
                System.out.print("Enter Patient ID to search >> ");
                String searchID = sc.nextLine();

                boolean found = false; // Initialize a flag to track search status [8, 9]

                // Iterate through the list of patients
                for (int i = 0; i < patientList.size(); i++) {
                    // Use the getter and .equals() to compare strings
                    if (patientList.get(i).getPatientID().equals(searchID)) {
                        System.out.println("Patient Record Found:");
                        patientList.get(i).displayDetails(); // Call the display method
                        found = true;
                        break; // Stop searching once the match is found
                    }
                }

                // After the loop, check if the patient was found [4, 5]
                if (!found) {
                    System.out.println("Error: Patient with ID " + searchID + " not found.");
                }
                break;

            case 3:
                System.out.println("\n--- Update Patient Details ---");
                System.out.print("Enter Patient ID to update >> ");
                String updateID = sc.nextLine();

                int index = -1;
// Step 1: Find the patient index
                for (int i = 0; i < patientList.size(); i++) {
                    if (patientList.get(i).getPatientID().equals(updateID)) {
                        index = i;
                        break;
                    }
                }

// Step 2: Perform the update if found
                if (index != -1) {
                    Patient foundPatient = patientList.get(index);
                    boolean updating = true;

                    while (updating) {
                        System.out.println("\nWhat would you like to update?");
                        System.out.println("1. First Name");
                        System.out.println("2. Last Name");
                        System.out.println("3. Age");
                        System.out.println("4. Medical Condition");
                        System.out.println("5. Category (Inpatient / Outpatient / Emergency)");
                        System.out.println("6. Done updating");
                        System.out.print(">> ");
                        String choice = sc.nextLine();

                        switch (choice) {
                            case "1":
                                System.out.print("Enter NEW First Name >> ");
                                foundPatient.setFirstName(sc.nextLine());
                                System.out.println("First name updated.");
                                break;

                            case "2":
                                System.out.print("Enter NEW Last Name >> ");
                                foundPatient.setLastName(sc.nextLine());
                                System.out.println("Last name updated.");
                                break;

                            case "3":
                                System.out.print("Enter NEW Age >> ");
                                String ageInput = sc.nextLine();
                                if (ageInput.matches("\\d+")) {
                                    foundPatient.setAge(Integer.parseInt(ageInput));
                                    System.out.println("Age updated.");
                                } else {
                                    System.out.println("Invalid age, must be a number.");
                                }
                                break;

                            case "4":
                                System.out.print("Enter NEW Medical Condition >> ");
                                foundPatient.setMedicalCondition(sc.nextLine());
                                System.out.println("Medical condition updated.");
                                break;

                            case "5":
                                System.out.println("1. Inpatient");
                                System.out.println("2. Outpatient");
                                System.out.println("3. Emergency");
                                System.out.print("Choose category >> ");
                                catChoice = sc.nextInt();

                                if (catChoice == 1) {
                                    foundPatient.setCategory(PatientCategory.INPATIENT);
                                    System.out.println("Category updated.");
                                } else if (catChoice == 2) {
                                    foundPatient.setCategory(PatientCategory.OUTPATIENT);
                                    System.out.println("Category updated.");
                                } else {
                                    foundPatient.setCategory(PatientCategory.EMERGENCY);
                                    System.out.println("Category updated.");
                                }

                                break;

                            case "6":
                                updating = false;
                                break;

                            default:
                                System.out.println("Invalid choice, try again.");
                        }
                    }

                    System.out.println("Patient record updated successfully.");

                } else {
                    // Step 3: Handle the "not found" scenario
                    System.out.println("Error: Patient ID " + updateID + " not found.");
                }
                break;

            case 4:
                // Logic for "Delete Patient"
                System.out.println("\n--- Delete Patient Record ---");

                // Step 1: Check if the list is empty before searching [4]
                if (patientList.isEmpty()) {
                    System.out.println("Error: The patient database is currently empty.");
                } else {
                    System.out.print("Enter Patient ID to delete >> ");
                    String deleteID = sc.nextLine();
                    index = -1;

                    // Step 2: Search for the patient's index [5, 6]
                    for (int i = 0; i < patientList.size(); i++) {
                        if (patientList.get(i).getPatientID().equals(deleteID)) {
                            index = i;
                            break;
                        }
                    }

                    // Step 3: Handle the results of the search
                    if (index != -1) {
                        // Retrieve the patient to check their category or display a confirmation
                        Patient removedPatient = patientList.get(index);

                        // Step 4: Perform the removal [7, 8]
                        patientList.remove(index);
                        System.out.println("Patient ID " + deleteID + " has been successfully deleted.");

            /*
               CRITICAL FOR FEATURE 2:
               If your bed array is separate, you must also check if
               this patient was assigned to a bed and set that bed to null.
            */
                    } else {
                        System.out.println("Error: Patient with ID " + deleteID + " not found.");
                    }
                }
                break;

            case 5:
                System.out.println("\n--- Registered Patient Report ---");

                // 1. Check if the database is empty using the isEmpty() method [3]
                if (patientList.isEmpty()) {
                    System.out.println("No patients are currently registered in the system.");
                } else {
                    // 2. Display the total number of patients as required for Feature 3 [2]
                    System.out.println("Total Registered Patients: " + patientList.size());
                    System.out.println("---------------------------------");

                    // 3. Iterate through the list using a loop [4, 5]
                    for (int i = 0; i < patientList.size(); i++) {
                        // 4. Retrieve each patient and call their specific display method
                        patientList.get(i).displayDetails();
                        System.out.println("---------------------------------");
                    }
                }

                break;

            case 6:
                // Logic for "Exit"
                System.out.println("Exiting the system. Goodbye!");
                break;

            default:
                // Executes if the user enters a number other than 1-6 [2, 4]
                System.out.println("Invalid selection. Please try again.");
        }




















    }
}