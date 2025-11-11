package project.model;

public class SportsPerson {
    private final int spID;

    private final int phoneNumber;


    public SportsPerson(int spID, int phoneNumber) {
        this.spID = spID;
        this.phoneNumber = phoneNumber;
    }

    public int getSpID() {
        return spID;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}
