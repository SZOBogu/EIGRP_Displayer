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
        this.callForUpdate();
    }

    public Device getDevice2() {
        return device2;
    }

    public void setDevice2(Device device2) {
        this.device2 = device2;
        this.callForUpdate();
    }

    public void linkDevice(Device device){
        if(this.device1 == null){
            this.device1 = device;
            this.callForUpdate();
        }
        else if(this.device2 == null){
            this.device2 = device;
            this.callForUpdate();
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
        this.callForUpdate();
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
        this.callForUpdate();
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
        this.callForUpdate();
    }

    public int getReliability() {
        return reliability;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
        this.callForUpdate();
    }

    public void callForUpdate(){
        if(this.device1 != null)
            this.device1.checkConnection(this);
        if(this.device2 != null)
            this.device2.checkConnection(this);
    }
}
