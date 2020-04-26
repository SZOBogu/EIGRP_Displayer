package eigrp_displayer;

import eigrp_displayer.messages.HelloMessage;
import eigrp_displayer.messages.NullMessage;
import eigrp_displayer.messages.RTPMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @BeforeEach
    void resetClock(){
        Clock.resetClock();
    }

    void init(){
        connection0.linkDevices(controller0, controller1);
        connection1.linkDevices(controller1, controller2);
        connection2.linkDevices(controller2, controller0);

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
        assertEquals(10000, controller0.getMessageSchedule().size());
        for(RTPMessage message : controller0.getMessageSchedule()){
            if(!(message instanceof NullMessage))
                fail();
        }
    }

    @Test
    void sendMessage() {
        init();
        HelloMessage message = new HelloMessage(ip0, ip1);
        controller0.sendMessage(message);
        assertEquals(message, controller0.getMessageSchedule().get(Clock.getTime()));
        assertEquals(10000, controller0.getMessageSchedule().size());

    }

    @Test
    void sendMessageWithOffset() {
        init();
        HelloMessage message = new HelloMessage(ip0, ip1);
        int offset = 10;
        controller0.sendMessage(message, offset);
        assertEquals(message, controller0.getMessageSchedule().get(Clock.getTime() + offset));
        assertEquals(10000, controller0.getMessageSchedule().size());
    }

    @Test
    void sendMessages() {
        init();
        HelloMessage message0 = new HelloMessage(ip0, ip1);
        HelloMessage message1 = new HelloMessage(ip1, ip0);
        HelloMessage message2 = new HelloMessage(ip0, ip0);

        List<RTPMessage> messageList = new ArrayList<>(Arrays.asList(message0, message1, message2));
        controller0.sendMessages(messageList);
        assertEquals(message0, controller0.getMessageSchedule().get(Clock.getTime()));
        assertEquals(message1, controller0.getMessageSchedule().get(Clock.getTime() + 1));
        assertEquals(message2, controller0.getMessageSchedule().get(Clock.getTime() + 2));
        assertEquals(10000, controller0.getMessageSchedule().size());

    }

    @Test
    void sendMessagesWithOffset() {
        init();
        HelloMessage message0 = new HelloMessage(ip0, ip1);
        HelloMessage message1 = new HelloMessage(ip1, ip0);
        HelloMessage message2 = new HelloMessage(ip0, ip0);
        int offset = 20;

        List<RTPMessage> messageList = new ArrayList<>(Arrays.asList(message0, message1, message2));
        controller0.sendMessages(messageList);
        assertEquals(message0, controller0.getMessageSchedule().get(Clock.getTime() + offset));
        assertEquals(message1, controller0.getMessageSchedule().get(Clock.getTime() + offset + 1));
        assertEquals(message2, controller0.getMessageSchedule().get(Clock.getTime() + offset + 2));
        assertEquals(10000, controller0.getMessageSchedule().size());
    }

    @Test
    void sendCyclicMessage() {

    }

    @Test
    void sendCyclicMessageWithOffset() {

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