package eigrp_displayer;

public abstract class Connection implements Addable {
    private String name;
    private DeviceController device1;
    private DeviceController device2;
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

    public DeviceController getDevice1() {
        return device1;
    }

    public void setDevice1(DeviceController device1) {
        this.device1 = device1;
    }

    public DeviceController getDevice2() {
        return device2;
    }

    public void setDevice2(DeviceController device2) {
        this.device2 = device2;
    }

    public void linkDevice(DeviceController device){
        if(this.device1 == null){
            this.device1 = device;
            this.device1.setConnection(this);
        }
        else if(this.device2 == null){
            this.device2 = device;
            this.device2.setConnection(this);
        }
    }

    public void linkDevices(DeviceController controller1, DeviceController controller2){
        this.setDevice1(controller1);
        this.device1.setConnection(this);
        this.setDevice2(controller2);
        this.device2.setConnection(this);
    }

    public DeviceController getOtherDevice(DeviceController device){
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
