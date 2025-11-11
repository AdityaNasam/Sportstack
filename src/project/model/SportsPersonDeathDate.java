package project.model;

public class SportsPersonDeathDate {
    private SportsPerson spID;

    private final String deathDate;

    public SportsPersonDeathDate(SportsPerson spID, String deathDate) {
        this.spID = spID;
        this.deathDate = deathDate;
    }

    public SportsPerson getSpID() {
        return spID;
    }

    public String getDeathDate() {
        return deathDate;
    }
}
