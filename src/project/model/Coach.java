package project.model;

public class Coach {
    private SportsPerson spID;

    private final int yearsCoaching;

    private final int numberOfChampionships;

    public Coach(SportsPerson spID, int yearsCoaching, int numberOfChampionships) {
        this.spID = spID;
        this.yearsCoaching = yearsCoaching;
        this.numberOfChampionships = numberOfChampionships;
    }

    public SportsPerson getSpID() {
        return spID;
    }

    public int getYearsCoaching() {
        return yearsCoaching;
    }

    public int getNumberOfChampionships() {
        return numberOfChampionships;
    }
}
