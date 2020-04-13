package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.RoutingTableEntry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ReplyTest {
    IPAddress sender = Mockito.mock(IPAddress.class);
    IPAddress receiver = Mockito.mock(IPAddress.class);
    RoutingTableEntry entry = Mockito.mock(RoutingTableEntry.class);

    Reply reply = new Reply(sender, receiver, entry);

    @Test
    void getRoutingTableEntry() {
        assertEquals(entry, reply.getRoutingTableEntry());
    }
}