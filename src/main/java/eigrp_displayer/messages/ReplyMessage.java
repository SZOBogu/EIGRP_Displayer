package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.RoutingTableEntry;

public class ReplyMessage extends RTPMessage implements Message{
    private RoutingTableEntry routingTableEntry;

    public ReplyMessage(IPAddress sender, IPAddress receiver, RoutingTableEntry routingTableEntry){
        super(sender, receiver);
        this.routingTableEntry = routingTableEntry;
    }

    public ReplyMessage(ReplyMessage replyMessage){
        this(replyMessage.getSenderAddress(), replyMessage.getReceiverAddress(),
                replyMessage.getRoutingTableEntry());
    }

    public RoutingTableEntry getRoutingTableEntry() {
        return routingTableEntry;
    }
}
