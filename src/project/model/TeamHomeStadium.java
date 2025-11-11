package project.model;

public class TeamHomeStadium {
    private final String tName;
    private final String city;
    private final String mascot;
    private String  sportName;
    private String address;
    public static final String[] COL_NAMES = {"tName", "city", "sportName", "address", "mascot"};

    public TeamHomeStadium(String tName, String city, String mascot, String sportName, String address) {
        this.tName = tName;
        this.city = city;
        this.mascot = mascot;
        this.sportName = sportName;
        this.address = address;
    }

    public String getTName() {
        return tName;
    }

    public String getSportName() {
        return sportName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {return this.city;}

    public String getMascot() {return this.mascot;}
}
