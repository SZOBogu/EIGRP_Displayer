package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DeviceInterfaceTest {
    DeviceInterface deviceInterface0 = new DeviceInterface();
    DeviceInterface deviceInterface1 = new DeviceInterface("Test");
    Connection connection = new Cable();
    Device device0 = Mockito.mock(Device.class);
    Device device1 = Mockito.mock(Device.class);
    DeviceController controller0 = new DeviceController(device0);
    DeviceController controller1 = new DeviceController(device1);

    void init(){
        connection.linkDevices(controller0, controller1);
        deviceInterface0.setConnection(connection);
    }

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
        init();
        assertTrue(deviceInterface0.checkIfOtherDeviceControllerConnected(controller0));
        assertTrue(deviceInterface0.checkIfOtherDeviceControllerConnected(controller1));
        assertFalse(deviceInterface1.checkIfOtherDeviceControllerConnected(controller1));
    }

    @Test
    void getOtherDeviceController() {
        init();
        assertEquals(controller0, deviceInterface0.getOtherDeviceController(controller1));
        assertEquals(controller0, deviceInterface0.getOtherDeviceController(controller1));
        assertNull(deviceInterface1.getOtherDeviceController(controller1));
    }
}