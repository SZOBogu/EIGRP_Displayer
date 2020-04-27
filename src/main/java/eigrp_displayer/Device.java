package eigrp_displayer;

import java.util.Random;

public abstract class Device implements Addable {
    private String name;
    private IPAddress ip_address;
    private DeviceInterface[] deviceInterfaces;
    private final int messageSendingTimeOffset;


    public Device(){
        this(4);
    }

    public Device(int numberOfInterfaces){
        this.deviceInterfaces = new DeviceInterface[numberOfInterfaces];
        for(int i = 0; i < numberOfInterfaces; i++){
            this.deviceInterfaces[i] = new DeviceInterface("Interface " + i);
        }
        this.messageSendingTimeOffset = new Random().nextInt(60);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IPAddress getIp_address() {
        return ip_address;
    }

    public void setIp_address(IPAddress ip_address) {
        this.ip_address = ip_address;
    }

    public DeviceInterface[] getDeviceInterfaces() {
        return deviceInterfaces;
    }

    public int getMessageSendingTimeOffset() {
        return messageSendingTimeOffset;
    }
}
