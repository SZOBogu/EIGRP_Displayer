package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.RoutingTable;

public class UpdateMessage extends RTPMessage implements Message {
    private RoutingTable routingTable;

    public UpdateMessage(IPAddress sender, IPAddress receiver, RoutingTable routingTable){
        super(sender, receiver);
        this.routingTable = routingTable;
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }
}
