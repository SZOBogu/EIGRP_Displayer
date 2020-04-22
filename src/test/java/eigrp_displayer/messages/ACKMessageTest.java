package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ACKMessageTest {
    IPAddress sender = Mockito.mock(IPAddress.class);
    IPAddress receiver = Mockito.mock(IPAddress.class);
    ACKMessage ack = new ACKMessage(sender, receiver);

    @Test
    void getSenderAddress() {
        assertEquals(sender, ack.getSenderAddress());
    }

    @Test
    void getReceiverAddress() {
        assertEquals(receiver, ack.getReceiverAddress());
    }
}