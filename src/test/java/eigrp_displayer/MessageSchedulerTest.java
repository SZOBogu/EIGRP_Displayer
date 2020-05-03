package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.RTPMessage;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MessageSchedulerTest {
    RTPMessage message = Mockito.mock(RTPMessage.class);
    Network network = Mockito.mock(Network.class);
    CyclicMessage cyclicMessage = new CyclicMessage(message, 10);
    Device device = new EndDevice();
    DeviceController controller = new DeviceController(device);
    Router router = new Router("Rtest");
    RouterController controller0 = new RouterController(router);
    Connection con = new Cable();
    MessageScheduler scheduler = MessageScheduler.getInstance();

    @BeforeEach
    void init(){
        device.setIp_address(Mockito.mock(IPAddress.class));
        router.setIp_address(Mockito.mock(IPAddress.class));
        con.linkDevices(controller, controller0);
        controller.addSelfToScheduler();
        controller0.addSelfToScheduler();
        scheduler.clear();
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
        controller.scheduleHellos();
        scheduler.clear();

        assertEquals(2, scheduler.getSchedule().size());
        assertEquals(10000, scheduler.getSchedule().get(controller).size());
        assertEquals(10000, scheduler.getSchedule().get(controller0).size());

        for(int i = 0; i < scheduler.getSchedule().get(controller).size(); i++){
            assertNull(scheduler.getSchedule().get(controller).get(i));
        }
        for(int i = 0; i < scheduler.getSchedule().get(controller0).size(); i++){
            assertNull(scheduler.getSchedule().get(controller0).get(i));
        }
    }

    @Test
    void getTicksToAnotherMessage() {
        controller.getDevice().setMessageSendingTimeOffset(0);
        controller0.getDevice().setMessageSendingTimeOffset(100);

        controller.scheduleHellos();
        controller0.scheduleHellos();

        assertEquals(100, MessageScheduler.getInstance().getTicksToAnotherMessage());
    }

    @Test
    void getSchedule() {
        assertEquals(2, MessageScheduler.getInstance().getSchedule().size());
    }

    @Test
    void updateTime() {

    }

    @AfterEach
    void tearDown(){
        scheduler.getSchedule().clear();
    }
}