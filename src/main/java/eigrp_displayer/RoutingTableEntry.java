package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public class RoutingTableEntry {
    private String code;
    private IPAddress ip_address;
    private int successors;
    private int shortestFeasibleDistance;
    private List<Route> routes;
    private int ticksSinceLastHelloMessage = 0;

    public RoutingTableEntry(String code, IPAddress ip_address){
        this.code = code;
        this.ip_address = ip_address;
        this.successors = 0;
        this.successors = Integer.MAX_VALUE;
        this.routes = new ArrayList<>();
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

    public void setSuccessors(int successors) {
        this.successors = successors;
    }

    public int getShortestFeasibleDistance() {
        return shortestFeasibleDistance;
    }

    public void setShortestFeasibleDistance(int shortestFeasibleDistance) {
        this.shortestFeasibleDistance = shortestFeasibleDistance;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    //TODO: tests
    public int getTicksSinceLastHelloMessage() {
        return this.ticksSinceLastHelloMessage;
    }

    public void incrementTicks(int ticks) {
        this.ticksSinceLastHelloMessage += ticks;
    }

    public void resetTicks(){
        this.ticksSinceLastHelloMessage = 0;
    }
}
