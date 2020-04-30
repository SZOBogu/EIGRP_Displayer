package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PremadeNetworkTest {

    @Test
    void getNetwork() {
        Network network = PremadeNetwork.getNetwork();

        DeviceController endDeviceController0 = network.getDeviceControllers().get(0);
        DeviceController endDeviceController1 = network.getDeviceControllers().get(1);
        RouterController routerController0 = (RouterController) network.getDeviceControllers().get(2);
        RouterController routerController1 = (RouterController) network.getDeviceControllers().get(3);
        RouterController routerController2 = (RouterController) network.getDeviceControllers().get(4);

        assertEquals(5, network.getDeviceControllers().size());
        assertEquals(new IPAddress(192,168,0,1),
                endDeviceController0.getDevice().getIp_address());
        assertEquals(new IPAddress(192,168,0,2),
                endDeviceController1.getDevice().getIp_address());
        assertEquals(new IPAddress(192,168,0,3),
                routerController0.getDevice().getIp_address());
        assertEquals(new IPAddress(192,168,0,4),
                routerController1.getDevice().getIp_address());
        assertEquals(new IPAddress(192,168,0,5),
                routerController2.getDevice().getIp_address());

        Connection con0 = routerController0.getInterface(new IPAddress(10,0,0,1)).getConnection();
        Connection con1 = routerController0.getInterface(routerController1.getDevice().getIp_address()).getConnection();
        Connection con2 = routerController0.getInterface(routerController2.getDevice().getIp_address()).getConnection();
        Connection con3 = routerController1.getInterface(endDeviceController0.getDevice().getIp_address()).getConnection();
        Connection con4 = routerController2.getInterface(routerController1.getDevice().getIp_address()).getConnection();
        Connection con5 = routerController2.getInterface(endDeviceController1.getDevice().getIp_address()).getConnection();

        assertTrue(con0.getDevice1().getDevice() instanceof ExternalNetwork);
        assertEquals(con0.getDevice2(), routerController0);

        assertEquals(con1.getDevice1(), routerController0);
        assertEquals(con1.getDevice2(), routerController1);

        assertEquals(con2.getDevice1(), routerController0);
        assertEquals(con2.getDevice2(), routerController2);

        assertEquals(con3.getDevice1(), routerController1);
        assertEquals(con3.getDevice2(), endDeviceController0);

        assertEquals(con4.getDevice1(), routerController1);
        assertEquals(con4.getDevice2(), routerController2);

        assertEquals(con5.getDevice1(), routerController2);
        assertEquals(con5.getDevice2(), endDeviceController1);
    }
}