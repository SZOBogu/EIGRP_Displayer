package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RouterControllerTest {
    Router router = new Router("R");
    Router router0 = new Router("R0");
    Router router1 = new Router("R1");
    Router router2 = new Router("R2");
    IPAddress ip = Mockito.mock(IPAddress.class);
    IPAddress ip0 = Mockito.mock(IPAddress.class);
    IPAddress ip1 = Mockito.mock(IPAddress.class);

    RouterController controller = new RouterController(router);
    DeviceController deviceController0 = new DeviceController(router0);
    DeviceController deviceController1 = new DeviceController(router1);
    DeviceController deviceController2 = new DeviceController(router2);

    Connection connection0 = new Cable();
    Connection connection1 = new Cable();
    Connection connection2 = new Cable();

    void init(){
        connection0.linkDevice(controller);
        connection0.linkDevice(deviceController0);
        connection1.linkDevice(deviceController0);
        connection1.linkDevice(deviceController1);
        connection2.linkDevice(deviceController1);
        connection2.linkDevice(controller);


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
    void setConnection(){
        controller.setConnection(connection0);
        assertEquals(connection0, controller.getDevice().getDeviceInterfaces()[0].getConnection());
        assertNull(controller.getDevice().getDeviceInterfaces()[1].getConnection());
        assertNull(controller.getDevice().getDeviceInterfaces()[2].getConnection());
        assertNull(controller.getDevice().getDeviceInterfaces()[3].getConnection());

        controller.setConnection(connection1);
        assertEquals(connection0, controller.getDevice().getDeviceInterfaces()[0].getConnection());
        assertEquals(connection1, controller.getDevice().getDeviceInterfaces()[1].getConnection());
        assertNull(controller.getDevice().getDeviceInterfaces()[2].getConnection());
        assertNull(controller.getDevice().getDeviceInterfaces()[3].getConnection());
    }

    @Test
    void getDevice() {
        assertEquals(router, controller.getDevice());
    }

    @Test
    void setDevice() {
        controller.setDevice(router0);
        assertEquals(router0, controller.getDevice());
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
    void getAllConnectedDeviceControllers() {
        init();
        assertEquals(2, controller.getAllConnectedDeviceControllers().size());
        assertEquals(deviceController0, controller.getAllConnectedDeviceControllers().get(0));
        assertEquals(deviceController1, controller.getAllConnectedDeviceControllers().get(1));
    }

    @Test
    void getConnectedDeviceController() {
        init();
        assertEquals(deviceController0, controller.getConnectedDeviceController(ip0));
        assertEquals(deviceController1, controller.getConnectedDeviceController(ip1));
    }

    @Test
    void getConnectionWithDevice() {
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
    }

    @Test
    void printTopologyTable() {
    }

    @Test
    void printRoutingTable() {
    }

    @Test
    void getAllNeighbours() {
        init();
        controller.getDevice().getNeighbourTable().formNeighbourship(ip1);
        assertEquals(1, controller.getAllNeighbours().size());
        assertEquals(deviceController1, controller.getAllNeighbours().get(0));
//TODO: look into it
//        controller.getDevice().getNeighbourTable().formNeighbourship(ip0);
//        assertEquals(2, controller.getAllNeighbours().size());
//        assertEquals(deviceController0, controller.getAllNeighbours().get(1));
    }

    @Test
    void getAllNeighboursButOne() {
        init();
        IPAddress ip2 = Mockito.mock(IPAddress.class);
        router.getNeighbourTable().formNeighbourship(ip0);
        router.getNeighbourTable().formNeighbourship(ip1);

        assertEquals(1, controller.getAllNeighboursButOne(ip0).size());
        assertEquals(deviceController1, controller.getAllNeighboursButOne(ip0).get(0));

        assertEquals(1, controller.getAllNeighboursButOne(ip1).size());
        assertEquals(deviceController0, controller.getAllNeighboursButOne(ip1).get(0));

        assertEquals(2, controller.getAllNeighboursButOne(ip2).size());
        assertEquals(deviceController0, controller.getAllNeighboursButOne(ip2).get(0));
        assertEquals(deviceController1, controller.getAllNeighboursButOne(ip2).get(1));
    }
}