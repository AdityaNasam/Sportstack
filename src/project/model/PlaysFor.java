package project.model;

public class PlaysFor {

    private TeamHomeStadium teamHomeStadium;

    private LeagueOrganises leagueOrganises;

    public PlaysFor (TeamHomeStadium teamHomeStadium, LeagueOrganises leagueOrganises) {
        this.teamHomeStadium = teamHomeStadium;
        this.leagueOrganises = leagueOrganises;
    }

    public TeamHomeStadium getTeamHomeStadium() {
        return teamHomeStadium;
    }

    public LeagueOrganises getLeagueOrganises() {
        return leagueOrganises;
    }
}
