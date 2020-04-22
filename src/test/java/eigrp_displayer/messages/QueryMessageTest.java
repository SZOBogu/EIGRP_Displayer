package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class QueryMessageTest {
    IPAddress sender = Mockito.mock(IPAddress.class);
    IPAddress receiver = Mockito.mock(IPAddress.class);
    IPAddress queriedDeviceAddress = Mockito.mock(IPAddress.class);
    QueryMessage query = new QueryMessage(sender, receiver, queriedDeviceAddress);

    @Test
    void getQueriedDeviceAddress() {
        assertEquals(queriedDeviceAddress, query.getQueriedDeviceAddress());
    }
}