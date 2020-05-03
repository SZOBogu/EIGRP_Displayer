package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EndDeviceTest {
    EndDevice endDevice = new EndDevice();
    @Test
    void testToString() {
        IPAddress ip = Mockito.mock(IPAddress.class);
        endDevice.setIp_address(ip);
        String string = "End Device (" + ip + ")";
    }
}