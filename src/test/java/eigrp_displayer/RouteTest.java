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

    Connection connection1 = new Cable();
    Connection connection2 = new Cable();
    Connection connection3 = new Cable();

    @BeforeEach
    void init(){
        route.setTargetIPAddress(address);
        route.setFeasibleDistance(10);
        route.setReportedDistance(1);

        connection1.linkDevice(router1);
        connection1.linkDevice(router2);

        connection2.linkDevice(router2);
        connection2.linkDevice(router3);

        connection3.linkDevice(router3);
        connection3.linkDevice(router4);

        route.getPaths().add(connection1);
        route.getPaths().add(connection2);
        route.getPaths().add(connection3);
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
    void getPaths() {
        assertEquals(3, route.getPaths().size());
        assertEquals(connection1, route.getPaths().get(0));
        assertEquals(connection2, route.getPaths().get(1));
        assertEquals(connection3, route.getPaths().get(2));
    }

    @Test
    void getLowestBandwidth() {
        connection1.setBandwidth(2);
        connection2.setBandwidth(3);
        connection3.setBandwidth(4);

        assertEquals(5000000 ,route.getLowestBandwidth());
    }

    @Test
    void getSumOfDelays() {
        connection1.setDelay(30);
        connection2.setDelay(20);
        connection3.setDelay(10);
        assertEquals(60, route.getSumOfDelays());
    }

    @Test
    void getWorstLoad() {
        connection1.setLoad(22);
        connection2.setLoad(33);
        connection3.setLoad(11);

        assertEquals(11 ,route.getWorstLoad());
    }

    @Test
    void getWorstReliability() {
        connection1.setReliability(44);
        connection2.setReliability(20);
        connection3.setReliability(63);

        assertEquals(20 ,route.getWorstReliability());
    }
}