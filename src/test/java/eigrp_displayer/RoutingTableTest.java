package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RoutingTableTest {
    RoutingTable routingTable = new RoutingTable();
    IPAddress ip0 = Mockito.mock(IPAddress.class);
    IPAddress ip1 = Mockito.mock(IPAddress.class);
    RoutingTableEntry entry0 = new RoutingTableEntry(ip0);
    RoutingTableEntry entry1 = new RoutingTableEntry(ip1);

    ArrayList<RoutingTableEntry> entryList = new ArrayList<>(Arrays.asList(entry0, entry1));

    @Test
    void getEntries() {
        assertEquals(new ArrayList<>(), routingTable.getEntries());
    }

    @Test
    void setEntries() {
        routingTable.setEntries(entryList);
        assertEquals(entryList, routingTable.getEntries());
    }

    @Test
    void getEntry() {
        routingTable.setEntries(entryList);
        assertEquals(entry0, routingTable.getEntry(ip0));
        assertNull(routingTable.getEntry(Mockito.mock(IPAddress.class)));
    }

    @Test
    void updateEntry() {
        RoutingTableEntry betterEntry = new RoutingTableEntry(ip0);
        RoutingTableEntry worseEntry = new RoutingTableEntry(ip0);
        betterEntry.setFeasibleDistance(100);
        worseEntry.setFeasibleDistance(10000);

        routingTable.update(betterEntry);
        routingTable.update(worseEntry);

        assertEquals(betterEntry, routingTable.getEntry(ip0));
    }
}