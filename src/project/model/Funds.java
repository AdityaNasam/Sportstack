package project.model;

public class Funds {
    private String sponName;
    private String lName;
    private float monetaryContribution;

    public Funds(String sponName, String lName, float monetaryContribution) {
        this.sponName = sponName;
        this.lName = lName;
        this.monetaryContribution = monetaryContribution;
    }

    public String getSponName() {
        return sponName;
    }

    public String getLName() {
        return lName;
    }

    public float getMonetaryContribution() {
        return monetaryContribution;
    }
}
