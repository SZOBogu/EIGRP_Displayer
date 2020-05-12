package eigrp_displayer;

import org.hibernate.validator.constraints.Range;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public abstract class Device implements Addable {
    private String name;
    private IPAddress ip_address;
    private DeviceInterface[] deviceInterfaces;
    @Range(min = 0, max = 255)
    private Integer messageSendingTimeOffset;

    public Device(){
        this(4);
    }

    public Device(int numberOfInterfaces){
        this.name = "Device";
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

    public void setMessageSendingTimeOffset(Integer messageSendingTimeOffset) {
        if(messageSendingTimeOffset < 0)
            this.messageSendingTimeOffset = 0;
        else
            this.messageSendingTimeOffset = messageSendingTimeOffset;
    }

    @Override
    public String toString() {
        return  name + " (" + ip_address + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(getName(), device.getName()) &&
                Objects.equals(getIp_address(), device.getIp_address()) &&
                Arrays.equals(getDeviceInterfaces(), device.getDeviceInterfaces()) &&
                Objects.equals(getMessageSendingTimeOffset(), device.getMessageSendingTimeOffset());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName(), getIp_address(), getMessageSendingTimeOffset());
        result = 31 * result + Arrays.hashCode(getDeviceInterfaces());
        return result;
    }
}
