package project.model;

public class Sponsor {
    private String sponName;
    private float networth;

    public Sponsor(String name, float networth) {
        this.sponName = name;
        this.networth = networth;
    }

    public String getSponName() {
        return this.sponName;
    }

    public float getNetworth() {
        return this.networth;
    }
}
