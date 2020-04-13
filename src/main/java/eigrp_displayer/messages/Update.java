package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.RoutingTable;

public class Update extends RTPMessage implements Message {
    private RoutingTable routingTable;

    public Update(IPAddress sender, IPAddress receiver, RoutingTable routingTable){
        super(sender, receiver);
        this.routingTable = routingTable;
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }
}
