package eigrp_displayer;

public class Cable extends Link{
    private int bandwidth;
    private int delay;

    public Cable(){
        this.setName("Ethernet Cable");
        this.bandwidth = 100000;
        this.delay = 100;
    }

    public Cable(String name, int bandwidth, int delay){
        this.setName(name);
        this.bandwidth = bandwidth;
        this.delay = delay;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
