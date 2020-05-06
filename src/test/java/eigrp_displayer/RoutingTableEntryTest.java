package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoutingTableEntryTest {
    IPAddress ip_address = Mockito.mock(IPAddress.class);
    IPAddress ip_address0 = Mockito.mock(IPAddress.class);
    RoutingTableEntry entry = new RoutingTableEntry("A", ip_address);
    RoutingTableEntry entry0 = new RoutingTableEntry(ip_address0);

    Connection connection1 = new Cable();
    Connection connection2 = new Cable();
    Connection connection3 = new Cable();

    RouterController routerController0 = Mockito.mock(RouterController.class);
    RouterController routerController1 = Mockito.mock(RouterController.class);
    RouterController routerController2 = Mockito.mock(RouterController.class);
    RouterController routerController3 = Mockito.mock(RouterController.class);

    ArrayList<Connection> path = new ArrayList<>(Arrays.asList(connection1, connection2, connection3));

    @BeforeEach
    void init(){
        entry.setPath(path);

        connection1.linkDevices(routerController0, routerController1);
        connection2.linkDevices(routerController1, routerController2);
        connection3.linkDevices(routerController2, routerController3);
    }

    @Test
    void getCode() {
        assertEquals("A", entry.getCode());
        assertEquals("P", entry0.getCode());
    }

    @Test
    void setCode() {
        entry.setCode("D");
        assertEquals("D", entry.getCode());
    }

    @Test
    void getIp_address() {
        assertEquals(ip_address, entry.getIp_address());
        assertEquals(ip_address0, entry0.getIp_address());
    }

    @Test
    void setIp_address() {
        entry.setIp_address(ip_address0);
        assertEquals(ip_address0, entry.getIp_address());
    }

    @Test
    void getTicksSinceLastHelloMessage() {
        assertEquals(0, entry.getTicksSinceLastHelloMessage());
    }

    @Test
    void incrementTicks() {
        assertEquals(0, entry.getTicksSinceLastHelloMessage());
        entry.incrementTicks(2);
        assertEquals(2, entry.getTicksSinceLastHelloMessage());
        entry.incrementTicks(3);
        assertEquals(5, entry.getTicksSinceLastHelloMessage());
    }

    @Test
    void resetTicks() {
        entry.incrementTicks(100);
        assertEquals(100, entry.getTicksSinceLastHelloMessage());
        entry.resetTicks();
        assertEquals(0, entry.getTicksSinceLastHelloMessage());
    }

    @Test
    void getPaths() {
        assertEquals(3, entry.getPath().size());
        assertEquals(connection1, entry.getPath().get(0));
        assertEquals(connection2, entry.getPath().get(1));
        assertEquals(connection3, entry.getPath().get(2));
    }

    @Test
    void getLowestBandwidth() {
        connection1.setBandwidth(2);
        connection2.setBandwidth(3);
        connection3.setBandwidth(4);

        assertEquals(5000000 ,entry.getLowestBandwidth());
    }

    @Test
    void getSumOfDelays() {
        connection1.setDelay(30);
        connection2.setDelay(20);
        connection3.setDelay(10);
        assertEquals(60, entry.getSumOfDelays());
    }

    @Test
    void getWorstLoad() {
        connection1.setLoad(22);
        connection2.setLoad(33);
        connection3.setLoad(11);

        assertEquals(11 ,entry.getWorstLoad());
    }

    @Test
    void getWorstReliability() {
        connection1.setReliability(44);
        connection2.setReliability(20);
        connection3.setReliability(63);

        assertEquals(20 ,entry.getWorstReliability());
    }

    @Test
    void getPath() {
        assertEquals(path, entry.getPath());
    }

    @Test
    void setPath() {
        entry.setPath(new ArrayList<>());
        assertEquals(new ArrayList<>(), entry.getPath());
    }

    @Test
    void getReportedDistance() {
        assertEquals(Long.MAX_VALUE, entry.getReportedDistance());
    }

    @Test
    void setReportedDistance() {
        entry.setReportedDistance(11);
        assertEquals(11, entry.getReportedDistance());
    }

    @Test
    void getFeasibleDistance() {
        assertEquals(Long.MAX_VALUE, entry.getFeasibleDistance());
    }

    @Test
    void setFeasibleDistance() {
        entry.setFeasibleDistance(1);
        assertEquals(1, entry.getFeasibleDistance());
    }


    @Test
    void getDevicePath() {
    }

    @Test
    void getIPAddressPath() {
    }

    @Test
    void getStringPath() {
    }
}