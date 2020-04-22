package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class QueryMessage extends RTPMessage implements Message {
    private final IPAddress queriedDeviceAddress;

    public QueryMessage(IPAddress sender, IPAddress receiver, IPAddress queriedDeviceAddress){
        super(sender, receiver);
        this.queriedDeviceAddress = queriedDeviceAddress;
    }

    public QueryMessage(QueryMessage queryMessage){
        this(queryMessage.getSenderAddress(), queryMessage.getReceiverAddress(),
                queryMessage.queriedDeviceAddress);
    }

    public IPAddress getQueriedDeviceAddress() {
        return queriedDeviceAddress;
    }
}
