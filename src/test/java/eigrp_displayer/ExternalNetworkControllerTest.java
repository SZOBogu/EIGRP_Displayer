package eigrp_displayer;

import eigrp_displayer.messages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExternalNetworkControllerTest {
    ExternalNetwork device0 = new ExternalNetwork();
    ExternalNetwork device1 = new ExternalNetwork();
    ExternalNetwork device2 = new ExternalNetwork();
    IPAddress ip0 = Mockito.mock(IPAddress.class);
    IPAddress ip1 = Mockito.mock(IPAddress.class);
    IPAddress ip2 = Mockito.mock(IPAddress.class);

    ExternalNetworkController controller0 = new ExternalNetworkController(device0);
    ExternalNetworkController controller1 = new ExternalNetworkController(device1);
    ExternalNetworkController controller2 = new ExternalNetworkController(device2);

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
    void scheduleHellos() {
        init();
        PacketTargetModel packetTargetModel0 = new PacketTargetModel(ip2, ip2);
        PacketTargetModel packetTargetModel1 = new PacketTargetModel(ip1, ip1);

        controller0.addPacketTargetModel(packetTargetModel0);
        controller0.addPacketTargetModel(packetTargetModel1);

        controller0.scheduleCyclicMessages();
        List<Message> messages = controller0.getMessageSchedule();

        assertTrue((messages.get(Clock.getTime() + 500) instanceof Packet) ||
                (messages.get(Clock.getTime() + 501) instanceof Packet));
        assertTrue((messages.get(Clock.getTime() + 510) instanceof Packet) ||
                (messages.get(Clock.getTime() + 511) instanceof Packet));
        assertTrue((messages.get(Clock.getTime() + 760) instanceof Packet) ||
                (messages.get(Clock.getTime() + 761) instanceof Packet));
    }

    @Test
    void respondPacket() {
        init();
        Packet packet = new Packet(ip0, ip1, 0);
        controller0.respond(packet);

        for(int i = 0; i < controller0.getMessageSchedule().size(); i++) {
            assertNull(controller0.getMessageSchedule().get(i));
        }
    }

    @Test
    void respondPacketAck() {
        init();

        PacketTargetModel packetTargetModel0 = new PacketTargetModel(ip2, ip2);
        PacketTargetModel packetTargetModel1 = new PacketTargetModel(ip1, ip1);

        controller0.addPacketTargetModel(packetTargetModel0);
        controller0.addPacketTargetModel(packetTargetModel1);

        Packet packet = new Packet(ip0, ip1, 0);
        PacketACK packetAck = new PacketACK(packet);
        controller0.respond(packetAck);

        assertEquals(0, controller0.getPacketTargetModel(0).getAckedMessageNumberSet().size());
        assertEquals(1, controller0.getPacketTargetModel(1).getAckedMessageNumberSet().size());
        assertFalse(controller0.getPacketTargetModel(0).getAckedMessageNumberSet().contains(0));
        assertTrue(controller0.getPacketTargetModel(1).getAckedMessageNumberSet().contains(0));
    }

    @Test
    void respondAck() {
        init();
        ACKMessage ack = new ACKMessage(ip0, ip1);
        controller0.respond(ack);

        for(int i = 0; i < controller0.getMessageSchedule().size(); i++) {
            assertNull(controller0.getMessageSchedule().get(i));
        }
    }

    @Test
    void respondHello() {
        init();
        HelloMessage hello = new HelloMessage(ip0, ip1);
        controller0.respond(hello);

        for(int i = 0; i < controller0.getMessageSchedule().size(); i++) {
            assertNull(controller0.getMessageSchedule().get(i));
        }
    }

    @Test
    void respondQuery() {
        init();
        QueryMessage query = new QueryMessage(ip0, ip1, ip2);
        controller0.respond(query);

        for(int i = 0; i < controller0.getMessageSchedule().size(); i++) {
            assertNull(controller0.getMessageSchedule().get(i));
        }
    }

    @Test
    void respondReply() {
        init();
        ReplyMessage reply = new ReplyMessage(ip0, ip1, Mockito.mock(RoutingTableEntry.class));
        controller0.respond(reply);

        for(int i = 0; i < controller0.getMessageSchedule().size(); i++) {
            assertNull(controller0.getMessageSchedule().get(i));
        }
    }

    @Test
    void respondUpdate() {
        init();
        UpdateMessage update = new UpdateMessage(ip0, ip1, Mockito.mock(TopologyTable.class));
        controller0.respond(update);

        for(int i = 0; i < controller0.getMessageSchedule().size(); i++) {
            assertNull(controller0.getMessageSchedule().get(i));
        }
    }
}