package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopologyTableTest {
    TopologyTable topologyTable = new TopologyTable();

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
    }

    @Test
    void getBestEntryForIP() {
    }

    @Test
    void getSuccessorEntriesForIP() {
    }

    @Test
    void getSuccessorCount() {
    }
}