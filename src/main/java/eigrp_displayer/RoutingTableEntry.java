package eigrp_displayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutingTableEntry {
    private String code;
    private IPAddress ip_address;
    private int successors;
    private long feasibleDistance;
    private long reportedDistance;
    private int ticksSinceLastHelloMessage = 0;
    private List<Connection> path;

    //TODO: tests
    public RoutingTableEntry(IPAddress ip_address){
        this("P", ip_address);
    }

    public RoutingTableEntry(String code, IPAddress ip_address){
        this.code = code;
        this.ip_address = ip_address;
        this.successors = 0;
        this.feasibleDistance = Long.MAX_VALUE;
        this.reportedDistance = Long.MAX_VALUE;
        this.path = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public IPAddress getIp_address() {
        return ip_address;
    }

    public void setIp_address(IPAddress ip_address) {
        this.ip_address = ip_address;
    }

    public long getFeasibleDistance() {
        return feasibleDistance;
    }

    public void setFeasibleDistance(long feasibleDistance) {
        this.feasibleDistance = feasibleDistance;
    }

    public long getReportedDistance() {
        return reportedDistance;
    }

    public void setReportedDistance(long reportedDistance) {
        this.reportedDistance = reportedDistance;
    }

    public int getSuccessors() {
        return successors;
    }

    public void setSuccessors(int successors) {
        this.successors = successors;
    }

    public int getTicksSinceLastHelloMessage() {
        return this.ticksSinceLastHelloMessage;
    }

    public void incrementTicks(int ticks) {
        this.ticksSinceLastHelloMessage += ticks;
    }

    public void resetTicks(){
        this.ticksSinceLastHelloMessage = 0;
    }

    public List<Connection> getPath() {
        return path;
    }

    public void setPath(List<Connection> path) {
        this.path = path;
    }

    public int getLowestBandwidth(){
        List<Integer> bandwidths = new ArrayList<>();
        for(Connection connection : this.path){
            bandwidths.add(connection.getBandwidth());
        }
        Collections.sort(bandwidths);
        return (int)Math.pow(10,7)/bandwidths.get(0);
    }

    public int getSumOfDelays(){
        int sum = 0;
        for(Connection connection : this.path){
            sum += connection.getDelay();
        }
        return sum;
    }

    public int getWorstLoad() {
        List<Integer> loads = new ArrayList<>();
        for(Connection connection : this.path){
            loads.add(connection.getLoad());
        }
        Collections.sort(loads);
        return loads.get(0);
    }

    public int getWorstReliability() {
        List<Integer> reliabilities = new ArrayList<>();
        for(Connection connection : this.path){
            reliabilities.add(connection.getReliability());
        }
        Collections.sort(reliabilities);
        return reliabilities.get(0);
    }

    public List<Device> getDevicePath(Router router){
        List<Device> devicePath = new ArrayList<>();
        List<IPAddress> usedIPs = new ArrayList<>();

        devicePath.add(router);
        usedIPs.add(router.getIp_address());

        for(int i = 0 ; i < this.path.size() ; i++){
            Connection connection = this.path.get(i);
            usedIPs.add(connection.getOtherDevice(devicePath.get(i)).getIp_address());
            devicePath.add(connection.getOtherDevice(devicePath.get(i)));
        }

        return devicePath;
    }

    public List<IPAddress> getIPAddressPath(Router router){
        List<Device> devices = this.getDevicePath(router);
        List<IPAddress> ips = new ArrayList<>();

        for(Device device : devices){
            ips.add(device.getIp_address());
        }
        return ips;
    }

    public String getStringPath(Router router){
        List<IPAddress> ips = this.getIPAddressPath(router);
        String string = "";
        for(int i = 1 ; i < ips.size(); i++){
            string += "via " + ips.get(i).toString();
        }
        return "dupa";
    }

}
