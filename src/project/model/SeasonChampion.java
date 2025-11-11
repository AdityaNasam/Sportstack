package project.model;

public class SeasonChampion {

    private LeagueOrganises leagueOrganises;

    private int year;

    private String startDate;

    private String endDate;

    private int daysRun;

    private TeamHomeStadium teamHomeStadium;

    public SeasonChampion(LeagueOrganises leagueOrganises, int year, String startDate, String endDate, int daysRun, TeamHomeStadium teamHomeStadium) {
        this.leagueOrganises = leagueOrganises;
        this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysRun = daysRun;
        this.teamHomeStadium = teamHomeStadium;
    }

    public LeagueOrganises getLeagueOrganises() {
        return leagueOrganises;
    }

    public int getYear() {
        return year;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getDaysRun() {
        return daysRun;
    }

    public TeamHomeStadium getTeamHomeStadium() {
        return teamHomeStadium;
    }
}
