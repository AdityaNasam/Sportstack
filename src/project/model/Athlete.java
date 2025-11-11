package project.model;

public class Athlete {
    private SportsPerson spID;

    private final float weight;

    private final float height;

    private final int jerseyID;

    private final int numberOfMVPs;

    public Athlete(SportsPerson spID, float weight, float height, int jerseyID, int numberOfMVPs) {
        this.spID = spID;
        this.weight = weight;
        this.height = height;
        this.jerseyID = jerseyID;
        this.numberOfMVPs = numberOfMVPs;
    }

    public SportsPerson getSpID() {
        return spID;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public int getJerseyID() {
        return jerseyID;
    }

    public int getNumberOfMVPs() {
        return numberOfMVPs;
    }
}
