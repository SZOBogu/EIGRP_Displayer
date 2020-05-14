package eigrp_displayer;

import eigrp_displayer.messages.HelloMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class EventLogTest {
    IPAddress ip = Mockito.mock(IPAddress.class);
    EndDevice device = new EndDevice();

    DeviceController controller = new DeviceController(device);
    IPAddress ipR = Mockito.mock(IPAddress.class);

    IPAddress ip0 = Mockito.mock(IPAddress.class);
    EndDevice device0 = new EndDevice();
    DeviceController controller0 = new DeviceController(device0);


    @BeforeEach
    void setUp() {
        EventLog.clear();;
        device.setIp_address(ip);
        device0.setIp_address(ip0);
    }

    @AfterEach
    void tearDown() {
        EventLog.clear();;
    }

    @Test
    void getInstance() {
        EventLog eventLog1 = EventLog.getInstance();
        EventLog eventLog2 = EventLog.getInstance();

        assertEquals(eventLog1, eventLog2);
    }

    @Test
    void appendLog() {
        EventLog.appendLog("test");
        assertEquals("test\n", EventLog.getEventLog());
    }

    @Test
    void getEventLog() {
        assertEquals("", EventLog.getEventLog());
    }

    @Test
    void clear() {
        EventLog.appendLog("na");
        EventLog.appendLog("jeziorze");
        EventLog.appendLog("wielka");
        EventLog.appendLog("burza");
        EventLog.appendLog("jezus");
        EventLog.appendLog("ze");
        EventLog.appendLog("mna");
        EventLog.appendLog("w lodzi");
        EventLog.appendLog("jest");

        assertNotEquals("", EventLog.getEventLog());
        EventLog.clear();
        assertEquals("", EventLog.getEventLog());
    }

    @Test
    void messageSent() {
        HelloMessage helloMessage = new HelloMessage(ip, ipR);

        EventLog.messageSent(controller, helloMessage);

        String string = Clock.getTime() + ": HelloMessage sent from End Device (" + ip + ") to " + ipR + "\n";
        assertEquals(string, EventLog.getEventLog());
    }

    @Test
    void messageReceived() {
        HelloMessage helloMessage = new HelloMessage(ipR, ip);
        String string = Clock.getTime() + ": " + controller.getDevice().toString() + " received "
                + helloMessage.getClass().getSimpleName() + " from " + ipR + "\n";
        EventLog.messageReceived(controller, helloMessage);
        assertEquals(string, EventLog.getEventLog());
    }

    @Test
    void connectionChanged() {
        Connection connection = new Cable();

        connection.linkDevices(controller, controller0);

        EventLog.connectionChanged(connection);
        String string = Clock.getTime() + ": " + connection + " has been changed.\n";
        assertEquals(string, EventLog.getEventLog());
    }

    @Test
    void deviceConnected() {
        Connection connection = new Cable();

        connection.linkDevices(controller, controller0);

        String string = Clock.getTime() + ": " + controller.getDevice().toString()
                + " connected by " + connection.toString() + "\n";
        EventLog.deviceConnected(controller, connection);
        assertEquals(string, EventLog.getEventLog());
    }

    @Test
    void deviceChanged() {
        EventLog.deviceChanged(controller);
        String string = Clock.getTime() + ": " + controller.getDevice().toString()
                + " has been changed.\n";
        assertEquals(string, EventLog.getEventLog());

    }

    @Test
    void deviceUnreachable() {
        EventLog.deviceUnreachable(controller);
        String string = Clock.getTime() + ": " + controller.getDevice() + " became unreachable.\n";
        assertEquals(string, EventLog.getEventLog());
    }

    @Test
    void neighbourshipFormed() {
        EventLog.neighbourshipFormed(controller, controller0);
        String string = Clock.getTime() + ": " + controller.getDevice().toString() + " and "
                + controller0.getDevice().toString() + " became neighbours.\n";
        assertEquals(string, EventLog.getEventLog());
    }

    @Test
    void neighbourshipBroken() {
        EventLog.neighbourshipBroken(controller, controller0);
        String string = Clock.getTime() + ": " + "Neighbourship between "
                + controller.getDevice() + " and " + controller0.getDevice() + " broken.\n";
        assertEquals(string, EventLog.getEventLog());
    }
}