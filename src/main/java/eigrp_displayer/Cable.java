package eigrp_displayer;

public class Cable extends Link{
    private int bandwidth;
    private int delay;
    private int load;

    public Cable(){
        this.setName("Ethernet Cable");
        this.bandwidth = 100000;
        this.delay = 100;
        this.load = 10;
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

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }
}
