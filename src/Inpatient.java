public class Inpatient extends Patient {

    private int wardNumber;
    private int bedNumber;

    public void setWardNumber(int wardNumber) {
        this.wardNumber = wardNumber;
    }

    public int getWardNumber() {
        return wardNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public int getBedNumber() {
        return bedNumber;
    }
}


