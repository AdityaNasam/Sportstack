package project.model;

public class SportsPersonDOB {
    private SportsPerson spID;

    private final String birthDate;

    public SportsPersonDOB(SportsPerson spID, String birthDate) {
        this.spID = spID;
        this.birthDate = birthDate;
    }

    public SportsPerson getSpID() {
        return spID;
    }

    public String getBirthDate() {
        return birthDate;
    }
}
