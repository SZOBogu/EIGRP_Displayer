package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DeviceControllerTest {
    EndDevice device0 = new EndDevice();
    EndDevice device1 = new EndDevice();
    EndDevice device2 = new EndDevice();
    IPAddress ip0 = Mockito.mock(IPAddress.class);
    IPAddress ip1 = Mockito.mock(IPAddress.class);
    IPAddress ip2 = Mockito.mock(IPAddress.class);

    DeviceController controller0 = new DeviceController(device0);
    DeviceController controller1 = new DeviceController(device1);
    DeviceController controller2 = new DeviceController(device2);

    Connection connection0 = new Cable();
    Connection connection1 = new Cable();
    Connection connection2 = new Cable();

    void init(){
        connection0.linkDevice(controller0);
        connection0.linkDevice(controller1);
        connection1.linkDevice(controller1);
        connection1.linkDevice(controller2);
        connection2.linkDevice(controller2);
        connection2.linkDevice(controller0);


        device0.setIp_address(ip0);
        device1.setIp_address(ip1);
        device2.setIp_address(ip2);

        controller0.setConnection(connection0);
        controller0.setConnection(connection2);
        controller1.setConnection(connection0);
        controller1.setConnection(connection1);
        controller2.setConnection(connection1);
    }

    @Test
    void getDevice() {
        assertEquals(device0, controller0.getDevice());
    }

    @Test
    void setDevice() {
        controller0.setDevice(device1);
        assertEquals(device1, controller0.getDevice());
    }

    @Test
    void getMessageSchedule() {
    }

    @Test
    void sendMessage() {
    }

    @Test
    void sendMessages() {
    }

    @Test
    void sendCyclicMessage() {
    }

    @Test
    void testSendMessage() {
    }

    @Test
    void testSendMessages() {
    }

    @Test
    void testSendCyclicMessage() {
    }

    @Test
    void scheduleHellos() {
    }

    @Test
    void respond() {
    }

    @Test
    void getAllConnectedDeviceControllers() {
        init();
        assertEquals(2, controller0.getAllConnectedDeviceControllers().size());
        assertEquals(controller1, controller0.getAllConnectedDeviceControllers().get(0));
        assertEquals(controller2, controller0.getAllConnectedDeviceControllers().get(1));
    }

    @Test
    void getConnectedDeviceController() {
        init();
        assertEquals(controller1, controller0.getConnectedDeviceController(ip1));
        assertEquals(controller2, controller0.getConnectedDeviceController(ip2));
    }

    @Test
    void getConnectionWithDeviceController() {
        init();
        assertEquals(connection0, controller0.getConnectionWithDeviceController(
                controller1));
    }

    @Test
    void updateMetric() {
    }

    @Test
    void setConnection(){
        controller0.setConnection(connection0);
        assertEquals(connection0, controller0.getDevice().getDeviceInterfaces()[0].getConnection());
        assertNull(controller0.getDevice().getDeviceInterfaces()[1].getConnection());
        assertNull(controller0.getDevice().getDeviceInterfaces()[2].getConnection());
        assertNull(controller0.getDevice().getDeviceInterfaces()[3].getConnection());

        controller0.setConnection(connection1);
        assertEquals(connection0, controller0.getDevice().getDeviceInterfaces()[0].getConnection());
        assertEquals(connection1, controller0.getDevice().getDeviceInterfaces()[1].getConnection());
        assertNull(controller0.getDevice().getDeviceInterfaces()[2].getConnection());
        assertNull(controller0.getDevice().getDeviceInterfaces()[3].getConnection());
    }
}