package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class Query extends RTPMessage implements Message {
    private final IPAddress queriedDeviceAddress;

    public Query(IPAddress sender, IPAddress receiver, IPAddress queriedDeviceAddress){
        super(sender, receiver);
        this.queriedDeviceAddress = queriedDeviceAddress;
    }

    public IPAddress getQueriedDeviceAddress() {
        return queriedDeviceAddress;
    }
}
