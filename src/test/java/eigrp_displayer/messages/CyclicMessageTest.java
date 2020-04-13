package eigrp_displayer.messages;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CyclicMessageTest {
    RTPMessage message = Mockito.mock(RTPMessage.class);
    CyclicMessage cyclicMessage = new CyclicMessage(message, 10);

    @Test
    void getMessage() {
        assertEquals(message, cyclicMessage.getMessage());
    }
    @Test
    void getInterval() {
        assertEquals(10, cyclicMessage.getInterval());
    }
}