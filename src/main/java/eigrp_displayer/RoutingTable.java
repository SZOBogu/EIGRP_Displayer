package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoutingTable {
    private String description;
    private String codes;
    private List<RoutingTableEntry> entries;

    public RoutingTable(){
        this.description = "placeholder";
        this.codes = "Codes: P - Passive, A - Active, U - Update, Q - Query, R - Reply, r - Reply status";
        this.entries = new ArrayList<>();
    }

    public RoutingTable(String description, String codes){
        this.description = description;
        this.codes = codes;
        this.entries = new ArrayList<>();
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

    public List<RoutingTableEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<RoutingTableEntry> entries) {
        this.entries = entries;
    }

    //TODO:implement
    public void update(RoutingTable receivedRoutingTable, IPAddress sender){}

    //TODO:implement
    public void update(RoutingTableEntry receivedRoutingTableEntry, IPAddress sender){}

    public List<Route> getBestPath() {return null;}

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
