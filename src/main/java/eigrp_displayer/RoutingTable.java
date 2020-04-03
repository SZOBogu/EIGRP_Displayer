package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public class RoutingTable {
    private String name;
    private String description;
    private String codes;
    private List<RoutingTableEntry> entries;

    public RoutingTable(String name){
        this.name = name;
        this.description = name + " placeholder";
        this.codes = "A - active ";
        this.entries = new ArrayList<>();
    }

    public RoutingTable(String name, String description, String codes){
        this.name = name;
        this.description = description;
        this.codes = codes;
        this.entries = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
