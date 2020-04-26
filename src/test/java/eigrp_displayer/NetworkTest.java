package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NetworkTest {
    IPAddress ipNet = new IPAddress(192,168,0,0);
    IPAddress ipBr = new IPAddress(192,168,0,15);
    IPAddress ipH0 = new IPAddress(192,168,0,1);
    IPAddress ipH1 = new IPAddress(192,168,0,2);
    Mask mask = new Mask(28);
    Network net = new Network(ipNet, ipBr, mask);
    Device device0 = new Router("Router");
    Device device1 = new EndDevice();
    DeviceController controller0 = new DeviceController();
    DeviceController controller1 = new DeviceController();

    @BeforeEach
    void init(){
        controller0.setDevice(device0);
        controller1.setDevice(device1);
    }

    @Test
    void addDevice() {
        assertNull(controller0.getDevice().getIp_address());
        net.addDeviceController(controller0);
        assertEquals(1, net.getDeviceControllers().size());
        assertEquals(ipH0, device0.getIp_address());
    }

    @Test
    void removeDevice() {
        net.addDeviceController(controller0);
        net.addDeviceController(controller1);
        assertEquals(2, net.getDeviceControllers().size());
        net.removeDeviceController(controller0);
        assertEquals(1, net.getDeviceControllers().size());
        assertNull(device0.getIp_address());
    }

    @Test
    void connectDevices() {
        net.connectDevices(controller0, controller1);
        assertEquals(device0.getDeviceInterfaces()[0].getConnection(),
                device1.getDeviceInterfaces()[0].getConnection());
        assertEquals(device1,
                device0.getDeviceInterfaces()[0].getConnection().getOtherDevice(controller0).getDevice());
        assertEquals(device0,
                device1.getDeviceInterfaces()[0].getConnection().getOtherDevice(controller1).getDevice());
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
    void getDeviceControllers() {
        assertEquals(new ArrayList<>(), net.getDeviceControllers());
    }

    @Test
    void setDeviceControllers() {
        ArrayList<DeviceController> mockList = Mockito.mock(ArrayList.class);
        net.setDeviceControllers(mockList);
        assertEquals(mockList, net.getDeviceControllers());
    }
}