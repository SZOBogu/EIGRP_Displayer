package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoutingTableTest {
    RoutingTable routingTable = new RoutingTable();
    RoutingTable neighbourTable = new RoutingTable(
            "This is neighbour table", "P - passive");

    RoutingTableEntry entry = Mockito.mock(RoutingTableEntry.class);
    RoutingTableEntry entry0 = Mockito.mock(RoutingTableEntry.class);
    RoutingTableEntry entry1 = Mockito.mock(RoutingTableEntry.class);
    RoutingTableEntry entry2 = Mockito.mock(RoutingTableEntry.class);

    ArrayList<RoutingTableEntry> entryList = new ArrayList<>(Arrays.asList(entry, entry0));
    ArrayList<RoutingTableEntry> entryList0 = new ArrayList<>(Arrays.asList(entry1, entry2));

    ArrayList<RoutingTableEntry> entryList1 = new ArrayList<>(Arrays.asList(entry2, entry0));
    ArrayList<RoutingTableEntry> entryList2 = new ArrayList<>(Arrays.asList(entry1, entry));

    @BeforeEach
    void setup(){
        routingTable.setEntries(entryList);
        neighbourTable.setEntries(entryList0);
    }

    @Test
    void getDescription() {
        assertEquals("placeholder", routingTable.getDescription());
        assertEquals("This is neighbour table", neighbourTable.getDescription());
    }

    @Test
    void setDescription() {
        routingTable.setDescription("A Table");
        neighbourTable.setDescription("B Table");
        assertEquals("A Table", routingTable.getDescription());
        assertEquals("B Table", neighbourTable.getDescription());
    }

    @Test
    void getCodes() {
        assertEquals("Codes: P - Passive, A - Active, U - Update, Q - Query, R - Reply, r - Reply status",
                routingTable.getCodes());
        assertEquals("P - passive", neighbourTable.getCodes());
    }

    @Test
    void setCodes() {
        routingTable.setCodes("D - dummy");
        neighbourTable.setCodes("T - test");
        assertEquals("D - dummy", routingTable.getCodes());
        assertEquals("T - test", neighbourTable.getCodes());
    }

    @Test
    void getEntries() {
        assertEquals(entryList, routingTable.getEntries());
        assertEquals(entryList0, neighbourTable.getEntries());
    }

    @Test
    void setEntries() {
        routingTable.setEntries(entryList1);
        neighbourTable.setEntries(entryList2);
        assertEquals(entryList1, routingTable.getEntries());
        assertEquals(entryList2, neighbourTable.getEntries());
    }

    @Test
    void update() {
    }
}