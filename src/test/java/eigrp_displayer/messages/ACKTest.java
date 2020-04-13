package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ACKTest {
    IPAddress sender = Mockito.mock(IPAddress.class);
    IPAddress receiver = Mockito.mock(IPAddress.class);
    ACK ack = new ACK(sender, receiver);

    @Test
    void getSenderAddress() {
        assertEquals(sender, ack.getSenderAddress());
    }

    @Test
    void getReceiverAddress() {
        assertEquals(receiver, ack.getReceiverAddress());
    }
}