package project.model;

public class SportsPersonName {
    private SportsPerson spID;

    private final String name;

    public SportsPersonName(SportsPerson spID, String name) {
        this.spID = spID;
        this.name = name;
    }

    public SportsPerson getSpID() {
        return spID;
    }

    public String getName() {
        return name;
    }
}
