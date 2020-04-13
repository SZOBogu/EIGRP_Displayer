package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {
    IPAddress sender = Mockito.mock(IPAddress.class);
    IPAddress receiver = Mockito.mock(IPAddress.class);
    IPAddress queriedDeviceAddress = Mockito.mock(IPAddress.class);
    Query query = new Query(sender, receiver, queriedDeviceAddress);

    @Test
    void getQueriedDeviceAddress() {
        assertEquals(queriedDeviceAddress, query.getQueriedDeviceAddress());
    }
}