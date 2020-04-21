package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MetricCalculatorTest {
    MetricCalculator calculator = new MetricCalculator();
    Router router1 = new Router("R1");
    Connection connection = new Cable();

    Route route = new Route();
    IPAddress address = Mockito.mock(IPAddress.class);

    Router router2 = Mockito.mock(Router.class);
    Router router3 = Mockito.mock(Router.class);
    Router router4 = Mockito.mock(Router.class);

    Connection connection1 = new Cable();
    Connection connection2 = new Cable();
    Connection connection3 = new Cable();

    @Test
    void calculateMetricRouter(){
        router1.setK1(true);
        router1.setK2(true);
        router1.setK3(true);
        router1.setK4(true);
        router1.setK5(true);

        route.setTargetIPAddress(address);
        route.setFeasibleDistance(10);
        route.setReportedDistance(1);
        route.setConnectionType("Any");

        connection1.linkDevice(router1);
        connection1.linkDevice(router2);

        connection2.linkDevice(router2);
        connection2.linkDevice(router3);

        connection3.linkDevice(router3);
        connection3.linkDevice(router4);

        route.getPaths().add(connection1);
        route.getPaths().add(connection2);
        route.getPaths().add(connection3);

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

        int bandwidth = route.getLowestBandwidth();
        int delay = route.getSumOfDelays();
        int load = route.getWorstLoad();
        int reliability = route.getWorstReliability();

        int metric = (bandwidth +
                (bandwidth / (256 - load))
                + delay) /(1 + reliability) * 256;
        assertEquals(metric, calculator.calculateMetric(router1, route));
    }


    @Test
    void calculateMetricDefaultCase() {
        router1.setK1(true);
        router1.setK2(false);
        router1.setK3(true);
        router1.setK4(false);
        router1.setK5(false);
        int metric = (connection.getBandwidth() + connection.getDelay()) * 256;
        assertEquals(metric, calculator.calculateMetric(router1, connection));
    }

    @Test
    void calculateMetricAllRsTrue() {
        router1.setK1(true);
        router1.setK2(true);
        router1.setK3(true);
        router1.setK4(true);
        router1.setK5(true);

        int metric = (connection.getBandwidth() + connection.getBandwidth() / (256 - connection.getLoad()) + connection.getDelay()) /(1 + connection.getReliability()) * 256;
        assertEquals(metric, calculator.calculateMetric(router1, connection));
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