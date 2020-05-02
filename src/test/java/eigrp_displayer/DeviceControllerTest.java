package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.HelloMessage;
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
            if(message  != null)
                fail();
        }
    }

    @Test
    void sendMessage() {
        init();
        HelloMessage message = new HelloMessage(ip0, ip1);
        HelloMessage message2 = new HelloMessage(ip0, ip1);
        controller0.sendMessage(message);
        assertEquals(message, controller0.getMessageSchedule().get(Clock.getTime()));
        assertEquals(10000, controller0.getMessageSchedule().size());
        controller0.sendMessage(message2);
        assertEquals(message2, controller0.getMessageSchedule().get(Clock.getTime() + 1));
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
        HelloMessage message1 = new HelloMessage(ip0, ip1);
        HelloMessage message2 = new HelloMessage(ip0, ip1);

        List<RTPMessage> messageList = new ArrayList<>(Arrays.asList(message0, message1, message2));
        controller0.sendMessages(messageList);
        assertEquals(message0, controller0.getMessageSchedule().get(Clock.getTime()));
        assertEquals(message1, controller0.getMessageSchedule().get(Clock.getTime() + 1));
        assertEquals(message2, controller0.getMessageSchedule().get(Clock.getTime() + 2));

        for(int i = Clock.getTime() + 3; i < controller0.getMessageSchedule().size(); i++){
            assertNull(controller0.getMessageSchedule().get(i));
        }
        assertEquals(10000, controller0.getMessageSchedule().size());
    }

    @Test
    void sendMessagesWithOffset() {
        init();
        HelloMessage message0 = new HelloMessage(ip0, ip1);
        HelloMessage message1 = new HelloMessage(ip1, ip1);
        HelloMessage message2 = new HelloMessage(ip0, ip1);
        int offset = 20;

        List<RTPMessage> messageList = new ArrayList<>(Arrays.asList(message0, message1, message2));
        controller0.sendMessages(messageList, offset);
        assertEquals(message0, controller0.getMessageSchedule().get(Clock.getTime() + offset));
        assertEquals(message1, controller0.getMessageSchedule().get(Clock.getTime() + offset + 1));
        assertEquals(message2, controller0.getMessageSchedule().get(Clock.getTime() + offset + 2));
        for(int i = Clock.getTime() + offset + 3; i < controller0.getMessageSchedule().size(); i++){
            assertNull(controller0.getMessageSchedule().get(i));
        }
        assertEquals(10000, controller0.getMessageSchedule().size());
    }

    @Test
    void sendCyclicMessage() {
        init();
        HelloMessage message0 = new HelloMessage(ip0, ip1);
        int interval = 15;
        CyclicMessage cyclicMessage = new CyclicMessage(message0, interval);
        controller0.sendCyclicMessage(cyclicMessage);
        for(int i = 0; i < controller0.getMessageSchedule().size(); i++){
            if(i % interval == 0)
                assertEquals(message0, controller0.getMessageSchedule().get(i));
            else
                assertNull(controller0.getMessageSchedule().get(i));
        }
    }

    @Test
    void sendCyclicMessageWithOffset() {
        init();
        HelloMessage message0 = new HelloMessage(ip0, ip1);
        int interval = 15;
        int offset = 3;
        CyclicMessage cyclicMessage = new CyclicMessage(message0, interval);
        controller0.sendCyclicMessage(cyclicMessage, offset);
        for(int i = offset; i < controller0.getMessageSchedule().size(); i++){
            if(((i % interval) == offset))
                assertEquals(message0, controller0.getMessageSchedule().get(i));
            else
                assertNull(controller0.getMessageSchedule().get(i));
        }
    }

    @Test
    void scheduleHellos() {
        //sometimes doesn't work, randomness comes from random offset in device
        init();
        List<IPAddress> ips = new ArrayList<>();
        for(DeviceController controller : controller0.getAllConnectedDeviceControllers()){
            ips.add(controller.getDevice().getIp_address());
        }
        assertEquals(2, ips.size());

        for(int i = 0; i < 120; i++) {
            int offset;

            if (i == 0)
                offset = 0;
            else if(i < 60)
                offset = i;
            else
                offset = i % 60;

            controller0.getDevice().setMessageSendingTimeOffset(i);
            controller0.scheduleHellos();
            for (int j = i; j < controller0.getMessageSchedule().size(); j++) {
                if (((j % 60) == offset || ((j % 60) == offset + 1))) {
                    assertTrue(controller0.getMessageSchedule().get(j) instanceof HelloMessage,
                            "Not hello: " + j + " offset: " + offset);
                }
            }
            controller0.clearSchedule();
        }
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

    @Test
    void clearSchedule() {
        init();
        HelloMessage message = new HelloMessage(ip0, ip1);
        int offset = 10;
        controller0.sendMessage(message, offset);
        assertEquals(message, controller0.getMessageSchedule().get(Clock.getTime() + offset));
        assertEquals(10000, controller0.getMessageSchedule().size());

        controller0.clearSchedule();
        for(int i = 0; i < controller0.getMessageSchedule().size(); i++){
            assertNull(controller0.getMessageSchedule().get(i));
        }
    }

    @Test
    void addSelfToScheduler() {
    }
}