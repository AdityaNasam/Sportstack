package project.model;

public class GameHostsInSeason {
    private String tNameA;
    private String cityA;
    private String tNameB;
    private String cityB;
    private String gameDate;
    private int scoreA;
    private int scoreB;
    private String weather;
    private String lName;
    private int yearL;
    private String address;

    public GameHostsInSeason(String tNameA,
                             String cityA,
                             String tNameB,
                             String cityB,
                             String gameDate,
                             int scoreA,
                             int scoreB,
                             String weather,
                             String lName,
                             int yearL,
                             String address) {
        this.tNameA = tNameA;
        this.cityA = cityA;
        this.tNameB = tNameB;
        this.cityB = cityB;
        this.gameDate = gameDate;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.weather = weather;
        this.lName = lName;
        this.yearL = yearL;
        this.address = address;
    }

    public String getTNameA() {
        return tNameA;
    }

    public String getCityA() {
        return cityA;
    }

    public String getTNameB() {
        return tNameB;
    }

    public String getCityB() {
        return cityB;
    }

    public String getGameDate() {
        return gameDate;
    }

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public String getWeather() {
        return weather;
    }

    public String getLName() {
        return lName;
    }

    public int getYearL() {
        return yearL;
    }

    public String getAddress() {
        return address;
    }
}
