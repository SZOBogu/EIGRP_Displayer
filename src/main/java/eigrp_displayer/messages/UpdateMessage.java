package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.TopologyTable;

public class UpdateMessage extends Message implements Sendable {
    private TopologyTable topologyTable;

    public UpdateMessage(IPAddress sender, IPAddress receiver, TopologyTable topologyTable){
        super(sender, receiver, receiver);
        this.topologyTable = topologyTable;
    }

    public UpdateMessage(IPAddress sender, IPAddress receiver, IPAddress target, TopologyTable topologyTable){
        super(sender, receiver, target);
        this.topologyTable = topologyTable;
    }

    public TopologyTable getTopologyTable() {
        return topologyTable;
    }
}
