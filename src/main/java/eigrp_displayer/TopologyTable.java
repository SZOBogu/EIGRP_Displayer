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

    //TODO:test
    public List<RoutingTableEntry> getAllEntriesForIP(IPAddress targetAddress){
        List<RoutingTableEntry> suitableEntries = new ArrayList<>();

        for(RoutingTableEntry entry : this.getEntries()){
            if(entry.getIp_address().equals(targetAddress)){
                suitableEntries.add(entry);
            }
        }
        return suitableEntries;
    }

    //TODO: test
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

    //TODO: test
    public List<RoutingTableEntry> getSuccessorEntriesForIP(IPAddress targetAddress) {
        List<RoutingTableEntry> suitableEntries = this.getAllEntriesForIP(targetAddress);
        RoutingTableEntry bestEntry = this.getBestEntryForIP(targetAddress);

        suitableEntries.remove(bestEntry);
        return suitableEntries;
    }

    //TODO: test
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
        //check if present
        List<IPAddress> ips = new ArrayList<>();
        long metricForConnectionWithSender = Long.MAX_VALUE;
        MetricCalculator calculator = new MetricCalculator();
        Connection connection = new Cable();

        for (DeviceInterface deviceInterface : routerController.getDevice().getDeviceInterfaces()) {
            connection = deviceInterface.getConnection();
            if (connection.getOtherDevice(routerController).getDevice().getIp_address().equals(sender)) {
                metricForConnectionWithSender = calculator.calculateMetric(
                        routerController.getDevice(), connection);
                break;
            }
        }

        for (RoutingTableEntry entry : this.getEntries()) {
            ips.add(entry.getIp_address());
        }
        //update
        if(ips.contains(receivedRoutingTableEntry.getIp_address())) {
            for (RoutingTableEntry entry : this.getEntries()) {
                if (entry.getIp_address() == receivedRoutingTableEntry.getIp_address()) {
                    //if received route is better then replace it
                    if (metricForConnectionWithSender -
                            receivedRoutingTableEntry.getFeasibleDistance() <
                            entry.getFeasibleDistance()) {

                        this.getEntries().remove(entry);
                        RoutingTableEntry updatedRoutingTableEntry =
                                new RoutingTableEntry(receivedRoutingTableEntry.getIp_address());

                        updatedRoutingTableEntry.setFeasibleDistance(
                                receivedRoutingTableEntry.getFeasibleDistance() + metricForConnectionWithSender);

                        updatedRoutingTableEntry.setReportedDistance(
                                receivedRoutingTableEntry.getFeasibleDistance());
                        this.getEntries().add(updatedRoutingTableEntry);
                        List<Connection> updatedPath = entry.getPath();
                        updatedPath.add(0, connection);
                        updatedRoutingTableEntry.setPath(updatedPath);
                    }
                }
            }
        }
        else {
            //create new entry
            RoutingTableEntry newEntry = new RoutingTableEntry(receivedRoutingTableEntry.getIp_address());
            newEntry.setFeasibleDistance(receivedRoutingTableEntry.getFeasibleDistance()
                    + metricForConnectionWithSender);

            //TODO: make builder out of it
            RoutingTableEntry updatedRoutingTableEntry =
                    new RoutingTableEntry(receivedRoutingTableEntry.getIp_address());

            updatedRoutingTableEntry.setFeasibleDistance(
                    receivedRoutingTableEntry.getFeasibleDistance() + metricForConnectionWithSender);

            updatedRoutingTableEntry.setReportedDistance(receivedRoutingTableEntry.getFeasibleDistance());

            this.getEntries().add(updatedRoutingTableEntry);
            List<Connection> updatedPath = receivedRoutingTableEntry.getPath();
            updatedPath.add(0, connection);
            updatedRoutingTableEntry.setPath(updatedPath);
        }
    }
}