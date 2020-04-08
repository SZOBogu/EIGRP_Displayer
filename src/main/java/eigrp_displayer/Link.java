package eigrp_displayer;

public abstract class Link implements Addable {
    private String name;
    private Device device1;
    private Device device2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //TODO: tests
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
        if(this.device1 != null){
            this.device1 = device;
        }
        else if(this.device2 != null){
            this.device2 = device;
        }
    }
}
