package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DeviceInterfaceTest {
    DeviceInterface deviceInterface0 = new DeviceInterface();
    DeviceInterface deviceInterface1 = new DeviceInterface("Test");
    Connection connection = new Cable();
    Device device0 = new EndDevice();
    Device device1 = new EndDevice();
    DeviceController controller0 = new DeviceController(device0);
    DeviceController controller1 = new DeviceController(device1);

    @Test
    void getName() {
        assertEquals("Any", deviceInterface0.getName());
        assertEquals("Test", deviceInterface1.getName());
    }

    @Test
    void getConnection() {
        assertNull(deviceInterface0.getConnection());
        assertNull(deviceInterface1.getConnection());
    }

    @Test
    void setConnection() {
        deviceInterface0.setConnection(connection);
        assertEquals(connection, deviceInterface0.getConnection());
    }

    @Test
    void checkIfOtherDeviceControllerConnected() {
        device0.setIp_address(Mockito.mock(IPAddress.class));
        device1.setIp_address(Mockito.mock(IPAddress.class));

        connection.linkDevices(controller0, controller1);
        assertTrue(controller0.getDevice().getDeviceInterfaces()[0].checkIfOtherDeviceControllerConnected(controller1));
        assertTrue(controller1.getDevice().getDeviceInterfaces()[0].checkIfOtherDeviceControllerConnected(controller0));
        assertFalse(controller1.getDevice().getDeviceInterfaces()[1].checkIfOtherDeviceControllerConnected(controller1));
    }

    @Test
    void getOtherDeviceController() {
        connection.linkDevices(controller0, controller1);
        assertEquals(controller0, controller0.getDevice().getDeviceInterfaces()[0].getOtherDeviceController(controller1));
        assertEquals(controller1, controller0.getDevice().getDeviceInterfaces()[0].getOtherDeviceController(controller0));
    }
}