package eigrp_displayer;

import eigrp_displayer.messages.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RouterControllerTest {
    Router router = new Router("R");
    Router router0 = new Router("R0");
    Router router1 = new Router("R1");
    IPAddress ip = Mockito.mock(IPAddress.class);
    IPAddress ip0 = Mockito.mock(IPAddress.class);
    IPAddress ip1 = Mockito.mock(IPAddress.class);

    RouterController controller = new RouterController(router);
    RouterController controller0 = new RouterController(router0);
    RouterController controller1 = new RouterController(router1);

    Connection connection0 = new Cable();
    Connection connection1 = new Cable();
    Connection connection2 = new Cable();

    void init(){
        connection0.linkDevices(controller, controller0);
        connection1.linkDevices(controller0, controller1);
        connection2.linkDevices(controller1, controller);

        router.setIp_address(ip);
        router0.setIp_address(ip0);
        router1.setIp_address(ip1);
    }

    @Test
    void respond() {

    }

    @Test
    void respondACK() {
        init();
        ACKMessage ack = new ACKMessage(ip, ip0);
        ReplyMessage replyGoodIP = new ReplyMessage(ip0, ip, Mockito.mock(RoutingTableEntry.class));
        UpdateMessage updateGoodIP = new UpdateMessage(ip0, ip, Mockito.mock(TopologyTable.class));
        ReplyMessage replyWrongIP = new ReplyMessage(ip0, ip1, Mockito.mock(RoutingTableEntry.class));
        Message outOfPlaceMsg = Mockito.mock(Message.class);

        HashMap<Message, Integer> messageMap = new HashMap<>();
        messageMap.put(replyGoodIP, 0);
        messageMap.put(updateGoodIP, 0);
        messageMap.put(replyWrongIP, 0);
        messageMap.put(outOfPlaceMsg, 0);

        //debugging shows that keys are properly deleted despite contains() being always false
        controller0.setMessagesToTryToSendAgain(messageMap);
        assertEquals(4, controller0.getMessagesToTryToSendAgain().size());
        controller0.respondACK(ack);
        assertEquals(3, controller0.getMessagesToTryToSendAgain().size());
        controller0.respondACK(ack);
        assertEquals(2, controller0.getMessagesToTryToSendAgain().size());
        controller0.respondACK(ack);
        assertEquals(2, controller0.getMessagesToTryToSendAgain().size());
        controller0.respondACK(ack);
        assertEquals(2, controller0.getMessagesToTryToSendAgain().size());
    }

    @Test
    void respondHelloWhenAlreadyNeighbours() {
        HelloMessage hello = new HelloMessage(ip, ip0);
        DeviceInterface deviceInterface = controller0.getDevice().getDeviceInterfaces()[0];
        controller0.getDevice().getNeighbourTable().formNeighbourship(deviceInterface, ip);
        NeighbourTableEntry entry = controller0.getDevice().getNeighbourTable().getEntries().get(0);
        entry.setTicksSinceLastHello(10);
        assertEquals(1, controller0.getDevice().getNeighbourTable().getAllNeighboursAddresses().size());
        controller0.respondHello(hello);
        assertEquals(1, controller0.getDevice().getNeighbourTable().getAllNeighboursAddresses().size());
        assertEquals(entry, controller0.getDevice().getNeighbourTable().getEntries().get(0));
        assertEquals(0, entry.getTicksSinceLastHello());
    }

    @Test
    void respondHelloFromNewRouter(){
        init();
        HelloMessage hello = new HelloMessage(ip, ip0);
        assertTrue(controller0.getDevice().getNeighbourTable().getAllNeighboursAddresses().isEmpty());
        controller0.respondHello(hello);
        assertEquals(1, controller0.getDevice().getNeighbourTable().getAllNeighboursAddresses().size());
        assertEquals(ip, controller0.getDevice().getNeighbourTable().getAllNeighboursAddresses().get(0));
        assertTrue(controller0.getMessageSchedule().get(Clock.getTime() + 1) instanceof UpdateMessage);
    }

    @Test
    void respondHelloFormEndDevice(){
        init();
        IPAddress ip3 = Mockito.mock(IPAddress.class);
        EndDevice device = new EndDevice();
        device.setIp_address(ip3);
        DeviceController deviceController = new DeviceController(device);
        Connection connection3 = new Cable();
        connection3.linkDevices(controller0, deviceController);

        HelloMessage helloMessage = new HelloMessage(ip3, ip0);

        assertTrue(controller0.getDevice().getNeighbourTable().getAllNeighboursAddresses().isEmpty());
        assertNull(controller0.getDevice().getRoutingTable().getEntry(ip3));
        assertTrue(controller0.getDevice().getTopologyTable().getAllEntriesForIP(ip3).isEmpty());

        controller0.respond(helloMessage);

        assertFalse(controller0.getDevice().getNeighbourTable().getAllNeighboursAddresses().isEmpty());
        assertNotNull(controller0.getDevice().getRoutingTable().getEntry(ip3));
        assertFalse(controller0.getDevice().getTopologyTable().getAllEntriesForIP(ip3).isEmpty());
    }
    @Test
    void respondQueryLoopedBack() {
        init();
        QueryMessage queryMessage = new QueryMessage(ip, ip0, ip1);
        QueryMessage loopedBackQueryMessage = new QueryMessage(ip0, ip, ip1);
        QueryMessage supposedToStayQueryMessage = new QueryMessage(ip0, ip, Mockito.mock(IPAddress.class));

        HashMap<Message, Integer> messageMap = new HashMap<>();
        messageMap.put(loopedBackQueryMessage, 0);
        messageMap.put(supposedToStayQueryMessage, 0);
        controller0.setMessagesSentWaitingForReply(messageMap);

        assertEquals(2, controller0.getMessagesSentWaitingForReply().size());
        controller0.respondQuery(queryMessage);
        //debugging shows that correct one stays
        assertEquals(1, controller0.getMessagesSentWaitingForReply().size());
        assertTrue(controller0.getMessagesSentWaitingForReply().containsKey(supposedToStayQueryMessage));
        assertTrue(controller0.getMessageSchedule().get(Clock.getTime()) instanceof ACKMessage);
    }

    @Test
    void respondQuery() {
        init();
        QueryMessage queryMessage = new QueryMessage(ip, ip0, ip1);
        RoutingTableEntry entry = new RoutingTableEntry(ip1);
        controller0.getDevice().getRoutingTable().getEntries().add(entry);

        controller0.respondQuery(queryMessage);
        assertTrue(controller0.getMessageSchedule().get(Clock.getTime()) instanceof ACKMessage);
        ReplyMessage reply = (ReplyMessage) controller0.getMessageSchedule().get(Clock.getTime()+1);
        assertEquals(entry, reply.getRoutingTableEntry());
    }

    @Test
    void respondUpdate() {
        init();
        TopologyTable table = new TopologyTable();
        RoutingTableEntry entry0 = new RoutingTableEntry(ip1);
        entry0.setReportedDistance(100000000);
        entry0.setFeasibleDistance(100000000);
        controller0.getDevice().getTopologyTable().getEntries().add(entry0);
        controller0.getDevice().getRoutingTable().getEntries().add(entry0);

        RoutingTableEntry entry1 = new RoutingTableEntry(ip1);
        table.getEntries().add(entry1);
        UpdateMessage updateMessage = new UpdateMessage(ip, ip0, table);
        entry1.setReportedDistance(10);
        entry1.setFeasibleDistance(10);

        RoutingTableEntry initialEntry = controller0.getDevice().getRoutingTable().getEntry(ip1);
        long initialBestPathMetric = controller0.getDevice().getTopologyTable().getBestEntryForIP(ip1).getFeasibleDistance();
        controller0.respond(updateMessage);
        assertNotEquals(initialEntry, controller0.getDevice().getRoutingTable().getEntry(ip1));
        long bestEntryMetric = controller0.getDevice().getTopologyTable().getBestEntryForIP(ip1).getFeasibleDistance();
        assertTrue(initialBestPathMetric > bestEntryMetric);
        assertTrue(controller0.getMessageSchedule().get(Clock.getTime() + 1) instanceof ACKMessage);
    }

    @Test
    void respondReply() {
        init();
        RoutingTableEntry entry0 = new RoutingTableEntry(ip1);
        ReplyMessage replyMessage = new ReplyMessage(ip, ip0, entry0);

        assertNull(controller0.getDevice().getRoutingTable().getEntry(ip1));
        assertTrue(controller0.getDevice().getTopologyTable().getAllEntriesForIP(ip1).isEmpty());

        controller0.respond(replyMessage);
        assertTrue(controller0.getMessageSchedule().get(Clock.getTime() + 1) instanceof ACKMessage);

        assertNotNull(controller0.getDevice().getRoutingTable().getEntry(ip1));
        assertFalse(controller0.getDevice().getTopologyTable().getAllEntriesForIP(ip1).isEmpty());
    }

    @Test
    void getMessagesSentWaitingForReply() {
        assertEquals(new HashMap<Message, Integer>(), controller.getMessagesSentWaitingForReply());
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
    void updateTable() {
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

    @Test
    void printTopologyTable() {
        init();
        String string = "IP-EIGRP Topology Table for AS 1\n" +
                "Codes: P - Passive, A - Active, U - Update, Q - Query, R - Reply, r - Reply status\n";

        assertEquals(string, controller.printTopologyTable());

        RoutingTableEntry entry = new RoutingTableEntry(ip0);
        RoutingTableEntry entry1 = new RoutingTableEntry(ip1);
        RoutingTableEntry entry2 = new RoutingTableEntry(ip0);
        entry.setFeasibleDistance(10);
        entry1.setFeasibleDistance(121240);
        entry2.setFeasibleDistance(100);

        controller.getDevice().getTopologyTable().getEntries().add(entry);
        controller.getDevice().getTopologyTable().getEntries().add(entry1);
        controller.getDevice().getTopologyTable().getEntries().add(entry2);

        string += entry.getCode() + " " + entry.getIp_address() + "/"
                + MessageScheduler.getInstance().getNetwork().getMask().getMask()
                + ", 1 successors, FD is " + entry.getFeasibleDistance() + "\n";

        string += "\tvia " + entry.getIp_address() + " (" + entry.getFeasibleDistance()
                + "\\" + entry.getReportedDistance() + " Interface 0\n";
        string += "\tvia " + entry2.getIp_address() + " (" + entry2.getFeasibleDistance()
                + "\\" + entry2.getReportedDistance() + " Interface 0\n";

        string += entry1.getCode() + " " + entry1.getIp_address() + "/"
                + MessageScheduler.getInstance().getNetwork().getMask().getMask()
                + ", 0 successors, FD is " + entry1.getFeasibleDistance() + "\n";
        string += "\tvia " + entry1.getIp_address() + " (" + entry1.getFeasibleDistance()
                + "\\" + entry1.getReportedDistance() + ") Interface 1\n";

        assertEquals(string, controller.printTopologyTable());
    }

    @Test
    void printRoutingTable() {
        init();
        RoutingTableEntry entry = new RoutingTableEntry(ip0);
        entry.setPath(Collections.singletonList(connection0));
        String string = controller.getDevice().getIp_address()
                + "\\" + MessageScheduler.getInstance().getNetwork().getMask().getMask()
                + " isn't variably subnetted, 0 subnets, 1 masks\n";

        assertEquals(string, controller.printRoutingTable());
        controller.getDevice().getRoutingTable().getEntries().add(entry);
        string += entry.getCode() + "\t" + entry.getIp_address() + "[" + entry.getReportedDistance() + "\\"
                + entry.getFeasibleDistance() + "] via " + entry.getPath().get(0).getOtherDevice(controller).getDevice().getIp_address()
                + ", Interface 0\n";
        assertEquals(string, controller.printRoutingTable());
    }

    @Test
    void getAllNeighbourControllers() {
        init();
        DeviceInterface deviceInterface = controller0.getDevice().getDeviceInterfaces()[0];
        controller.getDevice().getNeighbourTable().formNeighbourship(deviceInterface, ip1);
        assertEquals(1, controller.getAllNeighbourControllers().size());
        assertEquals(controller1, controller.getAllNeighbourControllers().get(0));
        controller.getDevice().getNeighbourTable().formNeighbourship(deviceInterface, ip0);
        assertEquals(2, controller.getAllNeighbourControllers().size());
        assertEquals(controller1, controller.getAllNeighbourControllers().get(1));
    }

    @Test
    void getAllNeighbourControllersButOne() {
        init();
        DeviceInterface deviceInterface = Mockito.mock(DeviceInterface.class);
        IPAddress ip2 = Mockito.mock(IPAddress.class);
        router.getNeighbourTable().formNeighbourship(deviceInterface, ip0);
        router.getNeighbourTable().formNeighbourship(deviceInterface, ip1);

        assertEquals(1, controller.getAllNeighbourControllersButOne(ip0).size());
        assertEquals(controller1, controller.getAllNeighbourControllersButOne(ip0).get(0));

        assertEquals(1, controller.getAllNeighbourControllersButOne(ip1).size());
        assertEquals(controller0, controller.getAllNeighbourControllersButOne(ip1).get(0));

        assertEquals(2, controller.getAllNeighbourControllersButOne(ip2).size());
        assertEquals(controller0, controller.getAllNeighbourControllersButOne(ip2).get(0));
        assertEquals(controller1, controller.getAllNeighbourControllersButOne(ip2).get(1));
    }

    @Test
    void getAddressOfNextDeviceOnPath() {
        init();
        Connection connection3 = new Cable();
        Router router3 = new Router("R3");
        IPAddress ipAddress = Mockito.mock(IPAddress.class);
        RouterController routerController3 = new RouterController(router3);
        router3.setIp_address(ipAddress);

        connection3.linkDevices(controller1, routerController3);

        ArrayList<Connection> bestPath = new ArrayList<>(Arrays.asList(connection3, connection2));
        ArrayList<Connection> successorPath = new ArrayList<>(Arrays.asList(connection3, connection1, connection0));
        RoutingTableEntry bestEntry = new RoutingTableEntry(ip);
        RoutingTableEntry successorEntry = new RoutingTableEntry(ip);
        bestEntry.setPath(bestPath);
        successorEntry.setPath(successorPath);

        routerController3.getDevice().getTopologyTable().getEntries().add(bestEntry);
        routerController3.getDevice().getTopologyTable().getEntries().add(successorEntry);
        routerController3.getDevice().getRoutingTable().getEntries().add(bestEntry);

        assertEquals(ip1, routerController3.getAddressOfNextDeviceOnPath(ip));
    }

    @Test
    void sendQueryMessages() {
        init();
        QueryMessage q0 = new QueryMessage(ip, ip0, ip1);
        QueryMessage q1 = new QueryMessage(ip, ip0, ip1);
        QueryMessage q2 = new QueryMessage(ip, ip0, ip1);

        List<QueryMessage> qList = new ArrayList<>(Arrays.asList(q0, q1, q2));

        controller.sendQueryMessages(qList);
        assertEquals(q0, controller.getMessageSchedule().get(Clock.getTime() + 1));
        assertEquals(q1, controller.getMessageSchedule().get(Clock.getTime() + 2));
        assertEquals(q2, controller.getMessageSchedule().get(Clock.getTime() + 3));
    }

    @Test
    void severNeighbourship() {
        init();
        controller.getDevice().getNeighbourTable().formNeighbourship(controller.getInterface(ip0), ip0);

        RoutingTableEntry entry = new RoutingTableEntry(ip0);
        List<Connection> path =  new ArrayList<>();
        entry.setPath(path);
        controller.update(entry, ip0);

        assertEquals(1, controller.getDevice().getNeighbourTable().getEntries().size());
        assertEquals(1, controller.getDevice().getRoutingTable().getEntries().size());
        assertEquals(1, controller.getDevice().getTopologyTable().getEntries().size());
        controller.severNeighbourship(ip0);
        assertEquals(0, controller.getDevice().getNeighbourTable().getEntries().size());
        assertEquals(0, controller.getDevice().getRoutingTable().getEntries().size());
        assertEquals(0, controller.getDevice().getTopologyTable().getEntries().size());
    }
}