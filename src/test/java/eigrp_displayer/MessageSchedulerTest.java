package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.NullMessage;
import eigrp_displayer.messages.RTPMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageSchedulerTest {
    RTPMessage message = Mockito.mock(RTPMessage.class);
    Network network = Mockito.mock(Network.class);
    CyclicMessage cyclicMessage = new CyclicMessage(message, 10);
    Device device = Mockito.mock(Device.class);
    DeviceController controller = new DeviceController(device);

    @BeforeEach
    void setUp(){
        MessageScheduler.getInstance().clear();
    }

    @Test
    void getNetwork() {
        assertEquals(new IPAddress(192,168,0,0) ,
                MessageScheduler.getInstance().getNetwork().getNetworkAddress());
        assertEquals(new IPAddress(192,168,0,255) ,
                MessageScheduler.getInstance().getNetwork().getBroadcastAddress());
        assertEquals(new Mask(24) ,MessageScheduler.getInstance().getNetwork().getMask());
        assertEquals(new ArrayList<>() ,MessageScheduler.getInstance().getNetwork().getDeviceControllers());
    }

    @Test
    void setNetwork() {
        MessageScheduler.getInstance().setNetwork(network);
        assertEquals(network, MessageScheduler.getInstance().getNetwork());
    }

    @Test
    void clear() {
        for(List<RTPMessage> list : MessageScheduler.getInstance().getSchedule()){
            for(RTPMessage message : list){
                assertTrue(message instanceof NullMessage);
            }
        }
    }

    @Test
    void getTicksToAnotherMessage() {

    }
}