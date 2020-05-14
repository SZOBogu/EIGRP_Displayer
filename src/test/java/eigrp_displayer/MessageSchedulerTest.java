package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.HelloMessage;
import eigrp_displayer.messages.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MessageSchedulerTest {
    Message message = Mockito.mock(Message.class);
    Network network = Mockito.mock(Network.class);
    CyclicMessage cyclicMessage = new CyclicMessage(message, 10);
    Device device = new EndDevice();
    IPAddress ip = Mockito.mock(IPAddress.class);
    DeviceController controller = new DeviceController(device);
    Router router = new Router("Rtest");
    IPAddress ip0 = Mockito.mock(IPAddress.class);
    RouterController controller0 = new RouterController(router);
    Connection con = new Cable();
    MessageScheduler scheduler = MessageScheduler.getInstance();

    @BeforeEach
    void init(){
        device.setIp_address(ip);
        router.setIp_address(ip0);
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

        assertEquals(2, scheduler.getMessageSchedules().size());
        assertEquals(10000, scheduler.getMessageSchedules().get(0).size());
        assertEquals(10000, scheduler.getMessageSchedules().get(1).size());

        for(int i = 0; i < scheduler.getMessageSchedules().size(); i++){
            assertNull(scheduler.getMessageSchedules().get(0).get(i));
        }
        for(int i = 0; i < scheduler.getMessageSchedules().size(); i++){
            assertNull(scheduler.getMessageSchedules().get(1).get(i));
        }
    }

    @Test
    void getTicksToAnotherMessage() {
        controller.getDevice().setMessageSendingTimeOffset(10);
        controller0.getDevice().setMessageSendingTimeOffset(100);

        controller.scheduleHellos();
        controller0.scheduleHellos();

        assertEquals(90, MessageScheduler.getInstance().getTicksToAnotherMessage());

        HelloMessage hC0 = new HelloMessage(ip, ip0);
        HelloMessage hC1 = new HelloMessage(ip0, ip);
        controller.sendMessage(hC0);
        controller0.sendMessage(hC1,1);

        assertEquals(1, MessageScheduler.getInstance().getTicksToAnotherMessage());
    }

    @Test
    void getMessageSchedules() {
        assertEquals(2, MessageScheduler.getInstance().getMessageSchedules().size());
    }

    @Test
    void updateTime() {

    }

    @AfterEach
    void tearDown(){
        scheduler.getMessageSchedules().clear();
    }

    @Test
    void getControllers() {
    }
}