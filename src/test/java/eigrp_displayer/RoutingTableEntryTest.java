package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoutingTableEntryTest {
    IP_Address ip_address = Mockito.mock(IP_Address.class);
    IP_Address ip_address0 = Mockito.mock(IP_Address.class);
    RoutingTableEntry entry = new RoutingTableEntry("P", ip_address);
    Route route = Mockito.mock(Route.class);
    Route route0 = Mockito.mock(Route.class);
    ArrayList<Route> routeList = new ArrayList<>(Arrays.asList(route, route0));

    @BeforeEach
    void init(){
        entry.setRoutes(routeList);
        entry.setShortestFeasibleDistance(10);
        entry.setSuccessors(1);
    }

    @Test
    void getCode() {
        assertEquals("P", entry.getCode());
    }

    @Test
    void setCode() {
        entry.setCode("A");
        assertEquals("A", entry.getCode());
    }

    @Test
    void getIp_address() {
        assertEquals(ip_address, entry.getIp_address());
    }

    @Test
    void setIp_address() {
        entry.setIp_address(ip_address0);
        assertEquals(ip_address0, entry.getIp_address());
    }

    @Test
    void getSuccessors() {
        assertEquals(1, entry.getSuccessors());
    }

    @Test
    void setSuccessors() {
        entry.setSuccessors(0);
        assertEquals(0, entry.getSuccessors());
    }

    @Test
    void getShortestFeasibleDistance() {
        assertEquals(10, entry.getShortestFeasibleDistance());
    }

    @Test
    void setShortestFeasibleDistance() {
        entry.setShortestFeasibleDistance(100);
        assertEquals(100, entry.getShortestFeasibleDistance());
    }

    @Test
    void getRoutes() {
        assertEquals(routeList, entry.getRoutes());
    }

    @Test
    void setRoutes() {
        ArrayList dummyList = new ArrayList();
        entry.setRoutes(dummyList);
        assertEquals(dummyList, entry.getRoutes());
    }
}