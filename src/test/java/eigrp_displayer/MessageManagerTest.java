package eigrp_displayer;

import eigrp_displayer.messages.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MessageManagerTest {
    MessageManager manager = new MessageManager();

    Router router = Mockito.mock(Router.class);
    ACK ack = Mockito.mock(ACK.class);
    Hello hello = Mockito.mock(Hello.class);
    Query query = Mockito.mock(Query.class);
    Reply reply = Mockito.mock(Reply.class);
    Update update = Mockito.mock(Update.class);
    Placeholder placeholder = Mockito.mock(Placeholder.class);

    @Test
    void sendMessage() {

    }

    @Test
    void receiveMessage() {
    }

    @Test
    void respond() {
        //deleting message from list of messages that need response
        manager.respond(ack, router);
        //resetting ticks to timeout on router table entry with ip of sender
        manager.respond(hello, router);
        //ack + updating routing info (?)
        manager.respond(update, router);
        //responding with info about asked routing table entry
        manager.respond(reply, router);
        //ack + responding with info about asked ip address
        manager.respond(query, router);
        //incrementing ticks to timeout on router table entry with ip of sender
        manager.respond(placeholder, router);
    }
}