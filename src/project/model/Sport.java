package project.model;

public class Sport {

    private String sportName;

    private String rules;

    public Sport(String sportName, String rules) {
        this.sportName = sportName;
        this.rules = rules;
    }

    public String getSportName() {
        return sportName;
    }

    public String getRules() {
        return rules;
    }
}
