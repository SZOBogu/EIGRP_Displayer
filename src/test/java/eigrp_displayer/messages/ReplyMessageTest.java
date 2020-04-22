package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.RoutingTableEntry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ReplyMessageTest {
    IPAddress sender = Mockito.mock(IPAddress.class);
    IPAddress receiver = Mockito.mock(IPAddress.class);
    RoutingTableEntry entry = Mockito.mock(RoutingTableEntry.class);

    ReplyMessage reply = new ReplyMessage(sender, receiver, entry);

    @Test
    void getRoutingTableEntry() {
        assertEquals(entry, reply.getRoutingTableEntry());
    }
}