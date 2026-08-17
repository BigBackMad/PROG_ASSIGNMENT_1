import java.util.Scanner;
import java.util.*;

public class Main {

    private static Inpatient[][] wardBeds = new Inpatient[4][5];

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Patient> patientList = new ArrayList<Patient>();

        final int QUIT = 7;
        int userChoice;

        do {
            System.out.println(" " +
                    " 1. Register a new patient.\n" +
                    " 2. Search for a patient using their Patient ID.\n" +
                    " 3. Update an existing patient's details.\n" +
                    " 4. Delete a patient.\n" +
                    " 5. Display all registered patients.\n" +
                    " 6. Bed Management.\n" +
                    " 7. Reports.\n" +
                    " 8. Exit.");

            System.out.print(" Enter your choice--> ");
            userChoice = sc.nextInt();
            sc.nextLine(); // clear leftover newline so nextLine() calls below work properly

            switch (userChoice) {

                case 1:
                    // Logic for "Register Patient"
                    System.out.println("\n--- Register New Patient ---");

                    // 1. Capture Patient ID
                    System.out.print("Enter 6-digit Patient ID >> ");
                    String id = sc.nextLine();

                    // 2. Capture Names
                    System.out.print("Enter First Name >> ");
                    String firstName = sc.nextLine();

                    System.out.print("Enter Last Name >> ");
                    String lastName = sc.nextLine();

                    // 3. Capture Age
                    System.out.print("Enter Age >> ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    // 4. Capture Gender and Condition
                    System.out.print("Enter Gender >> ");
                    String gender = sc.nextLine();

                    System.out.print("Enter Medical Condition >> ");
                    String condition = sc.nextLine();

                    // 5. Select Category (Enum)
                    System.out.println("Select Category: 1. Inpatient, 2. Outpatient, 3. Emergency");
                    System.out.print("Enter Choice >> ");
                    int catChoice = sc.nextInt();
                    sc.nextLine();

                    // Create as Inpatient if category 1 was chosen, otherwise a plain Patient
                    Patient newPatient;
                    if (catChoice == 1) {
                        newPatient = new Inpatient();
                    } else {
                        newPatient = new Patient();
                    }

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

                    boolean found = false;

                    for (int i = 0; i < patientList.size(); i++) {
                        if (patientList.get(i).getPatientID().equals(searchID)) {
                            System.out.println("Patient Record Found:");
                            patientList.get(i).displayDetails();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Error: Patient with ID " + searchID + " not found.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Update Patient Details ---");
                    System.out.print("Enter Patient ID to update >> ");
                    String updateID = sc.nextLine();

                    int index = -1;
                    for (int i = 0; i < patientList.size(); i++) {
                        if (patientList.get(i).getPatientID().equals(updateID)) {
                            index = i;
                            break;
                        }
                    }

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
                                    sc.nextLine();

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
                        System.out.println("Error: Patient ID " + updateID + " not found.");
                    }
                    break;

                case 4:
                    // Logic for "Delete Patient"
                    System.out.println("\n--- Delete Patient Record ---");

                    if (patientList.isEmpty()) {
                        System.out.println("Error: The patient database is currently empty.");
                    } else {
                        System.out.print("Enter Patient ID to delete >> ");
                        String deleteID = sc.nextLine();
                        index = -1;

                        for (int i = 0; i < patientList.size(); i++) {
                            if (patientList.get(i).getPatientID().equals(deleteID)) {
                                index = i;
                                break;
                            }
                        }

                        if (index != -1) {
                            Patient removedPatient = patientList.get(index);

                            // If this patient is occupying a bed, free it up first
                            for (int row = 0; row < wardBeds.length; row++) {
                                for (int col = 0; col < wardBeds[row].length; col++) {
                                    if (wardBeds[row][col] != null &&
                                            wardBeds[row][col].getPatientID().equals(deleteID)) {
                                        wardBeds[row][col] = null;
                                    }
                                }
                            }

                            patientList.remove(index);
                            System.out.println("Patient ID " + deleteID + " has been successfully deleted.");

                        } else {
                            System.out.println("Error: Patient with ID " + deleteID + " not found.");
                        }
                    }
                    break;

                case 5:
                    System.out.println("\n--- Registered Patient Report ---");

                    if (patientList.isEmpty()) {
                        System.out.println("No patients are currently registered in the system.");
                    } else {
                        System.out.println("Total Registered Patients: " + patientList.size());
                        System.out.println("---------------------------------");

                        for (int i = 0; i < patientList.size(); i++) {
                            patientList.get(i).displayDetails();
                            System.out.println("---------------------------------");
                        }
                    }
                    break;

                case 6:
                    // Bed Management
                    boolean bedMenu = true;

                    while (bedMenu) {
                        System.out.println("\n--- Bed Management ---");
                        System.out.println("1. Allocate a bed");
                        System.out.println("2. Release a bed");
                        System.out.println("3. Display full ward layout");
                        System.out.println("4. Display available beds");
                        System.out.println("5. Display occupied beds");
                        System.out.println("6. Back to main menu");
                        System.out.print(">> ");
                        String bedChoice = sc.nextLine();

                        switch (bedChoice) {
                            case "1":
                                System.out.println("\n--- Allocate Bed ---");
                                System.out.print("Enter Patient ID to allocate a bed >> ");
                                String targetID = sc.nextLine();

                                // Step 1: find the patient in the list
                                Patient foundPatient = null;
                                for (int i = 0; i < patientList.size(); i++) {
                                    if (patientList.get(i).getPatientID().equals(targetID)) {
                                        foundPatient = patientList.get(i);
                                        break;
                                    }
                                }

                                // Step 2: make sure the patient exists and is an Inpatient
                                if (foundPatient == null) {
                                    System.out.println("Error: Patient ID not found.");
                                } else if (foundPatient.getCategory() != PatientCategory.INPATIENT) {
                                    System.out.println("Error: Only Inpatients can be allocated a hospital bed.");
                                } else {

                                    // Step 3: check they don't already have a bed
                                    boolean alreadyHasBed = false;
                                    for (int r = 0; r < 4; r++) { // go through each row (0,1,2,3)
                                        for (int c = 0; c < 5; c++) {
                                            if (wardBeds[r][c] != null && wardBeds[r][c].getPatientID().equals(targetID)) {
                                                alreadyHasBed = true;
                                            }
                                        }
                                    }

                                    if (alreadyHasBed) {
                                        System.out.println("Error: This patient is already assigned to a bed.");
                                    } else {

                                        // Step 4: find the first empty bed and put the patient there
                                        boolean allocated = false;
                                        int bedNum = 0; // tracks which bed number we're currently looking at

                                        for (int r = 0; r < 4 && !allocated; r++) {
                                            for (int c = 0; c < 5 && !allocated; c++) {
                                                bedNum++; // move to the next bed number every time we check a cell

                                                if (wardBeds[r][c] == null) {
                                                    wardBeds[r][c] = (Inpatient) foundPatient;

                                                    String bedCode = String.format("B%02d", bedNum); // e.g. "B07"
                                                    wardBeds[r][c].setWardNumber(1);
                                                    wardBeds[r][c].setBedNumber(bedCode);

                                                    System.out.println("Success: Patient " + targetID + " allocated to Bed " + bedCode);
                                                    allocated = true;

                                                }
                                            }
                                        }

                                        // Step 5: if we never found an empty bed, say so
                                        if (!allocated) {
                                            System.out.println("Error: No beds available. The ward is currently full.");
                                        }
                                    }
                                }
                                break;

                            case "2":
                                System.out.println("\n--- Release a Bed (Discharge) ---");
                                System.out.print("Enter Patient ID to release from bed >> ");
                                String releaseID = sc.nextLine();

                                boolean freed = false;

                                // 1. Iterate through the 4x5 ward layout
                                for (int r = 0; r < 4; r++) { // 4 rows
                                    for (int c = 0; c < 5; c++) { // 5 columns
                                        // 2. Check if the bed is occupied (not null) AND if the ID matches
                                        if (wardBeds[r][c] != null && wardBeds[r][c].getPatientID().equals(releaseID)) {

                                            wardBeds[r][c].setBedNumber(""); // 0 = no bed assigned, since real beds start at 1
                                            wardBeds[r][c] = null;

                                            freed = true;
                                            System.out.println("Success: Patient " + releaseID + " discharged. Bed is now available.");
                                            break;
                                        }
                                    }
                                    if (freed) break; // Stop outer loop if patient was found
                                }

                                // 4. Handle the "Not Found" scenario
                                if (!freed) {
                                    System.out.println("Error: Patient ID " + releaseID + " is not currently assigned to a bed.");
                                }
                                break;

                            case "3":

                                System.out.println("\n--- Complete Ward Layout ---");
                                int bedNum = 0; // tracks bed number as we go

                                for (int r = 0; r < wardBeds.length; r++) {
                                    for (int c = 0; c < wardBeds[r].length; c++) {
                                        bedNum++; // move to next bed number

                                        String bedLabel = String.format("B%02d", bedNum);

                                        if (wardBeds[r][c] == null) {
                                            System.out.print(bedLabel + ": [Available]  ");
                                        } else {
                                            System.out.print(bedLabel + ": [" + wardBeds[r][c].getPatientID() + "]  ");
                                        }
                                    }
                                    System.out.println();
                                }
                                break;

                            case "4":
                                System.out.println("\n--- Available Beds Report ---");
                                int availableCount = 0;
                                bedNum = 0;

                                for (int r = 0; r < 4; r++) {
                                    for (int c = 0; c < 5; c++) {
                                        bedNum++;

                                        if (wardBeds[r][c] == null) {
                                            System.out.printf("B%02d: [Available]\n", bedNum);
                                            availableCount++;
                                        }
                                    }
                                }

                                if (availableCount == 0) {
                                    System.out.println("Notice: No beds are currently available. The ward is full.");
                                } else {
                                    System.out.println("Total Available Beds: " + availableCount);
                                }
                                break;

                            case "5":
                                System.out.println("\n--- Occupied Beds Report ---");
                                int occupiedCount = 0;

                                for (int r = 0; r < 4; r++) {
                                    for (int c = 0; c < 5; c++) {
                                        if (wardBeds[r][c] != null) {

                                            String bedLabel = wardBeds[r][c].getBedNumber(); // read stored label directly
                                            String patientID = wardBeds[r][c].getPatientID();

                                            System.out.println(bedLabel + ": [Occupied by " + patientID + "]");
                                            occupiedCount++;
                                        }
                                    }
                                }

                                if (occupiedCount == 0) {
                                    System.out.println("Notice: There are currently no patients assigned to beds.");
                                } else {
                                    System.out.println("Total Occupied Beds: " + occupiedCount);
                                }
                                break;

                            case "6":
                                bedMenu = false;
                                break;

                            default:
                                System.out.println("Invalid choice, try again.");
                        }
                    }
                    break;

                case 7:
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                case 8:
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid selection. Please try again.");
            }

        } while (userChoice != QUIT);
    }
}



















