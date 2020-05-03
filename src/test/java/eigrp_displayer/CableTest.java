package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CableTest {
    Cable cable = new Cable();
    Cable cable0 = new Cable("Dummy Cable", 10, 11, 12,13);
    Device device = new EndDevice();
    Device device1 = new EndDevice();
    DeviceController controller = new DeviceController(device);
    DeviceController controller0 = new DeviceController(device1);

    @BeforeEach
    void init(){
        cable.setDevice1(controller);
        cable.setDevice2(controller0);
    }

    @Test
    void getName() {
        assertEquals("Ethernet Cable", cable.getName());
        assertEquals("Dummy Cable", cable0.getName());
    }

    @Test
    void setName() {
        cable.setName("Test");
        assertEquals("Test", cable.getName());
    }

    @Test
    void getBandwidth() {
        assertEquals(100000, cable.getBandwidth());
        assertEquals(10, cable0.getBandwidth());
    }

    @Test
    void setBandwidth() {
        cable.setBandwidth(2);
        assertEquals(2, cable.getBandwidth());
    }

    @Test
    void getDelay() {
        assertEquals(100, cable.getDelay());
        assertEquals(11, cable0.getDelay());
    }

    @Test
    void setDelay() {
        cable.setDelay(3);
        assertEquals(3, cable.getDelay());
    }

    @Test
    void getLoad() {
        assertEquals(10, cable.getLoad());
        assertEquals(12, cable0.getLoad());
    }

    @Test
    void setLoad() {
        cable.setLoad(4);
        assertEquals(4, cable.getLoad());
    }

    @Test
    void getReliability() {
        assertEquals(10, cable.getReliability());
        assertEquals(13, cable0.getReliability());
    }

    @Test
    void setReliability() {
        cable.setReliability(5);
        assertEquals(5, cable.getReliability());
    }

    @Test
    void getDevice1() {
        assertEquals(controller ,cable.getDevice1());
    }

    @Test
    void setDevice1() {
        cable.setDevice1(controller0);
        assertEquals(controller0 ,cable.getDevice1());
    }

    @Test
    void getDevice2() {
        assertEquals(controller0 ,cable.getDevice2());
    }

    @Test
    void setDevice2() {
        cable.setDevice2(controller);
        assertEquals(controller ,cable.getDevice2());
    }
    @Test
    void linkDevice(){
        assertNull(cable0.getDevice1());
        assertNull(cable0.getDevice2());
        cable0.linkDevice(controller);
        assertEquals(controller, cable0.getDevice1());
        assertNull(cable0.getDevice2());
        assertEquals(cable0, controller.getDevice().getDeviceInterfaces()[0].getConnection());
        cable0.linkDevice(controller0);
        assertEquals(controller, cable0.getDevice1());
        assertEquals(controller0, cable0.getDevice2());
        assertEquals(cable0, controller0.getDevice().getDeviceInterfaces()[0].getConnection());
    }

    @Test
    void getOtherDevice() {
        assertEquals(controller0, cable.getOtherDevice(controller));
        assertEquals(controller, cable.getOtherDevice(controller0));
        cable.setDevice1(null);
        assertNull(cable.getOtherDevice(controller));
        assertNull(cable.getOtherDevice(controller0));
    }

    @Test
    void linkDevices() {
        cable.linkDevices(controller, controller0);
        assertEquals(controller, cable.getDevice1());
        assertEquals(controller0, cable.getDevice2());
        assertEquals(cable, controller.getDevice().getDeviceInterfaces()[0].getConnection());
        assertEquals(cable, controller0.getDevice().getDeviceInterfaces()[0].getConnection());
    }

    @Test
    void testToString(){
        cable.linkDevices(controller, controller0);
        IPAddress ip = Mockito.mock(IPAddress.class);
        IPAddress ip0 = Mockito.mock(IPAddress.class);
        controller.getDevice().setIp_address(ip);
        controller0.getDevice().setIp_address(ip0);
        String string = cable.getName()  + " between " + controller.getDevice().toString()
                + " and " + controller0.getDevice().toString();
        assertEquals(string, cable.toString());
    }
}