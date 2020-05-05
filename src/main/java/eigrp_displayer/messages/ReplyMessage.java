package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.RoutingTableEntry;

public class ReplyMessage extends Message implements Sendable {
    private RoutingTableEntry routingTableEntry;

    public ReplyMessage(IPAddress sender, IPAddress receiver, RoutingTableEntry routingTableEntry){
        super(sender, receiver, receiver);
        this.routingTableEntry = routingTableEntry;
    }

    public ReplyMessage(IPAddress sender, IPAddress receiver, IPAddress target, RoutingTableEntry routingTableEntry){
        super(sender, receiver, target);
        this.routingTableEntry = routingTableEntry;
    }

    public RoutingTableEntry getRoutingTableEntry() {
        return routingTableEntry;
    }
}
