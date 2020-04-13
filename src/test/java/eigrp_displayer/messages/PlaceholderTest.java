package eigrp_displayer.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceholderTest {
    Placeholder placeholder = new Placeholder();
    @Test
    void getSenderAddress() {
        assertNull(placeholder.getSenderAddress());
    }

    @Test
    void getReceiverAddress() {
        assertNull(placeholder.getReceiverAddress());
    }
}