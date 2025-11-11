package project.model;

public class Broadcasts {
    private String nName;
    private String tNameA;
    private String cityA;
    private String gameDate;
    private String liveStartTime;

    public Broadcasts(String nName, String tNameA,
                      String cityA, String gameDate, String liveStartTime) {
        this.nName = nName;
        this.tNameA = tNameA;
        this.cityA = cityA;
        this.gameDate = gameDate;
        this.liveStartTime = liveStartTime;
    }

    public String getNName() {
        return nName;
    }

    public String getTNameA() {
        return tNameA;
    }

    public String getCityA() {
        return cityA;
    }

    public String getGameDate() {
        return gameDate;
    }

    public String getLiveStartTime() {
        return liveStartTime;
    }
}

