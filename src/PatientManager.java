import java.util.ArrayList;

public class PatientManager {

    // checks if patient ID alrady exists
    public static boolean isDuplicateID(ArrayList<Patient> patientList, String id) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getPatientID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // returns the patient with matching ID, or null
    public static Patient findPatientByID(ArrayList<Patient> patientList, String id) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getPatientID().equals(id)) {
                return patientList.get(i);
            }
        }
        return null;
    }

    // register patient
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

    // Deletes a patient and frees bed if they had one
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

    // Allocates  first free bed to given patient ID.
    // Returns bed code
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

    // Releases the bed occupied
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

    // Bubble sorts patientList by surname
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

    // Bubble sorts patientList by Patient ID
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

    // Counts how many beds are occupied
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
