package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ShowcaseNetworkTest {
    IPAddress ipNet = new IPAddress(192,168,0,0);
    IPAddress ipBr = new IPAddress(192,168,0,15);
    IPAddress ipH0 = new IPAddress(192,168,0,1);
    IPAddress ipH1 = new IPAddress(192,168,0,2);
    SubnetMask mask = new SubnetMask(28);
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
        SubnetMask mask0 = new SubnetMask(24);
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
        ArrayList mockList = Mockito.mock(ArrayList.class);
        net.setConnections(mockList);
        assertEquals(mockList, net.getConnections());
    }

    @Test
    void getDevices() {
        assertEquals(new ArrayList<>(), net.getDevices());
    }

    @Test
    void setDevices() {
        ArrayList mockList = Mockito.mock(ArrayList.class);
        net.setDevices(mockList);
        assertEquals(mockList, net.getDevices());
    }

    @Test
    void testLinkDevices() {
    }

    @Test
    void getDevice() {
    }

    @Test
    void getNeighboursOf() {
    }
}