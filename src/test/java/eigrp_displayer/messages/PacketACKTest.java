package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacketACKTest {

    @Test
    void constructor() {
        IPAddress ipSource = Mockito.mock(IPAddress.class);
        IPAddress ipSender = Mockito.mock(IPAddress.class);
        IPAddress ipReceiver = Mockito.mock(IPAddress.class);
        IPAddress ipTarget = Mockito.mock(IPAddress.class);

        Packet packet = new Packet(ipSource, ipSender, ipReceiver, ipTarget, 0);

        PacketACK packetACK = new PacketACK(packet);

        assertEquals(ipTarget, packetACK.getSenderAddress());
        assertEquals(ipTarget, packetACK.getSenderAddress());
        assertEquals(ipSender, packetACK.getReceiverAddress());
        assertEquals(ipSource, packetACK.getTargetAddress());
        assertEquals(0, packetACK.getPacketNumber());
    }
}