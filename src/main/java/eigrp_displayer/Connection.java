package eigrp_displayer;

public abstract class Connection implements Addable {
    private String name;
    private Device device1;
    private Device device2;
    private int bandwidth;
    private int delay;
    private int load;
    private int reliability;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Device getDevice1() {
        return device1;
    }

    public void setDevice1(Device device1) {
        this.device1 = device1;
    }

    public Device getDevice2() {
        return device2;
    }

    public void setDevice2(Device device2) {
        this.device2 = device2;
    }

    public void linkDevice(Device device){
        if(this.device1 == null){
            this.device1 = device;
        }
        else if(this.device2 == null){
            this.device2 = device;
        }
    }

    public Device getOtherDevice(Device device){
        if(this.device1 == device){
            return this.device2;
        }
        else if(this.device2 == device){
            return this.device1;
        }
        else
            return null;
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

    public int getReliability() {
        return reliability;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
    }
}