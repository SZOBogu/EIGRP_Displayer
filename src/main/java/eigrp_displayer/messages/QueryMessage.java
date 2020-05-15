package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class QueryMessage extends Message implements Sendable {
    private final IPAddress queriedDeviceAddress;

    public QueryMessage(IPAddress sender, IPAddress receiver, IPAddress queriedDeviceAddress){
        super(sender, sender, receiver, receiver);
        this.queriedDeviceAddress = queriedDeviceAddress;
    }

    public QueryMessage(IPAddress source, IPAddress sender, IPAddress receiver, IPAddress target, IPAddress queriedDeviceAddress){
        super(source, sender, receiver, target);
        this.queriedDeviceAddress = queriedDeviceAddress;
    }

    public IPAddress getQueriedDeviceAddress() {
        return queriedDeviceAddress;
    }
}
