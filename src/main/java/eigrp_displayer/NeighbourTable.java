package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NeighbourTable {
    private String description;
    private List<NeighbourTableEntry> entries;
    
    public NeighbourTable(){
        this.description = "IP-EIGRP neighbours table for process 1";
        this.entries = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public List<NeighbourTableEntry> getEntries() {
        return entries;
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
