package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public class ShowcaseNetwork {
    private IPAddress networkAddress;
    private IPAddress broadcastAddress;
    private Mask mask;
    private PoolOfIPAddresses pool;
    private List<Device> devices;

    public ShowcaseNetwork(IPAddress networkAddress, IPAddress broadcastAddress, Mask mask){
        this.networkAddress = networkAddress;
        this.broadcastAddress = broadcastAddress;
        this.mask = mask;
        this.pool = new PoolOfIPAddresses();
        this.pool.init(networkAddress, mask);
        this.devices = new ArrayList<>();
    }

    public void addDevice(Device device){
        this.devices.add(device);
        device.setIp_address(this.pool.getIPAddress());
    }

    public void removeDevice(Device device){
        this.pool.releaseIPAddress(device.getIp_address());
        device.setIp_address(null);
        this.devices.remove(device);
    }

    public void linkDevices(Device device1, Device device2){
        Cable cable = new Cable();
        device1.setConnection(cable);
        device2.setConnection(cable);
        cable.setDevice1(device1);
        cable.setDevice2(device2);
    }

    public Device getDevice(IPAddress ipAddress){
        for(Device device : this.devices){
            if(device.getIp_address().equals(ipAddress))
                return device;
        }
        return null;
    }

    public Mask getMask() {
        return mask;
    }

    public void setMask(Mask mask) {
        this.mask = mask;
    }

    public IPAddress getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(IPAddress networkAddress) {
        this.networkAddress = networkAddress;
    }

    public IPAddress getBroadcastAddress() {
        return broadcastAddress;
    }

    public void setBroadcastAddress(IPAddress broadcastAddress) {
        this.broadcastAddress = broadcastAddress;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}