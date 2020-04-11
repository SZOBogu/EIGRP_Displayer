package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.RoutingTableEntry;

public class Reply extends RTPMessage implements Message{
    private RoutingTableEntry routingTableEntry;

    public Reply(IPAddress sender, IPAddress receiver, RoutingTableEntry routingTableEntry){
        super(sender, receiver);
        this.routingTableEntry = routingTableEntry;
    }

    public RoutingTableEntry getRoutingTableEntry() {
        return routingTableEntry;
    }
}
