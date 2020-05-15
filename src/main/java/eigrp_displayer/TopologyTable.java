package eigrp_displayer;

import eigrp_displayer.messages.UpdateMessage;

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

    public RoutingTableEntry getFeasibleSuccessorEntry(IPAddress ip){
        long lowestDistance = Long.MAX_VALUE;
        RoutingTableEntry bestEntry = null;
        List<RoutingTableEntry> allEntries = getAllEntriesForIP(ip);
        allEntries.remove(this.getBestEntryForIP(ip));

        for(RoutingTableEntry entry : allEntries){
            if(entry.getFeasibleDistance() < lowestDistance){
                bestEntry = entry;
                lowestDistance = entry.getFeasibleDistance();
            }
        }
        return bestEntry;
    }

    public List<RoutingTableEntry> getSuccessorEntriesForIP(IPAddress targetAddress) {
        List<RoutingTableEntry> suitableEntries = this.getAllEntriesForIP(targetAddress);
        suitableEntries.removeIf(entry -> entry.getFeasibleDistance() == Long.MAX_VALUE);
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

    public void update(RouterController controller, UpdateMessage updateMessage){
        for(RoutingTableEntry entry : updateMessage.getTopologyTable().getEntries()){
            this.update(controller, entry, updateMessage.getSenderAddress());
        }
    }

    public void update(RouterController routerController, RoutingTableEntry receivedRoutingTableEntry,
                       IPAddress sender) {
        //czemu nie update message zamiast entry i ipsendera?
        //metryka z kabla od sendera
        //sprawdzic czy po dodaniu kabla na poczatek (tak jak w buildzie z dolu) daje ta sama sciezke co istniejaca
        //jesli tak to nowa metryka
        //jesli nie to to tworzymy nowy rekord zgodnie z builderem

        long metricOnCableFromSender = Long.MAX_VALUE;
        Connection connection = null;

        for(DeviceInterface deviceInterface : routerController.getDevice().getDeviceInterfaces()){
            if(deviceInterface.getConnection() != null && deviceInterface.getConnection().getOtherDevice(routerController).getDevice().getIp_address().equals(sender)){
                connection = deviceInterface.getConnection();
                metricOnCableFromSender = MetricCalculator.calculateMetric(routerController.getDevice(), connection);
            }
        }

        if(connection != null && metricOnCableFromSender != Long.MAX_VALUE) {
            List<Connection> targetPath = new ArrayList<>();
            targetPath.add(connection);
            targetPath.addAll(receivedRoutingTableEntry.getPath());

            RoutingTableEntry entryWithGoodPath = null;

            for(RoutingTableEntry entry : this.getEntries()){
                if(entry.getPath().equals(targetPath)){
                    entryWithGoodPath = entry;
                }
            }

            if(entryWithGoodPath != null){
                entryWithGoodPath.setFeasibleDistance(receivedRoutingTableEntry.getFeasibleDistance() + metricOnCableFromSender);
                entryWithGoodPath.setReportedDistance(receivedRoutingTableEntry.getFeasibleDistance());
            }
            else {
                RoutingTableEntry newEntry = new RoutingTableEntry(receivedRoutingTableEntry.getIp_address());
                newEntry.setFeasibleDistance(receivedRoutingTableEntry.getFeasibleDistance() + metricOnCableFromSender);
                newEntry.setReportedDistance(receivedRoutingTableEntry.getFeasibleDistance());
                List<Connection> path = new ArrayList<>();
                path.add(connection);
                path.addAll(receivedRoutingTableEntry.getPath());
                newEntry.setPath(path);

                if(path.size() <= 5){
                    this.getEntries().add(newEntry);
                }
            }
        }
    }

    public void deleteNeighbourEntries(DeviceController controller, IPAddress ipToDelete) {
        List<RoutingTableEntry> entriesToDelete = new ArrayList<>();
        for(RoutingTableEntry entry : this.getEntries()){
            List<Connection> path = entry.getPath();
            Connection connection = path.get(0);
            DeviceController controller1 = connection.getOtherDevice(controller);
            Device device = controller1.getDevice();
            IPAddress ip = device.getIp_address();
            if(ip.equals(ipToDelete))
                entriesToDelete.add(entry);
        }
        this.getEntries().removeAll(entriesToDelete);
    }
}