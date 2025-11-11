package project.model;

public class LeagueOrganises {
    private String lName;
    private int tier;
    private float revenue;
    private String sportName;

    public LeagueOrganises(String lName, int tier, float revenue, String sportName) {
        this.lName = lName;
        this.tier = tier;
        this.revenue = revenue;
        this.sportName = sportName;
    }

    public String getlName() {
        return lName;
    }

    public int getTier() {
        return tier;
    }

    public float getRevenue() {
        return revenue;
    }

    public String getSportName() {
        return this.sportName;
    }
}
