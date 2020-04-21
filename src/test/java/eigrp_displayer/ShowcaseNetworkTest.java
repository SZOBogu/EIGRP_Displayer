package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ShowcaseNetworkTest {
    IPAddress ipNet = new IPAddress(192,168,0,0);
    IPAddress ipBr = new IPAddress(192,168,0,15);
    IPAddress ipH0 = new IPAddress(192,168,0,1);
    IPAddress ipH1 = new IPAddress(192,168,0,2);
    Mask mask = new Mask(28);
    ShowcaseNetwork net = new ShowcaseNetwork(ipNet, ipBr, mask);
    Device device0 = new Router("Router");
    Device device1 = new EndDevice();

    @Test
    void addDevice() {
        assertNull(device0.getIp_address());
        net.addDevice(device0);
        assertEquals(1, net.getDevices().size());
        assertEquals(ipH0, device0.getIp_address());
    }

    @Test
    void removeDevice() {
        this.addDevice();
        net.removeDevice(device0);
        assertNull(device0.getIp_address());
        assertEquals(0, net.getDevices().size());
    }

    @Test
    void linkDevices() {
        net.linkDevices(device0, device1);
        assertEquals(1, net.getConnections().size());
        assertEquals(device0, net.getConnections().get(0).getDevice1());
        assertEquals(device1, net.getConnections().get(0).getDevice2());
    }

    @Test
    void getMask() {
        assertEquals(mask, net.getMask());
    }

    @Test
    void setMask() {
        Mask mask0 = new Mask(24);
        net.setMask(mask0);
        assertEquals(mask0, net.getMask());
    }

    @Test
    void getNetworkAddress() {
        assertEquals(ipNet, net.getNetworkAddress());
    }

    @Test
    void setNetworkAddress() {
        net.setNetworkAddress(ipH0);
        assertEquals(ipH0, net.getNetworkAddress());
    }

    @Test
    void getBroadcastAddress() {
        assertEquals(ipBr, net.getBroadcastAddress());
    }

    @Test
    void setBroadcastAddress() {
        net.setBroadcastAddress(ipH1);
        assertEquals(ipH1, net.getBroadcastAddress());
    }

    @Test
    void getConnections() {
        assertEquals(new ArrayList<>(), net.getConnections());
    }

    @Test
    void setConnections() {
        ArrayList<Connection> mockList = Mockito.mock(ArrayList.class);
        net.setConnections(mockList);
        assertEquals(mockList, net.getConnections());
    }

    @Test
    void getDevices() {
        assertEquals(new ArrayList<>(), net.getDevices());
    }

    @Test
    void setDevices() {
        ArrayList<Device> mockList = Mockito.mock(ArrayList.class);
        net.setDevices(mockList);
        assertEquals(mockList, net.getDevices());
    }

    @Test
    void getDevice() {
        net.addDevice(device0);
        net.addDevice(device1);
        assertEquals(ipH0, device0.getIp_address());
        assertEquals(ipH1, device1.getIp_address());
        assertEquals(device0, net.getDevice(ipH0));
        assertEquals(device1, net.getDevice(ipH1));
    }

    @Test
    void getNeighboursOf() {
        Device device2 = new Router("R2");
        Device device3 = new Router("R3");

        net.getConnections().clear();

        Connection connection00 = new Cable();
        Connection connection01 = new Cable();
        Connection connection02 = new Cable();

        connection00.linkDevice(device0);
        connection00.linkDevice(device1);

        connection01.linkDevice(device1);
        connection01.linkDevice(device2);

        connection02.linkDevice(device2);
        connection02.linkDevice(device3);

        net.getConnections().addAll(Arrays.asList(connection00, connection01, connection02));

        assertEquals(2, net.getNeighboursOf(device1).size());
        assertEquals(device0, net.getNeighboursOf(device1).get(0));
        assertEquals(device2, net.getNeighboursOf(device1).get(1));
    }

    @Test
    void checkIfConnected() {
        assertFalse(net.checkIfConnected(device0, device1));
        Connection connection = new Cable();
        connection.setDevice1(device0);
        connection.setDevice2(device1);
        net.getConnections().add(connection);
        assertTrue(net.checkIfConnected(device0, device1));
    }
}