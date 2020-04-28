package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetricCalculatorTest {
    MetricCalculator calculator = new MetricCalculator();
    Router router = new Router("R1");
    RouterController routerController0 = new RouterController(router);
    Connection connection = new Cable();

    IPAddress address = Mockito.mock(IPAddress.class);
    RoutingTableEntry entry = new RoutingTableEntry(address);

    RouterController routerController1 = Mockito.mock(RouterController.class);
    RouterController routerController2 = Mockito.mock(RouterController.class);
    RouterController routerController3 = Mockito.mock(RouterController.class);

    Connection connection1 = new Cable();
    Connection connection2 = new Cable();
    Connection connection3 = new Cable();

    ArrayList<Connection> path = new ArrayList<>(java.util.Arrays.asList(
            connection, connection1, connection2, connection3));

    @Test
    void calculateMetricRouter(){
        router.setK1(true);
        router.setK2(true);
        router.setK3(true);
        router.setK4(true);
        router.setK5(true);

        entry.setFeasibleDistance(10);

        connection1.linkDevices(routerController0, routerController1);
        connection2.linkDevices(routerController1, routerController2);
        connection3.linkDevices(routerController2, routerController3);

        connection1.setBandwidth(2);
        connection2.setBandwidth(3);
        connection3.setBandwidth(4);

        connection1.setDelay(30);
        connection2.setDelay(20);
        connection3.setDelay(10);

        connection1.setLoad(22);
        connection2.setLoad(33);
        connection3.setLoad(11);

        connection1.setReliability(44);
        connection2.setReliability(20);
        connection3.setReliability(63);

        entry.setPath(path);

        int bandwidth = entry.getLowestBandwidth();
        int delay = entry.getSumOfDelays();
        int load = entry.getWorstLoad();
        int reliability = entry.getWorstReliability();

        int metric = (bandwidth +
                (bandwidth / (256 - load))
                + delay) /(1 + reliability) * 256;
        assertEquals(metric, calculator.calculateMetric(router, entry));
    }


    @Test
    void calculateMetricDefaultCase() {
        router.setK1(true);
        router.setK2(false);
        router.setK3(true);
        router.setK4(false);
        router.setK5(false);
        int metric = (connection.getBandwidth() + connection.getDelay()) * 256;
        assertEquals(metric, calculator.calculateMetric(router, connection));
    }

    @Test
    void calculateMetricAllRsTrue() {
        router.setK1(true);
        router.setK2(true);
        router.setK3(true);
        router.setK4(true);
        router.setK5(true);

        int metric = (connection.getBandwidth() + connection.getBandwidth() /
                (256 - connection.getLoad()) + connection.getDelay()) /(1 + connection.getReliability()) * 256;
        assertEquals(metric, calculator.calculateMetric(router, connection));
    }

    @Test
    void calculateMetricDefaultCaseInts() {
        int bandwidth = 100;
        int delay = 300;

        int metric = (bandwidth + delay) * 256;
        assertEquals(metric, calculator.calculateMetric(bandwidth, delay));
    }

    @Test
    void calculateMetricAllInts() {
        int bandwidth = 100;
        int delay = 300;
        int load = 15;
        int reliability = 200;

        int metric = (bandwidth + bandwidth / (256 - load) + delay) /(1 + reliability) * 256;
        assertEquals(metric, calculator.calculateMetric(bandwidth, delay, load, reliability));
    }
}