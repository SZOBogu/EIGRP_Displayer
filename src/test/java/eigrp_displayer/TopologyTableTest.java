package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopologyTableTest {
    Router router = new Router("R");
    EndDevice device = new EndDevice();
    RouterController controller = new RouterController(router);
    DeviceController deviceController = new DeviceController(device);
    TopologyTable topologyTable = controller.getDevice().getTopologyTable();
    IPAddress ip0 = Mockito.mock(IPAddress.class);
    IPAddress ip1 = Mockito.mock(IPAddress.class);
    IPAddress ip2 = Mockito.mock(IPAddress.class);
    RoutingTableEntry entry0 = new RoutingTableEntry(ip0);
    RoutingTableEntry entry1 = new RoutingTableEntry(ip1);
    RoutingTableEntry entry2 = new RoutingTableEntry(ip2);
    RoutingTableEntry entry3 = new RoutingTableEntry(ip0);
    Connection connection0 = new Cable();

    @BeforeEach
    void init(){
        topologyTable.getEntries().add(entry0);
        topologyTable.getEntries().add(entry1);
        topologyTable.getEntries().add(entry2);
        topologyTable.getEntries().add(entry3);

        device.setIp_address(ip1);
        connection0.linkDevices(controller, deviceController);
        controller.setConnection(connection0);
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
        TopologyTable receivedTopologyTable = new TopologyTable();
        RoutingTableEntry betterEntry = new RoutingTableEntry(ip1);
        RoutingTableEntry worseEntry = new RoutingTableEntry(ip1);
        betterEntry.setFeasibleDistance(100);
        worseEntry.setFeasibleDistance(10000);

        receivedTopologyTable.getEntries().add(betterEntry);
        receivedTopologyTable.getEntries().add(worseEntry);

        assertEquals(1, topologyTable.getAllEntriesForIP(ip1).size());
        topologyTable.update(controller, receivedTopologyTable, ip1);
        assertEquals(3, topologyTable.getAllEntriesForIP(ip1).size());
    }

    @Test
    void updateEntry() {
        entry1.setFeasibleDistance(1000);
        RoutingTableEntry betterEntry = new RoutingTableEntry(ip1);
        RoutingTableEntry worseEntry = new RoutingTableEntry(ip1);
        betterEntry.setFeasibleDistance(100);
        worseEntry.setFeasibleDistance(10000);

        assertEquals(1, topologyTable.getAllEntriesForIP(ip1).size());
        topologyTable.update(controller, betterEntry, ip1);
        assertEquals(2, topologyTable.getAllEntriesForIP(ip1).size());
        topologyTable.update(controller, worseEntry, ip1);
        assertEquals(3, topologyTable.getAllEntriesForIP(ip1).size());
        assertEquals(2, topologyTable.getSuccessorCount(ip1));
    }

    @Test
    void getAllEntriesForIP() {
        List<RoutingTableEntry> entryList = topologyTable.getAllEntriesForIP(ip0);
        assertEquals(2, entryList.size());
        assertEquals(entry0, entryList.get(0));
        assertEquals(entry3, entryList.get(1));

        List<RoutingTableEntry> entryList0 = topologyTable.getAllEntriesForIP(ip1);
        assertEquals(1, entryList0.size());
        assertEquals(entry1, entryList0.get(0));
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
        entry0.setFeasibleDistance(102942);
        entry3.setFeasibleDistance(1029422);
        assertEquals(1, topologyTable.getSuccessorEntriesForIP(ip0).size());
        assertEquals(entry3, topologyTable.getSuccessorEntriesForIP(ip0).get(0));
    }

    @Test
    void getSuccessorCount() {
        assertEquals(1, topologyTable.getSuccessorCount(ip0));
        assertEquals(0, topologyTable.getSuccessorCount(ip1));
    }
}