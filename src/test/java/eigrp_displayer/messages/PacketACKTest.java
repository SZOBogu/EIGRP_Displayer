package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacketACKTest {

    @Test
    void constructor() {
        IPAddress ipSender = Mockito.mock(IPAddress.class);
        IPAddress ipReceiver = Mockito.mock(IPAddress.class);

        Packet packet = new Packet(ipSender, ipReceiver, 0);

        PacketACK packetACK = new PacketACK(packet);

        assertEquals(ipReceiver, packetACK.getSenderAddress());
        assertEquals(ipReceiver, packetACK.getReceiverAddress());
        assertEquals(ipSender, packetACK.getTargetAddress());
        assertEquals(0, packetACK.getPacketNumber());
    }
}