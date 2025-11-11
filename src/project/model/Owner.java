package project.model;

public class Owner {
    private SportsPerson spID;

    private final float netWorth;

    public Owner(SportsPerson spID, float netWorth) {
        this.spID = spID;
        this.netWorth = netWorth;
    }
}
