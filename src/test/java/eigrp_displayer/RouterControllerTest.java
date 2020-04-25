package eigrp_displayer;

import eigrp_displayer.messages.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RouterControllerTest {
    RouterController manager = new RouterController();

    Router router = Mockito.mock(Router.class);
    ACKMessage ack = Mockito.mock(ACKMessage.class);
    HelloMessage hello = Mockito.mock(HelloMessage.class);
    QueryMessage query = Mockito.mock(QueryMessage.class);
    ReplyMessage reply = Mockito.mock(ReplyMessage.class);
    UpdateMessage update = Mockito.mock(UpdateMessage.class);
    NullMessage nullMessage = Mockito.mock(NullMessage.class);

    @Test
    void sendMessage() {

    }

    @Test
    void receiveMessage() {
    }

    @Test
    void respond() {

    }
}