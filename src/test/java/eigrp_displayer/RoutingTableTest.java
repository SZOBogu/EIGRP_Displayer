package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoutingTableTest {
    RoutingTable routingTable = new RoutingTable();

    RoutingTableEntry entry = Mockito.mock(RoutingTableEntry.class);
    RoutingTableEntry entry0 = Mockito.mock(RoutingTableEntry.class);
    RoutingTableEntry entry1 = Mockito.mock(RoutingTableEntry.class);

    ArrayList<RoutingTableEntry> entryList = new ArrayList<>(Arrays.asList(entry, entry0));
    ArrayList<RoutingTableEntry> entryList1 = new ArrayList<>(Arrays.asList(entry1, entry0));

    @BeforeEach
    void setup(){
        routingTable.setEntries(entryList);
    }

    @Test
    void getEntries() {
        assertEquals(entryList, routingTable.getEntries());
    }

    @Test
    void setEntries() {
        routingTable.setEntries(entryList1);
        assertEquals(entryList1, routingTable.getEntries());
    }

    @Test
    void update() {

    }
}