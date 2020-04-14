package eigrp_displayer;

public class Cable extends Link{
    public Cable(){
        this("Ethernet Cable", 100000, 100, 10);
    }

    public Cable(String name, int bandwidth, int delay, int load){
        this.setName(name);
        this.setBandwidth(bandwidth);
        this.setDelay(delay);
        this.setLoad(load);
    }
}
