package eigrp_displayer;

import eigrp_displayer.messages.RTPMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        controller.setConnection(connection0);
        controller.setConnection(connection2);
        deviceController0.setConnection(connection0);
        deviceController0.setConnection(connection1);
        deviceController1.setConnection(connection1);
    }

    @Test
    void sendMessage() {

    }

    @Test
    void receiveMessage() {
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
    void scheduleHellos() {
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
    void update() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void getInterface() {
        init();
        RoutingTableEntry entry = new RoutingTableEntry(ip0);
        controller.getDevice().getRoutingTable().getEntries().add(entry);
        assertEquals(router.getDeviceInterfaces()[0],
                controller.getInterface(controller.getDevice().getRoutingTable().getEntry(ip0)));
    }

    @Test
    void printTopologyTable() {
    }

    @Test
    void printRoutingTable() {
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
}