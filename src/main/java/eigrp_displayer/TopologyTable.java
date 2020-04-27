package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public class TopologyTable extends RoutingTable{
    private String description;
    private String codes;

    public TopologyTable(){
        super();
        this.description = "IP-EIGRP Topology Table for AS 1";
        this.codes = "Codes: P - Passive, A - Active, U - Update, Q - Query, R - Reply, r - Reply status";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public List<RoutingTableEntry> getAllEntriesForIP(IPAddress targetAddress){
        List<RoutingTableEntry> suitableEntries = new ArrayList<>();

        for(RoutingTableEntry entry : this.getEntries()){
            if(entry.getIp_address().equals(targetAddress)){
                suitableEntries.add(entry);
            }
        }
        return suitableEntries;
    }

    public RoutingTableEntry getBestEntryForIP(IPAddress targetAddress) {
        long lowestDistance = Long.MAX_VALUE;
        RoutingTableEntry bestEntry = null;
        List<RoutingTableEntry> suitableEntries = this.getAllEntriesForIP(targetAddress);

        for(RoutingTableEntry entry : suitableEntries){
            if(entry.getFeasibleDistance() < lowestDistance){
                bestEntry = entry;
                lowestDistance = entry.getFeasibleDistance();
            }
        }
        return bestEntry;
    }

    public List<RoutingTableEntry> getSuccessorEntriesForIP(IPAddress targetAddress) {
        List<RoutingTableEntry> suitableEntries = this.getAllEntriesForIP(targetAddress);
        RoutingTableEntry bestEntry = this.getBestEntryForIP(targetAddress);

        suitableEntries.remove(bestEntry);
        return suitableEntries;
    }

    public int getSuccessorCount(IPAddress ipAddress){
        int count = 0;
        for(RoutingTableEntry entry : this.getEntries()){
            if(entry.getIp_address().equals(ipAddress)){
                count++;
            }
        }
        if(count <= 0)
            return 0;
        else
            return count - 1;
    }

    //TODO:test
    public void update(RouterController controller, RoutingTable receivedRoutingTable, IPAddress sender){
        for(RoutingTableEntry entry : receivedRoutingTable.getEntries()){
            this.update(controller, entry, sender);
        }
    }

    //TODO:test
    public void update(RouterController routerController, RoutingTableEntry receivedRoutingTableEntry, IPAddress sender) {
        long metricForConnectionWithSender = Long.MAX_VALUE;
        MetricCalculator calculator = new MetricCalculator();
        Connection connection = new Cable();

        for (DeviceInterface deviceInterface : routerController.getDevice().getDeviceInterfaces()) {
            if (deviceInterface.getOtherDeviceController(routerController).getDevice().getIp_address().equals(sender)) {
                metricForConnectionWithSender = calculator.calculateMetric(
                        routerController.getDevice(), connection);
                break;
            }
        }

        RoutingTableEntry newEntry = new RoutingTableEntry(receivedRoutingTableEntry.getIp_address());
        newEntry.setFeasibleDistance(receivedRoutingTableEntry.getFeasibleDistance()
                    + metricForConnectionWithSender);

        buildRoutingTableEntry(receivedRoutingTableEntry, metricForConnectionWithSender,
                connection, receivedRoutingTableEntry.getPath());
    }

    private void buildRoutingTableEntry(RoutingTableEntry receivedRoutingTableEntry,
                                                long metricForConnectionWithSender, Connection connection,
                                                List<Connection> path) {
        RoutingTableEntry updatedRoutingTableEntry =
                new RoutingTableEntry(receivedRoutingTableEntry.getIp_address());

        updatedRoutingTableEntry.setFeasibleDistance(
                receivedRoutingTableEntry.getFeasibleDistance() + metricForConnectionWithSender);

        updatedRoutingTableEntry.setReportedDistance(receivedRoutingTableEntry.getFeasibleDistance());

        this.getEntries().add(updatedRoutingTableEntry);
        path.add(0, connection);
        updatedRoutingTableEntry.setPath(path);
    }
}