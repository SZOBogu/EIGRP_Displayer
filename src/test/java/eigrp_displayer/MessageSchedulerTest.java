package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MessageSchedulerTest {
    Message message = Mockito.mock(Message.class);
    ShowcaseNetwork network = Mockito.mock(ShowcaseNetwork.class);
    CyclicMessage cyclicMessage = new CyclicMessage(message, 10);

    @BeforeEach
    void setUp(){
        MessageScheduler.getInstance().clear();
    }

    @Test
    void scheduleMessage() {
        MessageScheduler.getInstance().scheduleMessage(message);
        assertEquals(1, MessageScheduler.getInstance().getSchedule().get(0).size());
        assertEquals(message, MessageScheduler.getInstance().getSchedule().get(0).get(0));
        MessageScheduler.getInstance().scheduleMessage(message);
        assertEquals(2, MessageScheduler.getInstance().getSchedule().get(0).size());
    }

    @Test
    void scheduleMessageWithOffset() {
        MessageScheduler.getInstance().scheduleMessage(message, 5);
        assertEquals(1, MessageScheduler.getInstance().getSchedule().get(5).size());
        assertEquals(0, MessageScheduler.getInstance().getSchedule().get(4).size());
        assertEquals(message, MessageScheduler.getInstance().getSchedule().get(5).get(0));
        MessageScheduler.getInstance().scheduleMessage(message, 4);
        assertEquals(1, MessageScheduler.getInstance().getSchedule().get(4).size());
        assertEquals(message, MessageScheduler.getInstance().getSchedule().get(4).get(0));
    }

    @Test
    void scheduleCyclicMessage() {
        MessageScheduler.getInstance().scheduleCyclicMessage(cyclicMessage);
        for(int i = 0; i < MessageScheduler.getInstance().getSchedule().size(); i++){
            if(i % cyclicMessage.getInterval() == 0){
                if(MessageScheduler.getInstance().getSchedule().get(i).size() > 0)
                    assertEquals(message, MessageScheduler.getInstance().getSchedule().get(i).get(0));
            }
            else
                assertEquals(0, MessageScheduler.getInstance().getSchedule().get(i).size());
        }
    }

    @Test
    void scheduleCyclicMessageWithOffset() {
        int offset = 12;
        MessageScheduler.getInstance().scheduleCyclicMessage(cyclicMessage, offset);
        for(int i = 0; i < MessageScheduler.getInstance().getSchedule().size(); i++){
            if(i % cyclicMessage.getInterval() == (offset % cyclicMessage.getInterval())){
                if(MessageScheduler.getInstance().getSchedule().get(i).size() > 0)
                    assertEquals(message, MessageScheduler.getInstance().getSchedule().get(i).get(0));
            }
            else
                assertEquals(0, MessageScheduler.getInstance().getSchedule().get(i).size());
        }
    }

    @Test
    void getNetwork() {
        assertNull(MessageScheduler.getInstance().getNetwork());
    }

    @Test
    void setNetwork() {
        MessageScheduler.getInstance().setNetwork(network);
        assertEquals(network, MessageScheduler.getInstance().getNetwork());
    }

    @Test
    void clear() {
        for(List<Message> list : MessageScheduler.getInstance().getSchedule()){
            assertEquals(0, list.size());
        }
    }
}