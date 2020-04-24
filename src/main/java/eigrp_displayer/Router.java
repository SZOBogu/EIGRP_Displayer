package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Router extends Device{
    private RoutingTable routingTable;
    private NeighbourTable neighbourTable;
    private TopologyTable topologyTable;
    private boolean k1;
    private boolean k2;
    private boolean k3;
    private boolean k4;
    private boolean k5;
    private final int messageSendingTimeOffset;

    public Router(String name){
        this(name, 4);
    }

    public Router(String name, int numberOfInterfaces){
        super(numberOfInterfaces);
        this.setName(name);
        this.setIp_address(null);
        this.k1 = true;
        this.k2 = false;
        this.k3 = true;
        this.k4 = false;
        this.k5 = false;
        this.messageSendingTimeOffset = new Random().nextInt(60);
        this.routingTable = new RoutingTable();
        this.neighbourTable = new NeighbourTable();
        this.topologyTable = new TopologyTable();
    }

    public boolean isK1() {
        return k1;
    }

    public void setK1(boolean k1) {
        this.k1 = k1;
    }

    public boolean isK2() {
        return k2;
    }

    public void setK2(boolean k2) {
        this.k2 = k2;
    }

    public boolean isK3() {
        return k3;
    }

    public void setK3(boolean k3) {
        this.k3 = k3;
    }

    public boolean isK4() {
        return k4;
    }

    public void setK4(boolean k4) {
        this.k4 = k4;
    }

    public boolean isK5() {
        return k5;
    }

    public void setK5(boolean k5) {
        this.k5 = k5;
    }

    public int getMessageSendingTimeOffset() {
        return messageSendingTimeOffset;
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    public NeighbourTable getNeighbourTable() {
        return neighbourTable;
    }

    public RoutingTable getTopologyTable() {
        return topologyTable;
    }

    public List<Device> getAllNeighbours(){
        List<Device> devices = new ArrayList<>();
        List<IPAddress> ips = this.neighbourTable.getAllNeighboursAddresses();
        try {
            for (DeviceInterface deviceInterface : this.getDeviceInterfaces()) {
                Device device = deviceInterface.getConnection().getOtherDevice(this);
                if (device != null && ips.contains(device.getIp_address())) {
                    devices.add(device);
                }
            }
            return devices;
        }
        catch (Exception e){
            return devices;
        }
    }

    public List<Device> getAllNeighboursButOne(IPAddress ipAddress){
        List<Device> devices = this.getAllNeighbours();
        devices.removeIf(device -> device.getIp_address().equals(ipAddress));
        return devices;
    }

    //TODO: tests
    public void update(RoutingTableEntry entry, IPAddress senderAddress){
        this.topologyTable.update(this, entry, senderAddress);
        RoutingTableEntry bestEntry = this.topologyTable.getBestPath(senderAddress);
        this.routingTable.update(bestEntry);
    }

    public void update(RoutingTable table, IPAddress senderAddress){
        for(RoutingTableEntry entry : table.getEntries()){
            this.update( entry, senderAddress);
        }
    }
}