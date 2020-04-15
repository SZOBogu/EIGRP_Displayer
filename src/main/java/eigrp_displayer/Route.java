package eigrp_displayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {
    private IPAddress targetIPAddress;
    private int feasibleDistance;
    private int reportedDistance;
    private String connectionType; //TODO: replace by interface somehow
    private List<Link> paths = new ArrayList<>();

    public IPAddress getTargetIPAddress() {
        return targetIPAddress;
    }

    public void setTargetIPAddress(IPAddress targetIPAddress) {
        this.targetIPAddress = targetIPAddress;
    }

    public int getFeasibleDistance() {
        return feasibleDistance;
    }

    public void setFeasibleDistance(int feasibleDistance) {
        this.feasibleDistance = feasibleDistance;
    }

    public int getReportedDistance() {
        return reportedDistance;
    }

    public void setReportedDistance(int reportedDistance) {
        this.reportedDistance = reportedDistance;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public List<Link> getPaths() {
        return paths;
    }

    public int getLowestBandwidth(){
        List<Integer> bandwidths = new ArrayList<>();
        for(Link link : this.paths){
            bandwidths.add(link.getBandwidth());
        }
        Collections.sort(bandwidths);
        return (int)Math.pow(10,7)/bandwidths.get(0);
    }

    public int getSumOfDelays(){
        int sum = 0;
        for(Link link : this.paths){
            sum += link.getDelay();
        }
        return sum;
    }

    public int getWorstLoad() {
        List<Integer> loads = new ArrayList<>();
        for(Link link : this.paths){
            loads.add(link.getLoad());
        }
        Collections.sort(loads);
        return loads.get(0);
    }

    public int getWorstReliability() {
        List<Integer> reliabilities = new ArrayList<>();
        for(Link link : this.paths){
            reliabilities.add(link.getReliability());
        }
        Collections.sort(reliabilities);
        return reliabilities.get(0);
    }      //lowest
}
