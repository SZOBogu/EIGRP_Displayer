package eigrp_displayer;

public class Cable extends Connection {
    public Cable(){
        this("Ethernet Cable", 1000, 255, 255, 255);
    }
    //

    public Cable(String name, int bandwidth, int delay, int load, int reliability){
        this.setName(name);
        this.setBandwidth(bandwidth);
        this.setDelay(delay);
        this.setLoad(load);
        this.setReliability(reliability);
    }
}
