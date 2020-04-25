package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.TopologyTable;

public class UpdateMessage extends RTPMessage implements Message {
    private TopologyTable topologyTable;

    public UpdateMessage(IPAddress sender, IPAddress receiver, TopologyTable topologyTable){
        super(sender, receiver);
        this.topologyTable = topologyTable;
    }

    public TopologyTable getTopologyTable() {
        return topologyTable;
    }
}
