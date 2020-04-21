package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NetworkTest {
    Network network = new Network();
    Mask mask = Mockito.mock(Mask.class);
    IPAddress ip = Mockito.mock(IPAddress.class);

    @Test
    void getMask() {
        assertNull(network.getMask());
    }

    @Test
    void setMask() {
        network.setMask(mask);
        assertEquals(mask, network.getMask());
    }

    @Test
    void getNetworkAddress() {
        assertNull(network.getNetworkAddress());
    }

    @Test
    void setNetworkAddress() {
        network.setNetworkAddress(ip);
        assertEquals(ip, network.getNetworkAddress());
    }
}