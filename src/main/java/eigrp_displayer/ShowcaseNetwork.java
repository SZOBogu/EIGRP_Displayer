package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public class ShowcaseNetwork {
    private IPAddress networkAddress;
    private IPAddress broadcastAddress;
    private Mask mask;
    private PoolOfIPAddresses pool;
    private List<Connection> connections;
    private List<Device> devices;

    public ShowcaseNetwork(IPAddress networkAddress, IPAddress broadcastAddress, Mask mask){
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

    public Device getDevice(IPAddress ipAddress){
        for(Device device : this.devices){
            if(device.getIp_address().equals(ipAddress))
                return device;
        }
        return null;
    }

    public List<Device> getNeighboursOf(Device device){
        List<Device> neighbours = new ArrayList<>();

        for(Connection connection : this.connections){
            if(connection.getDevice1() == device)
                neighbours.add(connection.getDevice2());
            if(connection.getDevice2() == device)
                neighbours.add(connection.getDevice1());
        }

        return neighbours;
    }

    public boolean checkIfConnected(Device device1, Device device2) {
        List<Device> device1Neighbours = this.getNeighboursOf(device1);
        return device1Neighbours.contains(device2);
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

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}