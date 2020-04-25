package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public abstract class Device implements Addable {
    private String name;
    private IPAddress ip_address;
    private DeviceInterface[] deviceInterfaces;

    public Device(){
        this(4);
    }

    public Device(int numberOfInterfaces){
        this.deviceInterfaces = new DeviceInterface[numberOfInterfaces];
        for(int i = 0; i < numberOfInterfaces; i++){
            this.deviceInterfaces[i] = new DeviceInterface("Interface " + i);
        }
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

    public void setConnection(Connection connection){
        for(int i = 0; i < this.getDeviceInterfaces().length; i++){
            if(this.getDeviceInterfaces()[i].getConnection() == null) {
                this.getDeviceInterfaces()[i].setConnection(connection);
                break;
            }
        }
    }

    public List<Device> getAllConnectedDevices(){
        List<Device> devices = new ArrayList<>();
        try {
            for (DeviceInterface deviceInterface : this.getDeviceInterfaces()) {
                Device device = deviceInterface.getConnection().getOtherDevice(this);
                if (device != null) {
                    devices.add(device);
                }
            }
            return devices;
        }
        catch (Exception e) {
            return devices;
        }
    }

    public Device getConnectedDevice(IPAddress ipAddress){
        for(DeviceInterface deviceInterface : this.deviceInterfaces){
            Device device = deviceInterface.getConnection().getOtherDevice(this);
            if(device.getIp_address().equals(ipAddress)){
                return device;
            }
        }
        return null;
    }

    //TODO: test
    public Connection getConnectionWithDevice(Device device){
        for(DeviceInterface deviceInterface : this.deviceInterfaces){
            Connection connection = deviceInterface.getConnection();
            if(connection == device.getConnectionWithDevice(this)){
                return connection;
            }
        }
        return null;
    }
    //TODO: implement, rename, test
    public void updateMetric(Connection connection){
        //przelicz trasy
    }
}
