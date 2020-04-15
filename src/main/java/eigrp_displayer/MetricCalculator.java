package eigrp_displayer;

public class MetricCalculator {
    public int calculateMetric(Router router1, Router router2, Link link){
        int k1 = router1.isK1() ? 1 : 0;
        int k2 = router1.isK2() ? 1 : 0;
        int k3 = router1.isK3() ? 1 : 0;
        int k4 = router1.isK4() ? 1 : 0;
        int k5 = router1.isK5() ? 1 : 0;

        if(k5 == 0){
            return (k1 * link.getBandwidth() +
                    ((k2 * link.getBandwidth())/(256-link.getLoad()))
                    + k3 * link.getDelay()) * 256;
        }
        else
            return (k1 * link.getBandwidth() +
                    ((k2 * link.getBandwidth())/(256-link.getLoad()))
                    + k3 * link.getDelay()) *
                    k5/(k4 + link.getReliability()) * 256;
    }
}
