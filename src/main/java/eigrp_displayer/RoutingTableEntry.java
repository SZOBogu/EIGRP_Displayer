package eigrp_displayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutingTableEntry {
    private String code;
    private IPAddress ip_address;
    private int successors;
    private long feasibleDistance;
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

    public int getSuccessors() {
        return successors;
    }

    public long getFeasibleDistance() {
        return feasibleDistance;
    }

    public void setFeasibleDistance(long feasibleDistance) {
        this.feasibleDistance = feasibleDistance;
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
}
