package eigrp_displayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {
    private IPAddress targetIPAddress;
    private int feasibleDistance;
    private int reportedDistance;
    private String connectionType; //TODO: replace by interface somehow
    private List<Connection> paths = new ArrayList<>();

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

    public List<Connection> getPaths() {
        return paths;
    }

    public int getLowestBandwidth(){
        List<Integer> bandwidths = new ArrayList<>();
        for(Connection connection : this.paths){
            bandwidths.add(connection.getBandwidth());
        }
        Collections.sort(bandwidths);
        return (int)Math.pow(10,7)/bandwidths.get(0);
    }

    public int getSumOfDelays(){
        int sum = 0;
        for(Connection connection : this.paths){
            sum += connection.getDelay();
        }
        return sum;
    }

    public int getWorstLoad() {
        List<Integer> loads = new ArrayList<>();
        for(Connection connection : this.paths){
            loads.add(connection.getLoad());
        }
        Collections.sort(loads);
        return loads.get(0);
    }

    public int getWorstReliability() {
        List<Integer> reliabilities = new ArrayList<>();
        for(Connection connection : this.paths){
            reliabilities.add(connection.getReliability());
        }
        Collections.sort(reliabilities);
        return reliabilities.get(0);
    }      //lowest
}
