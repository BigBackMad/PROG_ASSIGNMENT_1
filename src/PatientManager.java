import java.util.ArrayList;

/*
 * Holds the core patient/bed logic, separated out from Main so it can be
 * unit tested without needing Scanner input.
 */

public class PatientManager {

    // Returns true if a patient with this ID already exists
    public static boolean isDuplicateID(ArrayList<Patient> patientList, String id) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getPatientID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // Returns the matching Patient, or null if not found
    public static Patient findPatientByID(ArrayList<Patient> patientList, String id) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getPatientID().equals(id)) {
                return patientList.get(i);
            }
        }
        return null;
    }

    // Registers a patient. Returns "DUPLICATE_ID" or "SUCCESS".
    public static String registerPatient(ArrayList<Patient> patientList, String id, String firstName,
                                         String lastName, int age, String gender, String condition,
                                         int catChoice) {
        if (isDuplicateID(patientList, id)) {
            return "DUPLICATE_ID";
        }

        Patient newPatient;
        if (catChoice == 1) {
            newPatient = new Inpatient(id, firstName, lastName, age, gender, condition, PatientCategory.INPATIENT);
        } else if (catChoice == 2) {
            newPatient = new Patient();
            newPatient.setPatientID(id);
            newPatient.setFirstName(firstName);
            newPatient.setLastName(lastName);
            newPatient.setAge(age);
            newPatient.setGender(gender);
            newPatient.setMedicalCondition(condition);
            newPatient.setCategory(PatientCategory.OUTPATIENT);
        } else {
            newPatient = new Patient();
            newPatient.setPatientID(id);
            newPatient.setFirstName(firstName);
            newPatient.setLastName(lastName);
            newPatient.setAge(age);
            newPatient.setGender(gender);
            newPatient.setMedicalCondition(condition);
            newPatient.setCategory(PatientCategory.EMERGENCY);
        }

        patientList.add(newPatient);
        return "SUCCESS";
    }

    // Deletes a patient and frees their bed if they had one. Returns "NOT_FOUND" or "SUCCESS".
    public static String deletePatient(ArrayList<Patient> patientList, Inpatient[][] wardBeds, String id) {
        int deleteIndex = -1;
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getPatientID().equals(id)) {
                deleteIndex = i;
                break;
            }
        }

        if (deleteIndex == -1) {
            return "NOT_FOUND";
        }

        for (int row = 0; row < wardBeds.length; row++) {
            for (int col = 0; col < wardBeds[row].length; col++) {
                if (wardBeds[row][col] != null && wardBeds[row][col].getPatientID().equals(id)) {
                    wardBeds[row][col] = null;
                }
            }
        }

        patientList.remove(deleteIndex);
        return "SUCCESS";
    }

    // Allocates the first free bed to the given patient ID.
    // Returns the bed code (e.g. "B07") on success, or one of:
    // "NOT_FOUND", "NOT_INPATIENT", "ALREADY_HAS_BED", "WARD_FULL"
    public static String allocateBed(ArrayList<Patient> patientList, Inpatient[][] wardBeds, String targetID) {
        Patient targetPatient = findPatientByID(patientList, targetID);

        if (targetPatient == null) {
            return "NOT_FOUND";
        }
        if (targetPatient.getCategory() != PatientCategory.INPATIENT) {
            return "NOT_INPATIENT";
        }

        for (int r = 0; r < wardBeds.length; r++) {
            for (int c = 0; c < wardBeds[r].length; c++) {
                if (wardBeds[r][c] != null && wardBeds[r][c].getPatientID().equals(targetID)) {
                    return "ALREADY_HAS_BED";
                }
            }
        }

        int bedNum = 0;
        for (int r = 0; r < wardBeds.length; r++) {
            for (int c = 0; c < wardBeds[r].length; c++) {
                bedNum++;
                if (wardBeds[r][c] == null) {
                    wardBeds[r][c] = (Inpatient) targetPatient;

                    String bedCode = String.format("B%02d", bedNum);
                    wardBeds[r][c].setWardNumber(1);
                    wardBeds[r][c].setBedNumber(bedCode);

                    return bedCode;
                }
            }
        }

        return "WARD_FULL";
    }

    // Releases the bed occupied by the given patient ID. Returns "NOT_FOUND" or "SUCCESS".
    public static String releaseBed(Inpatient[][] wardBeds, String releaseID) {
        for (int r = 0; r < wardBeds.length; r++) {
            for (int c = 0; c < wardBeds[r].length; c++) {
                if (wardBeds[r][c] != null && wardBeds[r][c].getPatientID().equals(releaseID)) {
                    wardBeds[r][c].setBedNumber("");
                    wardBeds[r][c] = null;
                    return "SUCCESS";
                }
            }
        }
        return "NOT_FOUND";
    }

    // Bubble sorts patientList by surname (in place)
    public static void sortBySurname(ArrayList<Patient> patientList) {
        for (int i = 0; i < patientList.size() - 1; i++) {
            for (int j = 0; j < patientList.size() - 1 - i; j++) {
                Patient current = patientList.get(j);
                Patient next = patientList.get(j + 1);

                if (current.getLastName().compareToIgnoreCase(next.getLastName()) > 0) {
                    patientList.set(j, next);
                    patientList.set(j + 1, current);
                }
            }
        }
    }

    // Bubble sorts patientList by Patient ID (in place)
    public static void sortByPatientID(ArrayList<Patient> patientList) {
        for (int i = 0; i < patientList.size() - 1; i++) {
            for (int j = 0; j < patientList.size() - 1 - i; j++) {
                Patient current = patientList.get(j);
                Patient next = patientList.get(j + 1);

                if (current.getPatientID().compareToIgnoreCase(next.getPatientID()) > 0) {
                    patientList.set(j, next);
                    patientList.set(j + 1, current);
                }
            }
        }
    }

    // Counts how many beds are currently occupied
    public static int countOccupiedBeds(Inpatient[][] wardBeds) {
        int count = 0;
        for (int r = 0; r < wardBeds.length; r++) {
            for (int c = 0; c < wardBeds[r].length; c++) {
                if (wardBeds[r][c] != null) {
                    count++;
                }
            }
        }
        return count;
    }
}
