package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetricCalculatorTest {
    MetricCalculator calculator = new MetricCalculator();
    Router router1 = new Router("R1");
    Router router2 = new Router("R2");
    Link link = new Cable();

    @Test
    void calculateMetricDefaultCase() {
        router1.setK1(true);
        router1.setK2(false);
        router1.setK3(true);
        router1.setK4(false);
        router1.setK5(false);
        int metric = (link.getBandwidth() + link.getDelay()) * 256;
        assertEquals(metric, calculator.calculateMetric(router1, router2, link));
    }

    @Test
    void calculateMetricAllRsTrue() {
        router1.setK1(true);
        router1.setK2(true);
        router1.setK3(true);
        router1.setK4(true);
        router1.setK5(true);

        int metric = (link.getBandwidth() + link.getBandwidth() / (256 - link.getLoad()) + link.getDelay()) /(1 + link.getReliability()) * 256;
        assertEquals(metric, calculator.calculateMetric(router1, router2, link));
    }
}