package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private IPAddress networkAddress;
    private IPAddress broadcastAddress;
    private Mask mask;
    private PoolOfIPAddresses pool;
    private List<DeviceController> deviceControllers;
    private List<Connection> connections;

    public Network(){
        this(
                new IPAddress(192,168,0,0),
                new IPAddress(192,168,0,255),
                new Mask(24)
                );
    }

    public Network(IPAddress networkAddress, IPAddress broadcastAddress, Mask mask){
        this.networkAddress = networkAddress;
        this.broadcastAddress = broadcastAddress;
        this.mask = mask;
        this.pool = new PoolOfIPAddresses();
        this.pool.init(networkAddress, mask);
        this.deviceControllers = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public void addDeviceController(DeviceController controller){
        this.deviceControllers.add(controller);
        controller.getDevice().setIp_address(this.pool.getIPAddress());
    }

    public void removeDeviceController(DeviceController controller){
        this.pool.releaseIPAddress(controller.getDevice().getIp_address());
        controller.getDevice().setIp_address(null);
        this.deviceControllers.remove(controller);
    }

    public void connectDevices(DeviceController deviceController1, DeviceController deviceController2){
        Cable cable = new Cable();
        deviceController1.setConnection(cable);
        deviceController2.setConnection(cable);
        cable.linkDevices(deviceController1, deviceController2);
        this.connections.add(cable);
    }

    public DeviceController getDeviceController(IPAddress ipAddress){
        for(DeviceController controller : this.deviceControllers){
            if(controller.getDevice().getIp_address().equals(ipAddress))
                return controller;
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

    public List<DeviceController> getDeviceControllers() {
        return deviceControllers;
    }

    public void setDeviceControllers(List<DeviceController> deviceControllers) {
        this.deviceControllers = deviceControllers;
    }

    public List<Connection> getConnections() {
        return connections;
    }
}