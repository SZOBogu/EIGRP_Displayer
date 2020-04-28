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
    void setMessageSendingTimeOffset() {
        router.setMessageSendingTimeOffset(10);
        assertEquals(10, router.getMessageSendingTimeOffset());
        router.setMessageSendingTimeOffset(-10);
        assertEquals(0, router.getMessageSendingTimeOffset());
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
        assertEquals(new TopologyTable(), router.getTopologyTable());
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
}