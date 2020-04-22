package eigrp_displayer.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NullMessageTest {
    NullMessage nullMessage = new NullMessage();
    @Test
    void getSenderAddress() {
        assertNull(nullMessage.getSenderAddress());
    }

    @Test
    void getReceiverAddress() {
        assertNull(nullMessage.getReceiverAddress());
    }
}