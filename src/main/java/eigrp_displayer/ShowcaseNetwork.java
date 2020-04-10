package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public class ShowcaseNetwork {
    private IPAddress networkAddress;
    private IPAddress broadcastAddress;
    private SubnetMask mask;

    private PoolOfIPAddresses pool;
    private List<Link> connections;
    private List<Device> devices;

    public ShowcaseNetwork(IPAddress networkAddress, IPAddress broadcastAddress, SubnetMask mask){
        this.networkAddress = networkAddress;
        this.broadcastAddress = broadcastAddress;
        this.mask = mask;
        this.pool = new PoolOfIPAddresses();
        this.pool.init(networkAddress, mask);
        this.connections = new ArrayList<>();
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
        cable.linkDevice(device1);
        cable.linkDevice(device2);
        this.connections.add(cable);
    }

    public SubnetMask getMask() {
        return mask;
    }

    public void setMask(SubnetMask mask) {
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

    public List<Link> getConnections() {
        return connections;
    }

    public void setConnections(List<Link> connections) {
        this.connections = connections;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}