package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {
    IPAddress ip_address0 = Mockito.mock(IPAddress.class);
    Router router = new Router("Some router");

    @Test
    void getName() {
        assertEquals("Some router", router.getName());
    }

    @Test
    void setName() {
        router.setName("Test");
        assertEquals("Test", router.getName());
    }

    @Test
    void isK1() {
        assertTrue(router.isK1());
    }

    @Test
    void setK1() {
        router.setK1(false);
        assertFalse(router.isK1());
    }

    @Test
    void isK2() {
        assertFalse(router.isK2());
    }

    @Test
    void setK2() {
        router.setK2(true);
        assertTrue(router.isK2());
    }

    @Test
    void isK3() {
        assertTrue(router.isK3());
    }

    @Test
    void setK3() {
        router.setK3(false);
        assertFalse(router.isK3());
    }

    @Test
    void isK4() {
        assertFalse(router.isK4());
    }

    @Test
    void setK4() {
        router.setK4(true);
        assertTrue(router.isK4());
    }

    @Test
    void isK5() {
        assertFalse(router.isK5());
    }

    @Test
    void setK5() {
        router.setK5(true);
        assertTrue(router.isK5());
    }

    @Test
    void getIP_Address(){
        assertNull(router.getIp_address());
    }

    @Test
    void setIP_Address(){
        router.setIp_address(ip_address0);
        assertEquals(ip_address0, router.getIp_address());
    }

    @Test
    void getMessageSendingTimeOffset() {
        assertTrue(router.getMessageSendingTimeOffset() < 60 &&
                router.getMessageSendingTimeOffset() >= 0);
    }

    @Test
    void getRoutingTable() {
        assertEquals(new RoutingTable(), router.getRoutingTable());
    }

    @Test
    void getNeighbourTable() {
        assertEquals(new NeighbourTable(), router.getNeighbourTable());
    }

    @Test
    void getTopologyTable() {
        assertEquals(new RoutingTable(), router.getTopologyTable());
    }

    @Test
    void getDeviceInterfaces() {
        assertEquals(4, router.getDeviceInterfaces().length);
        assertEquals("Interface 0", router.getDeviceInterfaces()[0].getName());
        assertNull(router.getDeviceInterfaces()[0].getConnection());
        assertEquals("Interface 1", router.getDeviceInterfaces()[1].getName());
        assertNull(router.getDeviceInterfaces()[0].getConnection());
        assertEquals("Interface 2", router.getDeviceInterfaces()[2].getName());
        assertNull(router.getDeviceInterfaces()[0].getConnection());
        assertEquals("Interface 3", router.getDeviceInterfaces()[3].getName());
        assertNull(router.getDeviceInterfaces()[0].getConnection());
    }

    @Test
    void setConnection(){
        Connection connection0 = Mockito.mock(Connection.class);
        router.setConnection(connection0);
        assertEquals(connection0, router.getDeviceInterfaces()[0].getConnection());
        assertNull(router.getDeviceInterfaces()[1].getConnection());
    }

    @Test
    void getAllConnectedDevices() {
        Router router0 = new Router("R1");
        Router router1 = new Router("R2");

        Cable cable0 = new Cable();
        Cable cable1 = new Cable();

        cable0.linkDevice(router);
        cable1.linkDevice(router);
        cable0.linkDevice(router0);
        cable1.linkDevice(router1);

        router.setConnection(cable0);
        router.setConnection(cable1);
        router0.setConnection(cable0);
        router1.setConnection(cable1);

        assertEquals(2, router.getAllConnectedDevices().size());
        assertEquals(router0, router.getAllConnectedDevices().get(0));
        assertEquals(router1, router.getAllConnectedDevices().get(1));
    }

    @Test
    void getConnectedDevice() {
        Router router0 = new Router("R1");
        Router router1 = new Router("R2");
        IPAddress ip0 = Mockito.mock(IPAddress.class);
        IPAddress ip1 = Mockito.mock(IPAddress.class);
        router0.setIp_address(ip0);
        router1.setIp_address(ip1);

        Cable cable0 = new Cable();
        Cable cable1 = new Cable();

        cable0.linkDevice(router);
        cable1.linkDevice(router);
        cable0.linkDevice(router0);
        cable1.linkDevice(router1);

        router.setConnection(cable0);
        router.setConnection(cable1);
        router0.setConnection(cable0);
        router1.setConnection(cable1);

        assertEquals(router0, router.getConnectedDevice(ip0));
        assertEquals(router1, router.getConnectedDevice(ip1));
    }

    @Test
    void getAllNeighbours() {
        Router router0 = new Router("R1");
        Router router1 = new Router("R2");
        IPAddress ip0 = Mockito.mock(IPAddress.class);
        IPAddress ip1 = Mockito.mock(IPAddress.class);
        router0.setIp_address(ip0);
        router1.setIp_address(ip1);

        Cable cable0 = new Cable();
        Cable cable1 = new Cable();

        cable0.linkDevice(router);
        cable1.linkDevice(router);
        cable0.linkDevice(router0);
        cable1.linkDevice(router1);

        router.setConnection(cable0);
        router.setConnection(cable1);
        router0.setConnection(cable0);
        router1.setConnection(cable1);

        router.getNeighbourTable().formNeighbourship(ip1);

        assertEquals(1, router.getAllNeighbours().size());
        assertEquals(router1, router.getAllNeighbours().get(0));
    }
}