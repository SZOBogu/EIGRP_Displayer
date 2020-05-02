package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NeighbourTable implements ClockDependent{
    private String description;
    private List<NeighbourTableEntry> entries;

    public NeighbourTable(){
        this.description = "IP-EIGRP neighbours table for process 1";
        this.entries = new ArrayList<>();
        Clock.addClockDependant(this);
    }

    public String getDescription() {
        return description;
    }

    public List<NeighbourTableEntry> getEntries() {
        return entries;
    }

    //TODO: test
    public NeighbourTableEntry getEntry(IPAddress ip) {
        for(NeighbourTableEntry entry : this.entries){
            if(entry.getNeighbourAddress().equals(ip))
                return entry;
        }
        return null;
    }

    public void formNeighbourship(IPAddress ipAddress){
        NeighbourTableEntry entry = new NeighbourTableEntry(ipAddress);
        this.entries.add(entry);
    }

    public void removeNeighbourship(IPAddress ipAddress){
        this.entries.removeIf(entry -> entry.getNeighbourAddress().equals(ipAddress));
    }

    public boolean checkIfPresent(IPAddress ip){
        for(NeighbourTableEntry entry : this.entries){
            if(entry.getNeighbourAddress().equals(ip))
                return true;
        }
        return false;
    }

    public List<IPAddress> getAllNeighboursAddresses(){
        List<IPAddress> ips = new ArrayList<>();
        for(NeighbourTableEntry entry : this.entries){
            ips.add(entry.getNeighbourAddress());
        }
        return ips;
    }

    @Override
    public void updateTime() {
        for(NeighbourTableEntry entry : this.entries){
            entry.setTicksSinceLastHello(entry.getTicksSinceLastHello() + 1);
            if(entry.getTicksSinceLastHello() > entry.getHold()){
                this.entries.remove(entry);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(description + '\n' +
                "H\tAddress\tInterface\tHold\tUptime\tSRTT\tRTO\tQ Cnt\tSeq Num\n");
        for(int i = 0; i < this.entries.size(); i++){
            string.append(i).append("\t").append(this.entries.get(i).toString()).append("\n");
        }
        return string.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeighbourTable that = (NeighbourTable) o;
        return Objects.equals(getEntries(), that.getEntries());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntries());
    }
}
