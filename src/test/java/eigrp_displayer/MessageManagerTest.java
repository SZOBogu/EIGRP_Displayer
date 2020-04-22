package eigrp_displayer;

import eigrp_displayer.messages.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MessageManagerTest {
    MessageManager manager = new MessageManager();

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
//        //deleting message from list of messages that need response
//        manager.respond(ack, router);
//        //resetting ticks to timeout on router table entry with ip of sender
//        manager.respond(hello, router);
//        //ack + updating routing info (?)
//        manager.respond(update, router);
//        //responding with info about asked routing table entry
//        manager.respond(reply, router);
//        //ack + responding with info about asked ip address
//        manager.respond(query, router);
//        //incrementing ticks to timeout on router table entry with ip of sender
//        manager.respond(placeholder, router);
    }
}