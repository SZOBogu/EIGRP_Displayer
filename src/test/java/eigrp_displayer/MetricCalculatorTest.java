package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MetricCalculatorTest {
    MetricCalculator calculator = new MetricCalculator();
    Router router1 = new Router("R1");
    Link link = new Cable();

    Route route = new Route();
    IPAddress address = Mockito.mock(IPAddress.class);

    Router router2 = Mockito.mock(Router.class);
    Router router3 = Mockito.mock(Router.class);
    Router router4 = Mockito.mock(Router.class);

    Link link1 = new Cable();
    Link link2 = new Cable();
    Link link3 = new Cable();

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

        link1.linkDevice(router1);
        link1.linkDevice(router2);

        link2.linkDevice(router2);
        link2.linkDevice(router3);

        link3.linkDevice(router3);
        link3.linkDevice(router4);

        route.getPaths().add(link1);
        route.getPaths().add(link2);
        route.getPaths().add(link3);

        link1.setBandwidth(2);
        link2.setBandwidth(3);
        link3.setBandwidth(4);

        link1.setDelay(30);
        link2.setDelay(20);
        link3.setDelay(10);

        link1.setLoad(22);
        link2.setLoad(33);
        link3.setLoad(11);

        link1.setReliability(44);
        link2.setReliability(20);
        link3.setReliability(63);

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
        int metric = (link.getBandwidth() + link.getDelay()) * 256;
        assertEquals(metric, calculator.calculateMetric(router1, link));
    }

    @Test
    void calculateMetricAllRsTrue() {
        router1.setK1(true);
        router1.setK2(true);
        router1.setK3(true);
        router1.setK4(true);
        router1.setK5(true);

        int metric = (link.getBandwidth() + link.getBandwidth() / (256 - link.getLoad()) + link.getDelay()) /(1 + link.getReliability()) * 256;
        assertEquals(metric, calculator.calculateMetric(router1, link));
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