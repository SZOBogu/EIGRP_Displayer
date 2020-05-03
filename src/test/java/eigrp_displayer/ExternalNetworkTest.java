package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ExternalNetworkTest {
    ExternalNetwork externalNetwork = new ExternalNetwork();
    Mask mask = Mockito.mock(Mask.class);
    IPAddress ip = Mockito.mock(IPAddress.class);

    @Test
    void getMask() {
        assertNull(externalNetwork.getMask());
    }

    @Test
    void setMask() {
        externalNetwork.setMask(mask);
        assertEquals(mask, externalNetwork.getMask());
    }

    @Test
    void getIPAddress() {
        assertNull(externalNetwork.getIp_address());
    }

    @Test
    void setIPAddress() {
        externalNetwork.setIp_address(ip);
        assertEquals(ip, externalNetwork.getIp_address());
    }

    @Test
    void getDeviceInterfaces() {
        assertEquals(100, externalNetwork.getDeviceInterfaces().length);
    }

    @Test
    void testToString(){
        externalNetwork.setIp_address(ip);
        String string = "External Network (" + ip + ")";
        assertEquals(string, externalNetwork.toString());
    }
}