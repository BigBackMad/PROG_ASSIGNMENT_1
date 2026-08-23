import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class PatientManagerTest {

    private ArrayList<Patient> patientList;
    private Inpatient[][] wardBeds;

    // BeforeEach Runs before every test method
    //gives each test empty arrays

    @BeforeEach
    public void setUp() {
        patientList = new ArrayList<Patient>();
        wardBeds = new Inpatient[4][5];
    }

    //register first patient
    @Test
    public void testRegisterPatient_Success() {
        String result = PatientManager.registerPatient(patientList, "100001", "John", "Smith",
                30, "Male", "Flu", 2);

        assertEquals("SUCCESS", result);
        assertEquals(1, patientList.size());
        assertEquals("100001", patientList.get(0).getPatientID());
    }

    //test for duplicate ID, shold be rejected
    @Test
    public void testRegisterPatient_DuplicateIDPrevented() {
        PatientManager.registerPatient(patientList, "100001", "John", "Smith", 30, "Male", "Flu", 2);

        // Try to register a second patient using the SAME ID
        String result = PatientManager.registerPatient(patientList, "100001", "Jane", "Doe", 25, "Female", "Cold", 2);

        assertEquals("DUPLICATE_ID", result);
        assertEquals(1, patientList.size()); // still only 1 patient - second one was rejected
    }


    // Searching for an ID that exists
    @Test
    public void testFindPatientByID_Found() {
        PatientManager.registerPatient(patientList, "100001", "John", "Smith", 30, "Male", "Flu", 2);

        Patient result = PatientManager.findPatientByID(patientList, "100001");

        assertNotNull(result);
        assertEquals("Smith", result.getLastName());
    }

    // Searching for an ID that doesn't exist should return null,

    @Test
    public void testFindPatientByID_NotFound() {
        Patient result = PatientManager.findPatientByID(patientList, "999999");

        assertNull(result);
    }


    // Updating patient's condition and age should actually change

    @Test
    public void testUpdatePatientDetails() {
        PatientManager.registerPatient(patientList, "100001", "John", "Smith", 30, "Male", "Flu", 2);

        Patient patient = PatientManager.findPatientByID(patientList, "100001");
        patient.setMedicalCondition("Recovered");
        patient.setAge(31);

        Patient updated = PatientManager.findPatientByID(patientList, "100001");
        assertEquals("Recovered", updated.getMedicalCondition());
        assertEquals(31, updated.getAge());
    }


    // Deleting patient - shrinks the list.
    @Test
    public void testDeletePatient_Success() {
        PatientManager.registerPatient(patientList, "100001", "John", "Smith", 30, "Male", "Flu", 2);

        String result = PatientManager.deletePatient(patientList, wardBeds, "100001");

        assertEquals("SUCCESS", result);
        assertEquals(0, patientList.size());
    }

    // Trying to delete an ID that was never registered should fail cleanly

    @Test
    public void testDeletePatient_NotFound() {
        String result = PatientManager.deletePatient(patientList, wardBeds, "999999");

        assertEquals("NOT_FOUND", result);
    }

    //  Inpatient with no bed should be allocated free bed.
    @Test
    public void testAllocateBed_Success() {
        PatientManager.registerPatient(patientList, "100001", "John", "Smith", 30, "Male", "Flu", 1); // 1 = Inpatient

        String result = PatientManager.allocateBed(patientList, wardBeds, "100001");

        assertEquals("B01", result); // first bed in the grid
        assertEquals(1, PatientManager.countOccupiedBeds(wardBeds));
    }

    // Outpatients and Emergency patients  not allowed hospital beds

    @Test
    public void testAllocateBed_NotInpatient() {
        PatientManager.registerPatient(patientList, "100001", "John", "Smith", 30, "Male", "Flu", 2); // 2 = Outpatient

        String result = PatientManager.allocateBed(patientList, wardBeds, "100001");

        assertEquals("NOT_INPATIENT", result);
    }

    // A patient who already has a bed shouldn't be allocated a second one.
    @Test
    public void testAllocateBed_PreventsDoubleAllocation() {
        PatientManager.registerPatient(patientList, "100001", "John", "Smith", 30, "Male", "Flu", 1);
        PatientManager.allocateBed(patientList, wardBeds, "100001"); // first allocation

        String result = PatientManager.allocateBed(patientList, wardBeds, "100001"); // try again

        assertEquals("ALREADY_HAS_BED", result);
    }

    // all beds occupied, prevents allocation of ward bed
    @Test
    public void testAllocateBed_PreventsAllocationWhenWardFull() {
        // Fill all 20 beds with 20 different Inpatients
        for (int i = 1; i <= 20; i++) {
            String id = String.format("1000%02d", i); // e.g. 100001, 100002, ... 100020
            PatientManager.registerPatient(patientList, id, "First" + i, "Last" + i, 30, "Male", "Condition", 1);
            PatientManager.allocateBed(patientList, wardBeds, id);
        }

        // Register one more Inpatient and try to allocate - ward should be full
        PatientManager.registerPatient(patientList, "100021", "Extra", "Patient", 30, "Male", "Condition", 1);
        String result = PatientManager.allocateBed(patientList, wardBeds, "100021");

        assertEquals("WARD_FULL", result);
        assertEquals(20, PatientManager.countOccupiedBeds(wardBeds));
    }


    // Releasing patient who  has a bed should free that bed.
    @Test
    public void testReleaseBed_Success() {
        PatientManager.registerPatient(patientList, "100001", "John", "Smith", 30, "Male", "Flu", 1);
        PatientManager.allocateBed(patientList, wardBeds, "100001");

        String result = PatientManager.releaseBed(wardBeds, "100001");

        assertEquals("SUCCESS", result);
        assertEquals(0, PatientManager.countOccupiedBeds(wardBeds));
    }

    @Test
    public void testReleaseBed_NotFound() {
        String result = PatientManager.releaseBed(wardBeds, "999999");

        assertEquals("NOT_FOUND", result);
    }


    // Sorting by surname should put patients in alphabetical order
    @Test
    public void testSortBySurname() {
        PatientManager.registerPatient(patientList, "100001", "Zoe", "Zulu", 30, "Female", "Flu", 2);
        PatientManager.registerPatient(patientList, "100002", "Amy", "Adams", 25, "Female", "Cold", 2);
        PatientManager.registerPatient(patientList, "100003", "Mike", "Mendez", 40, "Male", "Cough", 2);

        PatientManager.sortBySurname(patientList);

        assertEquals("Adams", patientList.get(0).getLastName());
        assertEquals("Mendez", patientList.get(1).getLastName());
        assertEquals("Zulu", patientList.get(2).getLastName());
    }

    // Sorting by Patient ID should put patients in ascending ID order,
    @Test
    public void testSortByPatientID() {
        PatientManager.registerPatient(patientList, "100003", "Mike", "Mendez", 40, "Male", "Cough", 2);
        PatientManager.registerPatient(patientList, "100001", "Zoe", "Zulu", 30, "Female", "Flu", 2);
        PatientManager.registerPatient(patientList, "100002", "Amy", "Adams", 25, "Female", "Cold", 2);

        PatientManager.sortByPatientID(patientList);

        assertEquals("100001", patientList.get(0).getPatientID());
        assertEquals("100002", patientList.get(1).getPatientID());
        assertEquals("100003", patientList.get(2).getPatientID());
    }
}
