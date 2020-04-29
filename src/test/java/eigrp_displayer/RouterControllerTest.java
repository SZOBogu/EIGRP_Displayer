package eigrp_displayer;

import eigrp_displayer.messages.RTPMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouterControllerTest {
    Router router = new Router("R");
    Router router0 = new Router("R0");
    Router router1 = new Router("R1");
    IPAddress ip = Mockito.mock(IPAddress.class);
    IPAddress ip0 = Mockito.mock(IPAddress.class);
    IPAddress ip1 = Mockito.mock(IPAddress.class);

    RouterController controller = new RouterController(router);
    DeviceController deviceController0 = new DeviceController(router0);
    DeviceController deviceController1 = new DeviceController(router1);

    Connection connection0 = new Cable();
    Connection connection1 = new Cable();
    Connection connection2 = new Cable();

    void init(){
        connection0.linkDevices(controller, deviceController0);
        connection1.linkDevices(deviceController0, deviceController1);
        connection2.linkDevices(deviceController1, controller);

        router.setIp_address(ip);
        router0.setIp_address(ip0);
        router1.setIp_address(ip1);
    }

    @Test
    void sendMessage() {

    }

    @Test
    void respond() {

    }

    @Test
    void sendMessages() {
    }

    @Test
    void sendCyclicMessage() {
    }

    @Test
    void updateMetric() {
    }

    @Test
    void respondACK() {
    }

    @Test
    void respondHello() {
    }

    @Test
    void respondQuery() {
    }

    @Test
    void respondUpdate() {
    }

    @Test
    void respondReply() {
    }

    @Test
    void getMessagesSentWaitingForReply() {
        assertEquals(new HashMap<RTPMessage, Integer>(), controller.getMessagesSentWaitingForReply());
    }

    @Test
    void updateTime() {
    }

    @Test
    void updateEntry() {
        init();
        RoutingTableEntry entry0 = new RoutingTableEntry(ip0);

        controller.getDevice().getRoutingTable().getEntries().add(entry0);
        controller.getDevice().getTopologyTable().getEntries().add(entry0);

        RoutingTableEntry betterEntry = new RoutingTableEntry(ip0);
        RoutingTableEntry worseEntry = new RoutingTableEntry(ip0);
        betterEntry.setFeasibleDistance(100);
        worseEntry.setFeasibleDistance(1000000000);
        entry0.setFeasibleDistance(900000000);

        long beforeFD = controller.getDevice().getRoutingTable().getEntry(ip0).getFeasibleDistance();
        assertEquals(entry0, controller.getDevice().getRoutingTable().getEntry(ip0));
        assertEquals(1, controller.getDevice().getTopologyTable().getAllEntriesForIP(ip0).size());
        controller.update(betterEntry, ip0);
        long afterFD = controller.getDevice().getRoutingTable().getEntry(ip0).getFeasibleDistance();
        RoutingTableEntry bestEntry = controller.getDevice().getTopologyTable().getBestEntryForIP(ip0);
        assertTrue(afterFD < beforeFD);
        assertEquals(bestEntry.getReportedDistance(), betterEntry.getFeasibleDistance());
        assertEquals(2, controller.getDevice().getTopologyTable().getAllEntriesForIP(ip0).size());
        assertEquals(1, controller.getDevice().getTopologyTable().getSuccessorCount(ip0));
    }

    @Test
    void testUpdateTable() {
        init();
        RoutingTableEntry entry0 = new RoutingTableEntry(ip0);
        RoutingTableEntry betterEntry = new RoutingTableEntry(ip0);
        RoutingTableEntry worseEntry = new RoutingTableEntry(ip0);

        betterEntry.setFeasibleDistance(100);
        worseEntry.setFeasibleDistance(1000000000);
        entry0.setFeasibleDistance(900000000);

        TopologyTable topologyTable = new TopologyTable();
        topologyTable.getEntries().addAll(Arrays.asList(entry0, betterEntry, worseEntry));

        assertEquals(0, controller.getDevice().getRoutingTable().getEntries().size());
        assertEquals(0, controller.getDevice().getTopologyTable().getAllEntriesForIP(ip0).size());
        controller.update(topologyTable, ip0);
        assertEquals(3, controller.getDevice().getTopologyTable().getAllEntriesForIP(ip0).size());
        assertEquals(2, controller.getDevice().getTopologyTable().getSuccessorCount(ip0));
        RoutingTableEntry bestEntry = controller.getDevice().getTopologyTable().getBestEntryForIP(ip0);
        assertEquals(bestEntry.getReportedDistance(), betterEntry.getFeasibleDistance());
        assertEquals(bestEntry, controller.getDevice().getRoutingTable().getEntry(ip0));
    }

    @Test
    void getInterfaceEntry() {
        init();
        RoutingTableEntry entry = new RoutingTableEntry(ip0);
        controller.getDevice().getRoutingTable().getEntries().add(entry);
        assertEquals(router.getDeviceInterfaces()[0],
                controller.getInterface(controller.getDevice().getRoutingTable().getEntry(ip0)));
    }

    @Test
    void getInterfaceIP() {
        init();
        RoutingTableEntry entry = new RoutingTableEntry(ip0);
        controller.getDevice().getRoutingTable().getEntries().add(entry);
        assertEquals(router.getDeviceInterfaces()[0],
                controller.getInterface(ip0));
    }

    //TODO: add some records
    @Test
    void printTopologyTable() {
        String string = "IP-EIGRP Topology Table for AS 1\n" +
                "Codes: P - Passive, A - Active, U - Update, Q - Query, R - Reply, r - Reply status\n";

        assertEquals(string, controller.printTopologyTable());
    }
    //TODO: add some records
    @Test
    void printRoutingTable() {
        IPAddress ip = Mockito.mock(IPAddress.class);
        controller.getDevice().setIp_address(ip);
        String string = ip + "\\" + MessageScheduler.getInstance().getNetwork().getMask().getMask() + " isn't variably subnetted, 0 subnets, 1 masks\n";

        assertEquals(string, controller.printRoutingTable());
    }

    @Test
    void getAllNeighbourControllers() {
        init();
        controller.getDevice().getNeighbourTable().formNeighbourship(ip1);
        assertEquals(1, controller.getAllNeighbourControllers().size());
        assertEquals(deviceController1, controller.getAllNeighbourControllers().get(0));
        controller.getDevice().getNeighbourTable().formNeighbourship(ip0);
        assertEquals(2, controller.getAllNeighbourControllers().size());
        assertEquals(deviceController1, controller.getAllNeighbourControllers().get(1));
    }

    @Test
    void getAllNeighbourControllersButOne() {
        init();
        IPAddress ip2 = Mockito.mock(IPAddress.class);
        router.getNeighbourTable().formNeighbourship(ip0);
        router.getNeighbourTable().formNeighbourship(ip1);

        assertEquals(1, controller.getAllNeighbourControllersButOne(ip0).size());
        assertEquals(deviceController1, controller.getAllNeighbourControllersButOne(ip0).get(0));

        assertEquals(1, controller.getAllNeighbourControllersButOne(ip1).size());
        assertEquals(deviceController0, controller.getAllNeighbourControllersButOne(ip1).get(0));

        assertEquals(2, controller.getAllNeighbourControllersButOne(ip2).size());
        assertEquals(deviceController0, controller.getAllNeighbourControllersButOne(ip2).get(0));
        assertEquals(deviceController1, controller.getAllNeighbourControllersButOne(ip2).get(1));
    }

    //TODO: finish it
    @Test
    void getAddressOfNextDeviceOnPath() {
        init();
        Connection connection3 = new Cable();
        Router router3 = new Router("R3");
        RouterController routerController3 = new RouterController(router3);

        connection3.linkDevices(deviceController1, routerController3);
    }
}