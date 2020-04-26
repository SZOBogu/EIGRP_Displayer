package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoutingTable {
    private List<RoutingTableEntry> entries;

    public RoutingTable(){
        this.entries = new ArrayList<>();
    }

    public List<RoutingTableEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<RoutingTableEntry> entries) {
        this.entries = entries;
    }

    public RoutingTableEntry getEntry(IPAddress ip) {
        for (RoutingTableEntry entry : this.entries) {
            if (entry.getIp_address().equals(ip))
                return entry;
        }
        return null;
    }

    public void update(RoutingTableEntry bestPathEntry){
        try {
            for (RoutingTableEntry entry : this.getEntries()) {
                if (entry.getIp_address().equals(bestPathEntry.getIp_address()) &&
                        bestPathEntry.getFeasibleDistance() < entry.getFeasibleDistance()) {
                    this.getEntries().remove(entry);
                    this.getEntries().add(bestPathEntry);
                }
            }
        }
        catch(Exception e){
            System.out.println("");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutingTable that = (RoutingTable) o;
        return Objects.equals(getEntries(), that.getEntries());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntries());
    }
}
