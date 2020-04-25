package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.TopologyTable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateMessageTest {
    IPAddress sender = Mockito.mock(IPAddress.class);
    IPAddress receiver = Mockito.mock(IPAddress.class);
    TopologyTable topologyTable = Mockito.mock(TopologyTable.class);

    UpdateMessage update = new UpdateMessage(sender, receiver, topologyTable);

    @Test
    void getRoutingTable() {
        assertEquals(topologyTable, update.getTopologyTable());
    }
}