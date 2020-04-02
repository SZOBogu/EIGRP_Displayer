package eigrp_displayer;

public class Router extends Device{
    private RoutingTable routingTable;

    public Router(String name){
        super();
        this.setName(name);
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(RoutingTable routingTable) {
        this.routingTable = routingTable;
    }
}