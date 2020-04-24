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

    public RoutingTableEntry getBestPath(IPAddress targetAddress) {return null;}

    //TODO:test
    public void update(Router router, RoutingTable receivedRoutingTable, IPAddress sender){
        for(RoutingTableEntry entry : receivedRoutingTable.getEntries()){
            this.update(router, entry, sender);
        }
    }

    //TODO:test
    public void update(Router router, RoutingTableEntry receivedRoutingTableEntry, IPAddress sender) {
        //check if present
        List<IPAddress> ips = new ArrayList<>();
        long metricForConnectionWithSender = Long.MAX_VALUE;
        MetricCalculator calculator = new MetricCalculator();
        Connection connection = new Cable();

        for (DeviceInterface deviceInterface : router.getDeviceInterfaces()) {
            connection = deviceInterface.getConnection();
            if (connection.getOtherDevice(router).getIp_address().equals(sender)) {
                metricForConnectionWithSender = calculator.calculateMetric(
                        router, connection);
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
            newEntry.setFeasibleDistance(receivedRoutingTableEntry.getFeasibleDistance() + metricForConnectionWithSender);

            //TODO: make method out of it
            RoutingTableEntry updatedRoutingTableEntry =
                    new RoutingTableEntry(receivedRoutingTableEntry.getIp_address());
            updatedRoutingTableEntry.setFeasibleDistance(
                    receivedRoutingTableEntry.getFeasibleDistance() + metricForConnectionWithSender);
            this.getEntries().add(updatedRoutingTableEntry);
            List<Connection> updatedPath = receivedRoutingTableEntry.getPath();
            updatedPath.add(0, connection);
            updatedRoutingTableEntry.setPath(updatedPath);
        }
    }
    
}
