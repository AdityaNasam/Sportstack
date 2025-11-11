package project.model;

public class SportsPersons {
    private final int spID;

    private final int phoneNumber;

    private final float salary;

    private final String nationality;

    private final String birthDate;

    private final String sport;

    private final int deathDate;

    private final int age;

    public SportsPersons(int spID, int phoneNumber, float salary, String nationality, String birthDate, String sport, int deathDate, int age) {
        this.spID = spID;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.sport = sport;
        this.deathDate = deathDate;
        this.age = age;
    }

    public int getSpID() {
        return spID;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public float getSalary() {
        return salary;
    }

    public String getNationality() {
        return nationality;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getSport() {
        return sport;
    }

    public int getDeathDate() {
        return deathDate;
    }

    public int getAge() {
        return age;
    }
}
