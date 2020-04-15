package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteTest {
    Route route = new Route();
    IPAddress address = Mockito.mock(IPAddress.class);
    IPAddress address0 = Mockito.mock(IPAddress.class);

    Router router1 = Mockito.mock(Router.class);
    Router router2 = Mockito.mock(Router.class);
    Router router3 = Mockito.mock(Router.class);
    Router router4 = Mockito.mock(Router.class);

    Link link1 = new Cable();
    Link link2 = new Cable();
    Link link3 = new Cable();

    @BeforeEach
    void init(){
        route.setTargetIPAddress(address);
        route.setFeasibleDistance(10);
        route.setReportedDistance(1);
        route.setConnectionType("Any");

        link1.linkDevice(router1);
        link1.linkDevice(router2);

        link2.linkDevice(router2);
        link2.linkDevice(router3);

        link3.linkDevice(router3);
        link3.linkDevice(router4);

        route.getPaths().add(link1);
        route.getPaths().add(link2);
        route.getPaths().add(link3);
    }

    @Test
    void getIp_address() {
        assertEquals(address, route.getTargetIPAddress());
    }

    @Test
    void setIp_address() {
        route.setTargetIPAddress(address0);
        assertEquals(address0, route.getTargetIPAddress());
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

    @Test
    void getPaths() {
        assertEquals(3, route.getPaths().size());
        assertEquals(link1, route.getPaths().get(0));
        assertEquals(link2, route.getPaths().get(1));
        assertEquals(link3, route.getPaths().get(2));
    }

    @Test
    void getLowestBandwidth() {
        link1.setBandwidth(2);
        link2.setBandwidth(3);
        link3.setBandwidth(4);

        assertEquals(5000000 ,route.getLowestBandwidth());
    }

    @Test
    void getSumOfDelays() {
        link1.setDelay(30);
        link2.setDelay(20);
        link3.setDelay(10);
        assertEquals(60, route.getSumOfDelays());
    }

    @Test
    void getWorstLoad() {
        link1.setLoad(22);
        link2.setLoad(33);
        link3.setLoad(11);

        assertEquals(11 ,route.getWorstLoad());
    }

    @Test
    void getWorstReliability() {
        link1.setReliability(44);
        link2.setReliability(20);
        link3.setReliability(63);

        assertEquals(20 ,route.getWorstReliability());
    }
}