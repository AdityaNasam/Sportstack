package project.model;

public class SportsPersonAge {
    private SportsPerson spID;

    private final int age;

    public SportsPersonAge(SportsPerson spID, int age) {
        this.spID = spID;
        this.age = age;
    }

    public SportsPerson getSpID() {
        return spID;
    }

    public int getAge() {
        return age;
    }
}
