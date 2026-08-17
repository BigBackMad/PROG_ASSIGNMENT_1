public class Inpatient extends Patient {

    private int wardNumber;
    private String bedNumber;

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
}


