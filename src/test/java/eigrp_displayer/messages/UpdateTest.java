package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import eigrp_displayer.RoutingTable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateTest {
    IPAddress sender = Mockito.mock(IPAddress.class);
    IPAddress receiver = Mockito.mock(IPAddress.class);
    RoutingTable routingTable = Mockito.mock(RoutingTable.class);

    Update update = new Update(sender, receiver, routingTable);

    @Test
    void getRoutingTable() {
        assertEquals(routingTable, update.getRoutingTable());
    }
}