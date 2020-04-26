package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TopologyTableTest {
    TopologyTable topologyTable = new TopologyTable();
    IPAddress ip0 = Mockito.mock(IPAddress.class);
    IPAddress ip1 = Mockito.mock(IPAddress.class);
    IPAddress ip2 = Mockito.mock(IPAddress.class);
    RoutingTableEntry entry0 = new RoutingTableEntry(ip0);
    RoutingTableEntry entry1 = new RoutingTableEntry(ip1);
    RoutingTableEntry entry2 = new RoutingTableEntry(ip2);
    RoutingTableEntry entry3 = new RoutingTableEntry(ip0);

    @BeforeEach
    void init(){
        topologyTable.getEntries().add(entry0);
        topologyTable.getEntries().add(entry1);
        topologyTable.getEntries().add(entry2);
        topologyTable.getEntries().add(entry3);
    }

    @Test
    void getDescription() {
        assertEquals("IP-EIGRP Topology Table for AS 1", topologyTable.getDescription());
    }

    @Test
    void setDescription() {
        topologyTable.setDescription("A Table");
        assertEquals("A Table", topologyTable.getDescription());
    }

    @Test
    void getCodes() {
        String expected = "Codes: P - Passive, A - Active, U - Update, Q - Query, R - Reply, r - Reply status";
        assertEquals(expected, topologyTable.getCodes());
    }

    @Test
    void setCodes() {
        topologyTable.setCodes("D - dummy");
        assertEquals("D - dummy", topologyTable.getCodes());
    }


    @Test
    void updateTable() {
    }

    @Test
    void updateEntry() {
    }

    @Test
    void getAllEntriesForIP() {
        List<RoutingTableEntry> entryList = topologyTable.getAllEntriesForIP(ip0);
        assertEquals(2, entryList.size());
        assertEquals(entry0, entryList.get(0));
        assertEquals(entry3, entryList.get(1));
    }

    @Test
    void getBestEntryForIP() {
        entry0.setFeasibleDistance(102942);
        entry3.setFeasibleDistance(1029422);
        assertEquals(entry0, topologyTable.getBestEntryForIP(ip0));
        entry0.setFeasibleDistance(102942);
        entry3.setFeasibleDistance(1029);
        assertEquals(entry3, topologyTable.getBestEntryForIP(ip0));
    }

    @Test
    void getSuccessorEntriesForIP() {
    }

    @Test
    void getSuccessorCount() {
    }
}