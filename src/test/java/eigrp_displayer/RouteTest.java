package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteTest {
    Route route = new Route();
    IP_Address address = Mockito.mock(IP_Address.class);
    IP_Address address0 = Mockito.mock(IP_Address.class);

    @BeforeEach
    void init(){
        route.setIp_address(address);
        route.setFeasibleDistance(10);
        route.setReportedDistance(1);
        route.setConnectionType("Any");
    }

    @Test
    void getIp_address() {
        assertEquals(address, route.getIp_address());
    }

    @Test
    void setIp_address() {
        route.setIp_address(address0);
        assertEquals(address0, route.getIp_address());
    }

    @Test
    void getFeasibleDistance() {
        assertEquals(10, route.getFeasibleDistance());
    }

    @Test
    void setFeasibleDistance() {
        route.setFeasibleDistance(111);
        assertEquals(111, route.getFeasibleDistance());
    }

    @Test
    void getReportedDistance() {
        assertEquals(1, route.getReportedDistance());
    }

    @Test
    void setReportedDistance() {
        route.setReportedDistance(2);
        assertEquals(2, route.getReportedDistance());
    }

    @Test
    void getConnectionType() {
        assertEquals("Any", route.getConnectionType());
    }

    @Test
    void setConnectionType() {
        route.setConnectionType("FastEthernet0/0");
        assertEquals("FastEthernet0/0", route.getConnectionType());
    }
}