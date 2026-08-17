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
            System.out.println(" 1. Register a new patient.\n" +
                    " 2. Search for a patient using their Patient ID.\n" +
                    " 3. Update an existing patient's details.\n" +
                    " 4. Delete a patient.\n" +
                    " 5. Display all registered patients.\n" +
                    " 6. Bed Management.\n" +
                    " 7. Exit.\n");
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
                                // TODO: allocate an available bed to an Inpatient
                                break;

                            case "2":
                                // TODO: release a bed when a patient is discharged
                                break;

                            case "3":
                                // TODO: display the full 4x5 ward layout
                                break;

                            case "4":
                                // TODO: display only available (empty) beds
                                break;

                            case "5":
                                // TODO: display only occupied beds
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

                default:
                    System.out.println("Invalid selection. Please try again.");
            }

        } while (userChoice != QUIT);
    }
}



















