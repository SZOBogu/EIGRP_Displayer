package eigrp_displayer;

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

    public List<Route> getBestPath() {return null;}

}
