package project.model;

public class Network {
    private String nName;
    private int channel;

    public Network(String nName, int channel) {
        this.channel = channel;
        this.nName = nName;
    }

    public String getNName() {
        return this.nName;
    }

    public int getChannel() {
        return this.channel;
    }
}
