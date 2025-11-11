package project.model;

public class SportsPersonSalary {
    private SportsPerson spID;

    private final float salary;

    public SportsPersonSalary(SportsPerson spID, float salary) {
        this.spID = spID;
        this.salary = salary;
    }

    public SportsPerson getSpID() {
        return spID;
    }

    public float getSalary() {
        return salary;
    }
}
