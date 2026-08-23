import java.util.Scanner;
import java.util.*;

public class Main {

    private static Inpatient[][] wardBeds = new Inpatient[4][5];

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Patient> patientList = new ArrayList<Patient>();

        final int QUIT = 9;
        int userChoice;

        do {
            System.out.println(
                    " 1. Register a new patient.\n" +
                            " 2. Search for a patient using their Patient ID.\n" +
                            " 3. Update an existing patient's details.\n" +
                            " 4. Delete a patient.\n" +
                            " 5. Display all registered patients.\n" +
                            " 6. Bed Management.\n" +
                            " 7. Reports.\n" +
                            " 8. Sort Patients.\n" +
                            " 9. Exit.\n");

            System.out.print(" Enter your choice--> ");
            try {
                userChoice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // clear the bad input
                userChoice = -1; // doesn't match any case, falls to default
            }

            switch (userChoice) {

                case 1:
                    System.out.println("\n--- Register New Patient ---");

                    System.out.print("Enter 6-digit Patient ID >> ");
                    String id = sc.nextLine();

                    System.out.print("Enter First Name >> ");
                    String firstName = sc.nextLine();

                    System.out.print("Enter Last Name >> ");
                    String lastName = sc.nextLine();

                    System.out.print("Enter Age >> ");
                    int age;
                    try {
                        age = sc.nextInt();
                        sc.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid age entered. Registration cancelled.");
                        sc.nextLine();
                        break;
                    }

                    System.out.print("Enter Gender >> ");
                    String gender = sc.nextLine();

                    System.out.print("Enter Medical Condition >> ");
                    String condition = sc.nextLine();

                    // Build the category menu from the enum itself using .values()
                    System.out.println("Select Category:");
                    PatientCategory[] categories = PatientCategory.values();
                    for (int i = 0; i < categories.length; i++) {
                        System.out.println((i + 1) + ". " + categories[i].name());
                    }
                    System.out.print("Enter Choice >> ");
                    int catChoice;
                    try {
                        catChoice = sc.nextInt();
                        sc.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid choice entered. Registration cancelled.");
                        sc.nextLine();
                        break;
                    }

                    String registerResult = PatientManager.registerPatient(patientList, id, firstName, lastName, age, gender, condition, catChoice);

                    if (registerResult.equals("DUPLICATE_ID")) {
                        System.out.println("Error: A patient with ID " + id + " is already registered.");
                    } else {
                        System.out.println("Registration complete. Total patients: " + patientList.size());
                    }
                    break;

                case 2:
                    System.out.println("\n--- Search for Patient ---");
                    System.out.print("Enter Patient ID to search >> ");
                    String searchID = sc.nextLine();

                    Patient searchResult = PatientManager.findPatientByID(patientList, searchID);

                    if (searchResult != null) {
                        System.out.println("Patient Record Found:");
                        searchResult.displayDetails();
                    } else {
                        System.out.println("Error: Patient with ID " + searchID + " not found.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Update Patient Details ---");
                    System.out.print("Enter Patient ID to update >> ");
                    String updateID = sc.nextLine();

                    Patient foundPatient = PatientManager.findPatientByID(patientList, updateID);

                    if (foundPatient != null) {
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
                                    int newCatChoice;
                                    try {
                                        newCatChoice = sc.nextInt();
                                        sc.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Category not changed.");
                                        sc.nextLine();
                                        break;
                                    }

                                    if (newCatChoice == 1) {
                                        foundPatient.setCategory(PatientCategory.INPATIENT);
                                        System.out.println("Category updated.");
                                    } else if (newCatChoice == 2) {
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
                    System.out.println("\n--- Delete Patient Record ---");

                    if (patientList.isEmpty()) {
                        System.out.println("Error: The patient database is currently empty.");
                    } else {
                        System.out.print("Enter Patient ID to delete >> ");
                        String deleteID = sc.nextLine();

                        String deleteResult = PatientManager.deletePatient(patientList, wardBeds, deleteID);

                        if (deleteResult.equals("SUCCESS")) {
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

                                String allocResult = PatientManager.allocateBed(patientList, wardBeds, targetID);

                                switch (allocResult) {
                                    case "NOT_FOUND":
                                        System.out.println("Error: Patient ID not found.");
                                        break;
                                    case "NOT_INPATIENT":
                                        System.out.println("Error: Only Inpatients can be allocated a hospital bed.");
                                        break;
                                    case "ALREADY_HAS_BED":
                                        System.out.println("Error: This patient is already assigned to a bed.");
                                        break;
                                    case "WARD_FULL":
                                        System.out.println("Error: No beds available. The ward is currently full.");
                                        break;
                                    default:
                                        System.out.println("Success: Patient " + targetID + " allocated to Bed " + allocResult);
                                }
                                break;

                            case "2":
                                System.out.println("\n--- Release a Bed (Discharge) ---");
                                System.out.print("Enter Patient ID to release from bed >> ");
                                String releaseID = sc.nextLine();

                                String releaseResult = PatientManager.releaseBed(wardBeds, releaseID);

                                if (releaseResult.equals("SUCCESS")) {
                                    System.out.println("Success: Patient " + releaseID + " discharged. Bed is now available.");
                                } else {
                                    System.out.println("Error: Patient ID " + releaseID + " is not currently assigned to a bed.");
                                }
                                break;

                            case "3":
                                System.out.println("\n--- Complete Ward Layout ---");
                                int layoutBedNum = 0;

                                for (int r = 0; r < wardBeds.length; r++) {
                                    for (int c = 0; c < wardBeds[r].length; c++) {
                                        layoutBedNum++;

                                        String bedLabel = String.format("B%02d", layoutBedNum);

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
                                int availBedNum = 0;

                                for (int r = 0; r < wardBeds.length; r++) {
                                    for (int c = 0; c < wardBeds[r].length; c++) {
                                        availBedNum++;

                                        if (wardBeds[r][c] == null) {
                                            System.out.printf("B%02d: [Available]\n", availBedNum);
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

                                for (int r = 0; r < wardBeds.length; r++) {
                                    for (int c = 0; c < wardBeds[r].length; c++) {
                                        if (wardBeds[r][c] != null) {
                                            String bedLabel = wardBeds[r][c].getBedNumber();
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
                    System.out.println("\n--- Ward & Patient Reports ---");

                    System.out.println("\nRegistered Patients:");
                    if (patientList.isEmpty()) {
                        System.out.println("No patients are currently registered.");
                    } else {
                        for (int i = 0; i < patientList.size(); i++) {
                            patientList.get(i).displayDetails();
                            System.out.println("---------------------------------");
                        }
                    }

                    System.out.println("\nAvailable Beds:");
                    int reportAvailableCount = 0;
                    int reportBedNum = 0;
                    for (int r = 0; r < wardBeds.length; r++) {
                        for (int c = 0; c < wardBeds[r].length; c++) {
                            reportBedNum++;
                            if (wardBeds[r][c] == null) {
                                System.out.printf("B%02d: [Available]\n", reportBedNum);
                                reportAvailableCount++;
                            }
                        }
                    }
                    if (reportAvailableCount == 0) {
                        System.out.println("No beds are currently available.");
                    }

                    System.out.println("\nOccupied Beds:");
                    int reportOccupiedCount = 0;
                    for (int r = 0; r < wardBeds.length; r++) {
                        for (int c = 0; c < wardBeds[r].length; c++) {
                            if (wardBeds[r][c] != null) {
                                String bedLabel = wardBeds[r][c].getBedNumber();
                                String patientID = wardBeds[r][c].getPatientID();
                                System.out.println(bedLabel + ": [Occupied by " + patientID + "]");
                                reportOccupiedCount++;
                            }
                        }
                    }
                    if (reportOccupiedCount == 0) {
                        System.out.println("No beds are currently occupied.");
                    }

                    System.out.println("\nTotal Registered Patients: " + patientList.size());
                    System.out.println("Total Occupied Beds: " + reportOccupiedCount);

                    int totalBeds = wardBeds.length * wardBeds[0].length;
                    double occupancyPercentage = ((double) reportOccupiedCount / totalBeds) * 100;
                    System.out.printf("Ward Occupancy: %.1f%%\n", occupancyPercentage);

                    break;

                case 8:
                    System.out.println("\n--- Sort Patients ---");

                    if (patientList.isEmpty()) {
                        System.out.println("No patients to sort.");
                        break;
                    }

                    System.out.println("1. Sort by Surname");
                    System.out.println("2. Sort by Patient ID");
                    System.out.print(">> ");
                    String sortChoice = sc.nextLine();

                    if (sortChoice.equals("1")) {
                        PatientManager.sortBySurname(patientList);
                    } else if (sortChoice.equals("2")) {
                        PatientManager.sortByPatientID(patientList);
                    } else {
                        System.out.println("Invalid choice.");
                        break;
                    }

                    System.out.println("Patients sorted successfully.");
                    System.out.println("---------------------------------");
                    for (int i = 0; i < patientList.size(); i++) {
                        patientList.get(i).displayDetails();
                        System.out.println("---------------------------------");
                    }
                    break;

                case 9:
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid selection. Please try again.");
            }

        } while (userChoice != QUIT);
    }
}