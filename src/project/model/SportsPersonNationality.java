package project.model;

public class SportsPersonNationality {
    private SportsPerson spID;

    private final String nationality;

    public SportsPersonNationality(SportsPerson spID, String nationality) {
        this.spID = spID;
        this.nationality = nationality;
    }

    public SportsPerson getSpID() {
        return spID;
    }

    public String getNationality() {
        return nationality;
    }
}
